const fs = require('fs');
const manifest = rootRequire('../package.json');

const isWin = process.platform === 'win32';
const isMac = process.platform === 'darwin';
const isLinux = process.platform === 'linux';
// FIXME: this seems unnecessary
const path = isWin ? require('path').win32 : require('path');

function platformBasedDirSuffix() {
  if(isWin) {
    if(process.arch === 'ia32') {
      return "-windows"
    } else {
      return "-windows-x64"
    }
  }

  if(isMac) {
    if(process.arch === 'arm64') return "-mac-arm64"
    return "-mac"
  }

  if(isLinux) {
    return "-linux"
  }

  return "";
}

const appDir = path.normalize(path.join(__dirname, '..', '..', 'app'));
const vendorDir = path.normalize(path.join(appDir, '..', 'vendor'));
const javaHome = path.join(vendorDir, 'jdk21' + platformBasedDirSuffix());
const tesseractHome = path.join(vendorDir, 'tesseract' + platformBasedDirSuffix());
const tesseractData = path.join(vendorDir, 'tessdata');
const desktopLauncherLibDir = path.join(vendorDir, 'desktop-launcher', 'lib');
const desktopLauncherLibClasspathJar = path.normalize(path.join(desktopLauncherLibDir, 'com.sejda.desktop-launcher-1.0.0-classpath.jar'));

const javaCmd = (function(){
  if(isWin) {
    return path.join(javaHome, 'bin', 'java.exe');
  }

  if(isMac || isLinux) {
    return path.join(javaHome, 'bin', 'java');
  }
})();

const tesseractCmd = (function(){
  if(isWin) {
    return path.join(tesseractHome, 'tesseract.exe');
  }

  return path.join(tesseractHome, 'bin', 'tesseract');
})();

const appDataDir = (function() {
  let dir = '';
  if(process.platform === 'win32') {
    dir = path.join(process.env['APPDATA'], manifest.name);
  } else {
    dir = path.join(process.env['HOME'], '.' + manifest.name);
  }

  // create app specific folder in APPDATA, if it does not exist
  if (!fs.existsSync(dir)){
    fs.mkdirSync(dir);
  }

  return dir;
})();

module.exports = {
  vendor: {
    tesseractHome: tesseractHome,
    tesseractData: tesseractData,
    javaHome: javaHome,
    javaCmd: javaCmd,
    tesseractCmd: tesseractCmd,
    vendorDir: vendorDir,
    desktopLauncherLibClasspathJar: desktopLauncherLibClasspathJar
  },
  isWin: isWin,
  isMac: isMac,
  isLinux: isLinux,
  appDataDir: appDataDir
};
