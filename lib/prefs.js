const fs = require('fs')
const path = require('path')
const _ = require('underscore')
const manifest = rootRequire('../package.json')

module.exports = {
  set: function(update) {
    var data = read()
    write(_.extend(data, update))
  },
  get: function(name) {
    if(name) {
      return read()[name]
    } else {
      return read()
    }
  }
}

// deprecated
function userHome() {
  return process.env[(process.platform == 'win32') ? 'USERPROFILE' : 'HOME'];
}
const prefsPath_deprecated = path.join(userHome(), manifest.config.file)

// new storage path
function newPrefsPath() {
  if(process.platform == 'win32') {
    // attempt to read from appData
    var appDataDir = path.join(process.env['APPDATA'], manifest.name);
    // create app specific folder in APPDATA, if it does not exist
    if (!fs.existsSync(appDataDir)){
      fs.mkdirSync(appDataDir);
    }

    return path.join(appDataDir, 'prefs.json');
  } else {
    return path.join(process.env['HOME'], manifest.config.file);
  }
}

function write(data) {
  fs.writeFileSync(newPrefsPath(), JSON.stringify(data));
}

function read() {
  try {
    try {
      // try to read from new location (after migration)
      return JSON.parse(fs.readFileSync(newPrefsPath(), 'UTF-8'))
    }
    catch(err) {
      // failed, try to read from old location (before migration)
      return JSON.parse(fs.readFileSync(prefsPath_deprecated, 'UTF-8'))
    }
  } catch(err) {
    return {}
  }

}