const fs = require('fs')
const path = require('path')
const exec = require('child_process').exec;

module.exports = {
  open: function(targetPath) {
    var verb = 'open'
    if(/^win/.test(process.platform)){
      verb = 'explorer'
    }
    if(/^linux/.test(process.platform)){
      verb = 'xdg-open'
    }

    var isDirectory = false;
    try {
      isDirectory = fs.statSync(targetPath).isDirectory()  
    } catch (e) {
      // eg: ENOENT: no such file or directory, stat 'C:\\Path\\to\\file\\already\\moved'"
      // noop
    }

    // if the target is a file, open parent folder
    if(!isDirectory){
      const parentPath = path.normalize(path.join(targetPath, '..'))
      return exec(verb + ' "' + parentPath + '"')
    } else {
      return exec(verb + ' "' + targetPath + '"')
    }
  }
}
