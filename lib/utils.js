const path = require('path');
const fs = require('fs');
const os = require('os');
const {isWin} = require('./app-paths');

function urlEncodeFilePath(filePath) {
  // for testing windows paths on mac
  var p = filePath.startsWith("C:") ? path.win32 : path;

  var parsed = p.parse(filePath);

  if (parsed.root === parsed.dir && parsed.base === '') return parsed.root;
  var result = p.join(urlEncodeFilePath(parsed.dir), encodeURIComponent(parsed.base));
  // electron does not handle UNC paths //network/path/here
  // so use a workaround, via the floppy :)
  if(result.startsWith("\\\\") || result.startsWith("//")) {
    result = 'A:\\' + result.substring(2);
  }
  return result;
}

var Crypto = (function () {
  var crypto = require('crypto');
  var self = {};

  self.random = function random(c) {
    return crypto.randomBytes(c).toString('hex');
  }

  function pbkdf2(password, salt) {
    return crypto.pbkdf2Sync(new Buffer(password, 'utf-8'), new Buffer(salt, 'utf-8'), 4096, 128 / 8, 'SHA1');
  }

  function md5(value) {
    return crypto.createHash('md5').update(value).digest();
  }

  function encryptRaw(plain, key, iv) {
    const cipher = crypto.createCipheriv('aes-128-cbc', key, iv);
    const data = cipher.update(plain);
    const remaining = cipher.final();
    return Buffer.concat([data, remaining], data.length + remaining.length);
  }

  self.encrypt = function encrypt(plain, password, salt) {
    var key = pbkdf2(password, salt);
    var iv = md5(salt);
    return encryptRaw(Buffer.from(plain, 'utf-8'), key, iv).toString('hex');
  }

  function decryptRaw(encrypted, key, iv) {
    const cipher = crypto.createDecipheriv('aes-128-cbc', key, iv);
    const data = cipher.update(encrypted);
    const remaining = cipher.final();
    return Buffer.concat([data, remaining], data.length + remaining.length);
  }

  self.decrypt = function decrypt(encrypted, password, salt) {
    var key = pbkdf2(password, salt);
    var iv = md5(salt);
    return decryptRaw(Buffer.from(encrypted, 'hex'), key, iv).toString('utf-8')
  };

  return self;
})();

function mkdirTmpDirSync(hintTmpFolder) {
  var tmpDirBase = hintTmpFolder || os.tmpdir();
  //console.log('mkdirTmpDirSync:', hintTmpFolder, tmpDirBase, process.env.TEMP, process.env.TMP, process.env.SystemRoot, process.env.windir);

  try {
    var name = 'tmp-sejda-' + Date.now();
    var tmpDir = path.join(tmpDirBase, name);
    fs.mkdirSync(tmpDir, { recursive: true });
    return tmpDir;
  } catch (e) {
    if(isWin) {
      var fallbackTmpFolder = path.join(process.env['USERPROFILE'], 'AppData', 'Local', 'Temp');
      process.env.TEMP = fallbackTmpFolder;
      process.env.TMP = fallbackTmpFolder;
      console.warn('Failure creating temp folder: "' + tmpDirBase + '"; Using fallback: "' + fallbackTmpFolder + '"; Env is:', process.env.TEMP, process.env.TMP, process.env.SystemRoot, process.env.windir);

      return mkdirTmpDirSync(fallbackTmpFolder);
    } else {
      throw e;
    }
  }
}

/**
 * File touching utility for keeping temporary files alive
 * by updating their last accessed and modified timestamps.
 */

const tempFilesToTouch = [];

const touchTimer = setInterval(function(){
  const now = new Date();
  tempFilesToTouch.forEach(function(filePath) {
    try {
      fs.utimesSync(filePath, now, now);
    } catch (err) {
      console.error('Could not touch:', filePath)
    }
  });
}, 3600 * 1000);

function scheduleKeepTmpFileAlive(filePath) {
  if (!filePath) {
    return false;
  }
  
  if (tempFilesToTouch.indexOf(filePath) !== -1) {
    return false;
  }

  tempFilesToTouch.push(filePath);

  return true;
}

function isTemp(path) {
  if (!path || typeof path !== 'string') {
    return false;
  }

  // Normalize path separators to forward slash for consistent checking
  const normalizedPath = path.replace(/\\/g, '/').toLowerCase();

  // Standard OS temporary directories with precise matching patterns
  const tempPatterns = [
    // Windows - specific standard temp paths
    /^[a-z]:\/windows\/temp\/?$/i,
    /^[a-z]:\/windows\/temp\//i,
    /^[a-z]:\/users\/[^/]+\/appdata\/local\/temp\/?$/i,
    /^[a-z]:\/users\/[^/]+\/appdata\/local\/temp\//i,
    /^[a-z]:\/users\/[^/]+\/appdata\/roaming\/temp\/?$/i,
    /^[a-z]:\/users\/[^/]+\/appdata\/roaming\/temp\//i,
    /^[a-z]:\/temp\/?$/i,
    /^[a-z]:\/temp\//i,

    // Unix-like OS temp paths with anchors to avoid partial matches
    /^\/tmp\/?$/i,
    /^\/tmp\//i,
    /^\/var\/tmp\/?$/i,
    /^\/var\/tmp\//i,

    // macOS specific
    /^\/private\/tmp\/?$/i,
    /^\/private\/tmp\//i,
    /^\/private\/var\/folders\/[^/]+\/[^/]+\/T\/?$/i,
    /^\/private\/var\/folders\/[^/]+\/[^/]+\/T\//i,
    /^\/var\/folders\/[^/]+\/[^/]+\/T\/?$/i,
    /^\/var\/folders\/[^/]+\/[^/]+\/T\//i,

    // Linux specific
    /^\/run\/user\/\d+\/?$/i,
    /^\/run\/user\/\d+\//i
  ];

  // Check if the path exactly matches any of the standard OS temp patterns
  return tempPatterns.some(function(pattern){
    return pattern.test(normalizedPath);
  });
}

function hasAvailableDiskSpace(requiredMBs) {
  const tempDir = os.tmpdir();

  try {
    const stats = fs.statfsSync(tempDir);

    const usableSpaceBytes = stats.bfree * stats.bsize;
    const usableSpaceMB = Math.floor(usableSpaceBytes / (1024 * 1024));
    
    // console.log('usable:', usableSpaceMB, 'required:', requiredMBs);

    if (usableSpaceMB < requiredMBs) {
      console.log(`Warning: Only ${usableSpaceMB} MB available. Need ${requiredMBs} MB. TempDir: ${tempDir}.`);
      return false
    }
  } catch (error) {
    console.error(`An error occurred while checking space: ${error.code} ${error.message}`);
  }

  return true
}

module.exports = {
  urlEncodeFilePath: urlEncodeFilePath,
  Crypto: Crypto,
  mkdirTmpDirSync: mkdirTmpDirSync,
  isTemp: isTemp,
  scheduleKeepTmpFileAlive: scheduleKeepTmpFileAlive,
  hasAvailableDiskSpace: hasAvailableDiskSpace
};
