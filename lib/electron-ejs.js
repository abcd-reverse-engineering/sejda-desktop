//Import electron
var electron = require('electron');
var app = electron.app;

//Import dependencies
var fs = require('fs');
var path = require('path');
var urls = require('url');
var ejs = require('ejs');
var mime = require('mime');
const htmlparser2 = require('htmlparser2');
const domutils = require('domutils');

//Main function
function ElectronEjs(options) {
  //Check options
  if(typeof options === 'undefined') { options = {}; }

  // locals
  const manifest = require(__dirname + '/../package.json')

  //App ready event
  app.on('ready', function() {
    //Import protocol
    var protocol = electron.protocol;

    //Intercept the file protocol
    protocol.interceptBufferProtocol('file', function(request, callback){

      //Get the file
      var file = filenameFrom(request.url);

      //Get the file extension
      var extension = path.extname(file);

      //Check the extension
      if(extension === '.ejs') {

        //Add the path to options
        options.filename = file;

        const i18n = rootRequire('app-i18n.js')(app)
        
        // locals
        options.manifest = manifest;
        options.locale = i18n.getLocale();
        options.__ = i18n.__;

        // post-processor for <lift:locS> and related tags
        const EJSPostProcessor = function(html, callback){
          const start = Date.now();

          const parser = new htmlparser2.Parser(new htmlparser2.DomHandler((error, dom) => {
            if (error) {
              console.error(error);
              return;
            }

            // process all <lift:locS>
            domutils.findAll(el => el.type === 'tag' && (el.name === 'lift:locs' || el.name === 'lift:locS'), dom).forEach(el => {
              if (el.parent && el.children && el.children.length > 0 && el.children[0]) {
                const textNode = el.children[0];
                if (textNode.type === 'text') {
                  textNode.data = i18n.__(textNode.data);
                  domutils.replaceElement(el, textNode);
                }
              }
            });

            function hasClassName(el, className) {
              return el.attribs && el.attribs.class && el.attribs.class.split(' ').includes(className);
            }

            // process all 'lift:locTooltip'
            domutils.findAll(el => el.type === 'tag' && hasClassName(el, 'lift:locTooltip'), dom).forEach(el => {
              el.attribs.title = i18n.__(el.attribs.title);
              el.attribs.class = el.attribs.class.replaceAll('lift:locTooltip', '');
            });

            // process images with src='/images/' -> src='public/images/'
            ['src', 'data-src'].forEach(attrName => {
              domutils.findAll(el => el.type === 'tag' && el.name === 'img' && el.attribs && el.attribs[attrName] && el.attribs[attrName].startsWith('/images/'), dom).forEach(el => {
                el.attribs[attrName] = 'public' + el.attribs[attrName];
              });  
            })

            //console.log('Post-processing took:' +  (Date.now() - start) + ' ms');
            callback(domutils.getOuterHTML(dom));
          }));

          parser.write(html);
          parser.end();
        }

        //Get the file content
        fs.readFile(file, 'utf-8', (err, content) => {
          if (err) return console.error(err);
          
          //Get the full file
          var full = ejs.render(content, options);
          EJSPostProcessor(full, function(final){
            return callback({data: Buffer.from(final), mimeType:'text/html'});
          })
        });
        
      } else {
        // hack for electron not handling UNC paths
        
        var realPath = file;
        if(realPath.startsWith("A:")) {
          realPath = '\\\\' + realPath.substring(3);
        }
        
        fs.readFile(realPath, (err, content) => {
          if (err) return console.error(err);
          
          callback({ data: content, mimeType: mime.getType(extension) });  
        });
      }
    });
  });
}

function filenameFrom(url) {
  var p = urls.parse(url);

  if(process.platform === 'win32' && p.pathname.indexOf('/') === 0) {
    return decodeURIComponent(p.pathname.substring(1))
  }

  return decodeURIComponent(p.pathname);
}

//Exports to node
module.exports = ElectronEjs;
