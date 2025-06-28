const fse = require('fs-extra');
const fs = require('fs');
const https = require('https');
const { appDataDir, vendor } = require('./app-paths');

const isWin = process.platform === 'win32';
// avoid paths such as C:\Users\foo\AppData\Roaming\sejda-desktop\tessdata/deu.traineddata
const path = isWin ? require('path').win32 : require('path');

const tessdataPath = path.join(appDataDir, 'tessdata');

function resolveTessdataDir() {
  // maybe initialize tessdata folder in app_data with the tessdata dir from vendor
  if(!fs.existsSync(tessdataPath)) {
    console.log('Initializing app tessdata folder:', tessdataPath);
    fse.copySync(vendor.tesseractData, tessdataPath)
  }

  return tessdataPath;
}

function requiresLangDownload(lang){
  const tessdataPath = resolveTessdataDir()

  const langFilename = lang + '.traineddata';
  const langFilePath = path.join(tessdataPath, langFilename);
  const downloadUrl = 'https://sejda-cdn.com/downloads/tessdata/' + langFilename;

  if (!fs.existsSync(langFilePath)) {
    console.log('Tesseract: language:', lang, 'needs download:', downloadUrl);
    return downloadUrl;
  }
}

function saveLangDownload(lang, arrayBuffer){
  const tessdataPath = resolveTessdataDir()

  const langFilename = lang + '.traineddata';
  const langFilePath = path.join(tessdataPath, langFilename);

  console.log('Tesseract: saving downloaded lang:', lang, 'to:', langFilePath);

  const buffer = Buffer.from(new Uint8Array(arrayBuffer));
  fse.outputFileSync(langFilePath, buffer);

  if(!fs.lstatSync(langFilePath).isFile()){
    throw new Error("Expected a file: " + langFilePath)
  }
}

module.exports = {
  resolveTessdataDir: resolveTessdataDir, 
  requiresLangDownload: requiresLangDownload,
  saveLangDownload: saveLangDownload,

  removeTessdataDir: function() {
    fse.removeSync(tessdataPath);
  }
};
