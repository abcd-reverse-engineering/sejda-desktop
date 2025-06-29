const username = require('username');
const os = require('os');

var cachedValue;

module.exports =  {
  get: function(callback) {
    if(cachedValue) {
      return callback(cachedValue)
    } else {
      username().then(function(username) {
        cachedValue = {
          hostname: os.hostname(),
          serial: '',
          username: username || ''
        }
        callback(cachedValue);
      });
    }
  }
}
