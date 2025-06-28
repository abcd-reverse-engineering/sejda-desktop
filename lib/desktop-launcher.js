const child_process = require('child_process');
const _ = require('underscore');
const async = require('async');
const fs = require('fs');
const os = require('os');
const { isMac, isWin, vendor: vendor, isLinux} = require('./app-paths');
const path = isWin ? require('path').win32 : require('path');
const tesseract = require('./tesseract');
const utils = require('./utils')

function formatDate(date) {
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const seconds = date.getSeconds().toString().padStart(2, '0');
  const milliseconds = date.getMilliseconds().toString().padStart(3, '0');

  return `${hours}:${minutes}:${seconds}.${milliseconds}`;
}

module.exports = {
  execute: function(task, _callback) {
    //console.log(formatDate(new Date()))
    
    const globalCallback = once(function (err, data) {
      _callback(serializeError(err), data);
    });

    const env = process.env;

    const javaCmd = vendor.javaCmd;
    let javaOpts = [];
    env['JAVA_HOME'] = vendor.javaHome;

    if(env['SEJDA_TESTING_INVALID_JVM'] === 'true') {
      env['JAVA_HOME'] = "invalid";
    }
    
    if(isMac) {
      env['JAVA_HOME'] = '';
    }
    
    if(isWin) {
      // check java.exe is present, sometimes it gets deleted
      if(!fs.existsSync(javaCmd)){
        let err = new Error('JAVACMD missing: ' + javaCmd);
        err.code = 'JAVACMD_MISSING';
        return globalCallback(err);
      }
    }

    const useEmbeddedOcr = !isLinux;
    if(useEmbeddedOcr){
      env['SEJDA_TESSERACT_PATH'] = vendor.tesseractCmd;  
    }

    env['java.awt.headless'] = 'true';
    env['sejda.unethical.read'] = 'true';
    
    // makes sure classpath is not influenced externally
    env['CLASSPATH'] = '.';
    // make sure any existing setup on the system doesn't break our launcher
    env['JAVA_OPTS'] = '-Dfoo=bar';
    env['_JAVA_OPTIONS'] = '-Dfoo=bar';
    env['JAVA_TOOL_OPTIONS'] = '-Dfoo=bar';
    env['JDK_JAVA_OPTIONS'] = '-Dfoo=bar';
    
    const base = utils.mkdirTmpDirSync();
    const taskInFile = path.join(base, 'task.in');
    const taskOutFile = path.join(base, 'task.out');
    const taskKillFile = path.join(base, 'task.kill');
    const argsTxtFile = path.join(base, 'args.txt');
    
    // handle any .accessibility.properties in HOME
    const homePath = process.env[isWin ? 'USERPROFILE' : 'HOME'];
    const accessibilityPropertiesFile = path.join(homePath, '.accessibility.properties')
    const accessibilityPropertiesMovedFile = accessibilityPropertiesFile + '.tmp_' + Date.now();

    async.series(
      [
        function prepareExecution(cb) {
          //console.log('prepareExecution', task, taskInFile);
          const cb_wrap = once(cb);
          const s = typeof (task) === 'string' ? task : JSON.stringify(task);
          try {
            fs.writeFileSync(taskInFile, s)
            return cb_wrap();
          } catch (e) {
            console.error(e);
            return cb_wrap(e);
          }
        },
        function executeTask(cb) {
          //console.log('executeTask');
          const cb_wrap = once(cb);
          const args = "task.in=" + taskInFile + "\ntask.out=" + taskOutFile + "\ntask.kill=" + taskKillFile;

          try {
            // write console args to file, without path to executable
            // can fail due to EPERM
            fs.writeFileSync(argsTxtFile, args);  
          } catch (e) {
            return cb_wrap(e);
          }
          
          // handle .accessibility.properties
          try {
            if(fs.lstatSync(accessibilityPropertiesFile).isFile()){
              fs.renameSync(accessibilityPropertiesFile, accessibilityPropertiesMovedFile)
            }
          } catch (e) {}

          function setHeapSize(maxHeapSize) {
            let jdk11SpecificOpts = ['--illegal-access=permit', '--add-opens=java.base/jdk.internal.loader=ALL-UNNAMED', '--add-exports=java.base/jdk.internal.ref=ALL-UNNAMED'];
            javaOpts = jdk11SpecificOpts.concat([
              '-Dfile.encoding=UTF8', '-Dapple.awt.UIElement=true', '-Xmx' + maxHeapSize + 'M',
              '-Djavax.accessibility.assistive_technologies=code.util.NoopAccessibilityProvider',
              '-Djavax.accessibility.screen_magnifier_present=false', '--enable-preview',
              '-Dsejda.jvm.startup=' + Date.now()
            ]);
          }
          let maxHeapSize = calculateMaxHeapSize(task);
          setHeapSize(maxHeapSize);

          let cmd = javaCmd;
          if(env['SEJDA_TESTING_INVALID_JVM'] === 'true') {
            cmd = 'invalid';
          }

          if(task['type'] === 'ocr' && useEmbeddedOcr) {
            try {
              env['TESSDATA_PREFIX'] = tesseract.resolveTessdataDir();
            } catch (err) {
              console.error('Failed to resolve tessdata dir');
              console.error(err);
            }
          }
          
          function execCmd() {
            const cmdArgs = javaOpts.concat([
              '-cp', vendor.desktopLauncherLibClasspathJar,
              'DesktopLauncher', argsTxtFile
            ]);
            const cwd = vendor.vendorDir;
            env['CWD'] = cwd;

            // console.log(env['SEJDA_TESSERACT_PATH']);
            // console.log(env['TESSDATA_PREFIX']);
            // console.log(env['CWD']);

            const execOpts = { maxBuffer: 1024 * 1024 * 10, env: env, windowsHide: true, cwd: cwd };
            const execCallback = function(execResult){
              const err = execResult.error, stderr = execResult.stderr, stdout = execResult.stdout,
                exitCode = execResult.exitCode;
              const stdOutErr = (stderr || '') + (stdout || '');
              if(exitCode !== 0 && stdOutErr &&
                (
                  stdOutErr.includes('Error occurred during initialization of VM') ||
                  stdOutErr.includes('There is insufficient memory for the Java Runtime Environment to continue')
                )
              ) {
                // relaunch command with less RAM
                // until heap size is lower than 200M
                console.error('Detected VM initialization error');
                console.error(stdOutErr);
                if(maxHeapSize > 200) {
                  maxHeapSize = Math.round(maxHeapSize * 0.8);
                  setHeapSize(maxHeapSize);
                  console.warn('Relaunching with maxHeapSize:', maxHeapSize);
                  return execCmd();
                }
              }

              //console.log('stdout:', stdout);
              //console.log('stderr:', stderr);

              const data = {
                stderr: stderr,
                stdout: stdout,
                exitCode: exitCode,
                cliCmd: cmd,
                cliCmdArgs: cmdArgs
              };

              cb_wrap(err, data)
            };
            
            //throw new Error('spawn EPERM')
            //console.log('Executing', cmd, cmdArgs);
            const child = child_process.spawn(cmd, cmdArgs, execOpts);
            let stdout = "", stderr = "", error;

            //console.log(`Executing task with pid: ${child.pid}`);

            child.stdout.setEncoding('utf8');
            child.stdout.on('data', function(data) {
              //console.log(data.toString());
              stdout += data.toString();
            });

            child.stderr.setEncoding('utf8');
            child.stderr.on('data', function(data) {
              //console.log(data.toString());
              stderr += data.toString();
            });

            child.on('close', function(exitCode) {
              execCallback({
                exitCode: exitCode,
                stdout: stdout,
                stderr: stderr,
                error: error
              })
            });

            child.on('error', function(err) {
              console.error(err);
              error = err;
            });
          }

          try {
            execCmd();  
          } catch (e){
            return cb_wrap(e)
          }
        }
      ],
      function(err, results) {
        //console.log('Done, ', err, results);
        const data = {
          memory: {
            total: os.totalmem(),
            free: os.freemem(),
            javaOpts: javaOpts.join(' ')
          },
          arch: process.arch,
          env: {}
        };
        
        ['JAVA_HOME', 'JAVA_TOOL_OPTIONS', 'JAVA_OPTS', '_JAVA_OPTIONS', 'JDK_JAVA_OPTIONS', 'PWD', 'CWD'].forEach(function(key){
          data.env[key] = env[key];
        });
        
        try {
          data.result = JSON.parse(fs.readFileSync(taskOutFile, 'UTF-8'))
        } catch(e) {
          data.result = {};
        }

        _.extend(data, results[1]);

        if(data.result && (data.result.status === 'Failed')) {
          err = err || new Error('Task failed');
        }
        
        if(data.exitCode !== 0) {
          err = err || new Error(`Task failed (${data.exitCode})`);
        }
        
        // clean up
        try {
          fs.unlinkSync(argsTxtFile);
        } catch (e) {}
        try {
          fs.unlinkSync(taskInFile);
        } catch (e) {}
        try {
          fs.unlinkSync(taskOutFile);
        } catch (e) {}
        try {
          fs.unlinkSync(taskKillFile);
        } catch (e) {}
        try {
          fs.rmdirSync(base);
        } catch (e) {}

        // cleanup .accessibility.properties
        try {
          if(fs.lstatSync(accessibilityPropertiesMovedFile).isFile()){
            fs.renameSync(accessibilityPropertiesMovedFile, accessibilityPropertiesFile)
          }
        } catch (e) {}

        return globalCallback(err, data)
      }
    );

    return {
      cancel: function() {
        try {
          // console.log('Cancelling task: ', base.name);
          fs.openSync(taskKillFile, 'w');
        } catch (e) {
          // noop
        }
      }
    }
  },
  calculateMaxHeapSize: calculateMaxHeapSize
};

