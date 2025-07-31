const i18n = require('i18n');
const fs = require("fs");

i18n.configure({
  locales:['fr', 'de', 'es', 'it', 'pt', 'ro', 'pl'],
  directory: __dirname + '/locales',
  updateFiles: false
});

const collectFilePath = __dirname + '/locales/_not_bundled_/strings.txt'

function collect(s){
  fs.appendFileSync(collectFilePath, s + '=\n');
}

const shouldCollect = process.env.SEJDA_DESKTOP_LOCALIZE_DEV_COLLECT === 'true';
if(shouldCollect){
  console.log('app-i18n collecting strings to file:', collectFilePath);
}

String.prototype.dropRight = function (len) {
  if (this.length < len) return this;

  return this.substr(0, this.length - len);
};

function init(app, appPrefs) {
  let appLocale = app.getLocale();
  if(appLocale && appLocale.indexOf('-') > 0) {
    appLocale = appLocale.substring(0, appLocale.indexOf('-'));
  }

  // allows tests to mock prefs
  appPrefs = appPrefs || rootRequire('prefs.js');
  
  const locale = appPrefs.get('lang') || appLocale || 'en';
  //console.log('Using locale:', locale);
  
  i18n.setLocale(locale);
  
  const self = {};
  
  // returns undefined if no translation found
  function translate(s){
    const v = i18n.__(s) || s;
    if(s === v) return undefined;
    else return v;
  }

  function translatePhrase(s) {
    // try without tokenization first
    const value = translate(s);
    if(typeof value !== 'undefined'){
      return value;
    }

    // try looking up without trailing dot
    if(!value && s.endsWith('.')) {
      const valueNoTrailingDot = translate(s.dropRight(1));
      if(valueNoTrailingDot) {
        return valueNoTrailingDot + '.'; // add the dot back
      }
    }

    // try tokenizing into sentences
    const SEPARATOR = '. ';
    const keys = s.split(SEPARATOR).map(function(s){ return s.trim() });

    // no separate tokens, just one
    if (keys.length === 1) {
      const value = translate(s);
      if(!value) {
        //console.warn('No translation for:', s);
        return s;
      }
    }

    // multiple tokens
    const values = keys.map(function(key){
      return translatePhrase(key);
    })

    return values.join(SEPARATOR);
  }
  
  self.__ = function(s) {
    if(shouldCollect) collect(s);
    
    const v = translatePhrase(s);
    //console.log(s + '=' + v);
    return v;
  };
  
  self.getLocale = function (){
    return locale;
  }
  
  return self;
}

module.exports = init
