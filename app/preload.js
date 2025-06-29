global.rootRequire = function(name) {
  return require(__dirname + '/lib/' + name);
};

const { contextBridge, ipcRenderer, Menu } = require('electron');
const appManifest = rootRequire('../package.json');

const path = require('path');
const fs = require('fs');
const fse = require('fs-extra');
const utils = require("./lib/utils");

contextBridge.exposeInMainWorld('sejdaContext', {
  electron: {
    app: {
      getPath: function(name) {
        return ipcRenderer.sendSync('electron.app.getPath', name);
      },
      setTitle: function(title) {
        let fullTitle = appManifest.productName;
        if(title.trim() !== '') {
          fullTitle = title + ' - ' + fullTitle;
        }
        return ipcRenderer.sendSync('electron.app.setTitle', fullTitle);
      },
      i18n: {
        __: function(s) {
          return ipcRenderer.sendSync('electron.app.i18n.__', s);
        }
      },
      externalLinkClicked: function(){
        return ipcRenderer.sendSync('electron.app.externalLinkClicked', '');
      }
    },
    dialog: {
      showOpenDialog: function(args){
        return ipcRenderer.invoke('electron.dialog.showOpenDialog', args);
      },
      showMessageBox: function(args){
        return ipcRenderer.invoke('electron.dialog.showMessageBox', args);
      },
      showSaveDialog: function(args){
        return ipcRenderer.invoke('electron.dialog.showSaveDialog', args);
      }
    },
    menu: {
      popup: function(args) {
        return ipcRenderer.invoke('electron.menu.popup', args);
      },
      setPageActions: function(args) {
        return ipcRenderer.sendSync('electron.menu.pageActions', args);
      }
    }
  },
  node: {
    process: {
      platform: function(){
        return process.platform;
      },
      on: function(event, callback) {
        process.on(event, callback);
      },
      envVariable: function(name) {
        return process.env[name];
      }
    },
    path: {
      dirname: path.dirname,
      join: path.join,
      resolve: path.resolve,
      basename: path.basename
    },
    fse: {
      currentDir: function(){
        return __dirname;
      },
      isFolderNotEmpty: function(folderName, callback) {
        fs.readdir(folderName, function(err, files) {
          const result = err || files.length > 0;
          callback(result);
        })
      },
      isFolderWritable: function(dir) {
        try {
          fs.accessSync(dir, fs.W_OK);
          return true;
        } catch(e) {
          return false;
        }
      },
      isFolder: function(dir) {
        return fs.statSync(dir).isDirectory();
      },
      getFileSize: function(filePath){
        return fs.statSync(filePath).size;
      },
      exists: function(filePath) {
        return fs.existsSync(filePath)
      },
      createTmpFile: function(filename, arrayBuffer) {
        const buffer = Buffer.from(new Uint8Array(arrayBuffer));
        const base = utils.mkdirTmpDirSync();
        const tmpFile = path.join(base, filename);
        
        fse.outputFileSync(tmpFile, buffer);
        utils.scheduleKeepTmpFileAlive(tmpFile);
        return tmpFile;
      },
      createTmpCopy: function(filePath) {
        const base = utils.mkdirTmpDirSync();
        const filename = path.basename(filePath)
        const tmpFile = path.join(base, filename);
        
        // ensure filePath is a file
        if(!fs.lstatSync(filePath).isFile()){
          throw new Error("Expected a file: " + filePath)
        }

        fse.copySync(filePath, tmpFile, { overwrite : false, errorOnExist: true });
        utils.scheduleKeepTmpFileAlive(tmpFile);
        return tmpFile;
      },
      isTemp: function(p){
        return utils.isTemp(p);
      },
      hasAvailableDiskSpace: function(requiredMBs){
        return utils.hasAvailableDiskSpace(requiredMBs)
      }
    },
    __dirname: __dirname
  },
  general: {
    prefs: rootRequire('prefs'),
    appManifest: appManifest,
    utils: rootRequire('utils'),
    osHelper: rootRequire('os-helper'),
    desktopLauncher: rootRequire('desktop-launcher'),
    tesseract: rootRequire('tesseract'),
    webContents: {
      on: function(channel, callback){
        ipcRenderer.on(channel, function(event, args){
          callback(args);
        })
      }
    }
  },
  editor: {
    getAvailableFonts: function(){
      return ipcRenderer.invoke('font-scanner.getAvailableFonts');
    }
  },
  licensing: {
    getLicenseData: function(callback){
      rootRequire('computer-id').get(function(computerId){
        var licenseData = {
          id: appManifest.identifier,
          version: appManifest.version,
          hostname: computerId.hostname,
          username: computerId.username,
          hostSerial: computerId.serial
        };

        callback(licenseData);
      })
    }
  }
});