function calculateMaxHeapSize(task, platform, arch, totalmem) {
  arch = arch || os.arch();
  platform = platform || os.platform();
  totalmem = totalmem || os.totalmem();
  
  const isSmallTask = task['type'] === 'edit' || task['type'] === 'fillSign';

  const totalMemoryMb = totalmem / 1024 / 1024;
  let heapSize = Math.round(totalMemoryMb * 0.6);

  // on windows 32 bit, can't go over 2Gb limit
  // also there seems to be problems with requirement that memory is continuous
  // so let's stick to the 1Gb as before
  if(platform === 'win32' && arch !== 'x64') {
    heapSize = Math.min(1024, heapSize);
  }
  
  if(isSmallTask) {
    heapSize = Math.min(2048, heapSize);
  } else {
    // some systems have a lot of ram, eg. 64Gb
    // don't allocate more than 8Gb
    // TODO: adjust this for large batches of files?
    heapSize = Math.min(8192, heapSize);
  }

  return heapSize;
}

// workaround for https://github.com/electron/electron/issues/25596
function serializeError(e) {
  if(e) {
    return {
      name: e.name,
      message: e.message,
      stack: e.stack,
      code: e.code
    }  
  } else {
    return e;
  }
}

// from underscore.js
function once(fn) {
  let ran = false, memo;
  return function() {
    if (ran) return memo;
    ran = true;
    memo = fn.apply(this, arguments);
    fn = null;
    return memo;
  };
}
