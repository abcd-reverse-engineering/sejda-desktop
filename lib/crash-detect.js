const fs = require('fs');
const path = require('path');
const manifest = rootRequire('../package.json')

module.exports = {
  initStartup: function() {
    const filePath = crashDetectFilePath();
    if(fs.existsSync(filePath)){
      // file exists, app crashed
      return true;
    }

    // create the file
    fs.closeSync(fs.openSync(filePath, 'w'));
    return false;
  },
  markStartupOk: function() {
    // startup ok, remove the file
    const filePath = crashDetectFilePath();
    fs.unlinkSync(filePath);
  }
}

function crashDetectFilePath() {
  if(process.platform === 'win32') {
    const appDataDir = path.join(process.env['APPDATA'], manifest.name);
    if (!fs.existsSync(appDataDir)){
      fs.mkdirSync(appDataDir);
    }

    return path.join(appDataDir, 'crash-detect.txt');
  } else {
    return path.join(process.env['HOME'], '.sejda-crash-detect.txt');
  }
}
