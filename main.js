process.on('uncaughtException', function(err) {
  console.log('Uncaught exception', err.stack);
});

process.on('unhandledRejection', function(err) {
  console.log('Unhandled rejection', err.stack);
});

const electron = require('electron');
const app = electron.app;

const singleInstanceLock = app.requestSingleInstanceLock();

if (!singleInstanceLock) {
  console.log('Did not get the single instance lock, will quit');
  app.quit();
} else {
  global.rootRequire = function(name) {
    return require(__dirname + '/lib/' + name);
  };

// Handle EJS templates in Electron
  require(__dirname + '/lib/electron-ejs.js')({ __: function(text){ return text } });

  const _ = require('underscore');
  const fs = require('fs');

  const isWin = process.platform === 'win32';

  var path = require('path');
  if(isWin) path = path.win32;

  const crashDetect = rootRequire('crash-detect.js');
  const appCrashedLastStartup = crashDetect.initStartup();

  if(appCrashedLastStartup) {
    console.log('App crashed last startup');
  }

// Handles configuration passed via quiet installs
// Eg: enterprise deployments where the license key and output folder are pre-configured
// Installer will set environment variables, which the app detects on startup and updates config file
//LICENSE_KEY=1234-ABCD-1234-ABCD;LOCALE=fr;UPDATE_CHECK=false;DISABLED_FEATURES=edit.whiteout;EULA_ACCEPTED=;ERROR_REPORTING=
  const installerCfg = {};
  (process.env['SEJDA_DESKTOP_INSTALLER_CONFIG'] || '').split(';').forEach(function(s){
    if(s && s.indexOf('=') > 0){
      const fragments = s.split('=');
      const name = fragments[0];
      const value = fragments[1];
      installerCfg[name] = value;
    }
  });

  const prefs = rootRequire('prefs.js');
  function setPrefsFromInstallerCfgIfDefined(cfgName, prefName, varType){
    let value = installerCfg[cfgName]
    if(typeof value !== 'undefined' && value !== '') {

      if(varType === 'boolean'){
        value = value === 'true' || value === 'on';
      }

      const updates = {};
      updates[prefName] = value;
      prefs.set(updates);
    }
  }

  if(!prefs.get('licenseKey')) {
    setPrefsFromInstallerCfgIfDefined('LICENSE_KEY', 'licenseKey');
  }

  setPrefsFromInstallerCfgIfDefined('LOCALE', 'lang');
  setPrefsFromInstallerCfgIfDefined('UPDATE_CHECK', 'updateCheck');
  setPrefsFromInstallerCfgIfDefined('DISABLED_FEATURES', 'disabledFeatures');
  setPrefsFromInstallerCfgIfDefined('EULA_ACCEPTED', 'eulaAcceptedAdmin', 'boolean');
  setPrefsFromInstallerCfgIfDefined('AUTO_REPORT_ERRORS', 'autoReportErrorsAdmin', 'boolean');

  const manifest = rootRequire('../package.json');

  const screen = electron.screen;
  const Menu = electron.Menu;
  const shell = electron.shell;
  const BrowserWindow = electron.BrowserWindow;  // Module to create native browser window.
  const ipcMain = electron.ipcMain;

// work around GPU process isn't usable. Goodbye.
// https://stackoverflow.com/a/70840422/156300
  app.commandLine.appendSwitch('in-process-gpu');

  ipcMain.handle('font-scanner.getAvailableFonts', async (event, args) => {
    return await require('font-scanner').getAvailableFonts();
  });

  ipcMain.on('electron.app.getPath', (event, name) => {
    try {
      var result = '';
      try {
        result = app.getPath(name);
      } catch (e) {
        // fallback to home
        try {
          result = app.getPath('home');
        } catch (e) {
          // fallback to empty string
          result = '';
        }
      }
      event.returnValue = result;
    } catch (e) {
      console.warn(e);
      const userHome = process.env[(process.platform === 'win32') ? 'USERPROFILE' : 'HOME'];
      event.returnValue = userHome;
    }
  });

  ipcMain.on('electron.app.setTitle', (event, title) => {
    let window = BrowserWindow.getFocusedWindow() || appWindows[0];
    window.setTitle(title);
    event.returnValue = window.getTitle();
  });

  var isExternalLinkClicked = false;
  ipcMain.on('electron.app.externalLinkClicked', (event, args) => {
    isExternalLinkClicked = true;
    event.returnValue = true;
  });

  ipcMain.handle('electron.dialog.showOpenDialog', async (event, args) => {
    return await electron.dialog.showOpenDialog(args)
  });

  ipcMain.handle('electron.dialog.showSaveDialog', async (event, args) => {
    return await electron.dialog.showSaveDialog(args)
  });

  ipcMain.handle('electron.dialog.showMessageBox', async (event, args) => {
    return await electron.dialog.showMessageBox(args)
  });

  ipcMain.handle('electron.menu.popup', async (event, args) => {
    let menu = Menu.buildFromTemplate(args);
    let window = BrowserWindow.getFocusedWindow() || appWindows[0];
    menu.popup(window);
  });

// https://github.com/electron/electron/issues/13465
  app.commandLine.appendSwitch('disable-mojo-local-storage');

// app crashs when connecting/disconnecting external monitors with disable-gpu
// https://github.com/electron/electron/issues/39870

// Keep a global reference of the windows object, if you don't, the windows will
// be closed automatically when the JavaScript object is garbage collected.
  var appWindows = [];

  app.on('second-instance', (event, commandLine, workingDirectory) => {
    console.log(commandLine);
    openAppWindow(commandLine);
  })

// Quit when all windows are closed.
  app.on('window-all-closed', function() {
    app.quit();
  });

// Hmm, otherwise browser might 'crash' and local storage is not persisted
  process.on('exit', () => {
    app.quit();
  });

  app.on('before-quit', function () {
    appWindows.forEach(function(window){
      window.webContents.send('before-quit');
    })
  });

  function configureProxy(window, app) {
    var proxyServer = process.env['SEJDA_PROXY_SERVER'] || process.env['PDFSAM_PROXY_SERVER'];
    if(proxyServer) {
      console.log('Using proxy:', proxyServer);
      window.webContents.session.setProxy({
        proxyRules: proxyServer
      }, function(){
        return true;
      });

      var proxyUsername = process.env['SEJDA_PROXY_USERNAME'] || process.env['PDFSAM_PROXY_USERNAME'];
      var proxyPwd = process.env['SEJDA_PROXY_PASSWORD'] || process.env['PDFSAM_PROXY_PASSWORD'];

      if(proxyUsername && proxyPwd) {
        // proxy requires authentication
        console.log('Proxy requires authentication');
        app.on('login', function(event, webContents, request, authInfo, callback) {
          console.log('Performing proxy authentication');
          callback(proxyUsername, proxyPwd);
          event.preventDefault();
        });
      }
    }
  }

  var argumentFiles = [];
  var appReady = false;
  var appReadyOpenFilesTimeout;

  function __log(msg){
    setTimeout(function() {
      appWindows.forEach(function(window){
        window.webContents.send('main.log', JSON.stringify(msg));
      });
    }, 2000);
  }

  function openWithArgumentFiles() {
    var files = _.filter(argumentFiles, function(filePath) {
      return filePath !== 'main.js' /* dev mode */ && fs.existsSync(filePath);
    });

    if(files.length > 0) {
      var startPage = 'open-with.ejs?files=' + encodeURIComponent(JSON.stringify(files));
      var window = createWindow();
      window.loadURL('file://' + __dirname + '/views/' + startPage);

      __log(argumentFiles);

      argumentFiles = [];
      return true;
    }

    return false;
  }

  app.on('will-finish-launching', function() {
    // handle MacOS user opening a file with the app
    app.on('open-file', function(event, filePath) {
      event.preventDefault();

      argumentFiles.push(filePath);

      if(appReady) {
        // wait a bit for more files from the same batch to arrive, then open them
        clearTimeout(appReadyOpenFilesTimeout);
        appReadyOpenFilesTimeout = setTimeout(function() {
          openWithArgumentFiles();
        }, 500);
      }
    });
  });

// prevents opening links inside the app window
// forces external links in external browser
  app.on('web-contents-created', (event, contents) => {
    const { shell } = require('electron');
    const URL = require('url').URL;

    async function openExternalUrlIfRequired(event, navigationUrl) {
      try {
        const parsedUrl = new URL(navigationUrl);
        const externalUrl = parsedUrl.origin !== null && parsedUrl.origin !== 'null';

        if(externalUrl) {
          event.preventDefault();

          // we'll ask the operating system
          // to open this event's url in the default browser.
          // but only if we recognize the protocol
          const allowedProtocols = ['http:', 'https:', 'mailto:', 'tel:'];
          if(allowedProtocols.indexOf(parsedUrl.protocol) < 0) {
            // block opening of unknown protocol
            console.warn('Blocked ', navigationUrl);
            __log('Blocked URL: ' + navigationUrl);
            return;
          }

          if(['https://www.sejda.com', 'https://sejda.com', 'www.sejda.com', 'sejda.com'].includes(parsedUrl.origin)) {
            if(navigationUrl.includes("?")) {
              navigationUrl += "&ref=sejda-desktop"
            } else if(navigationUrl.includes("#")){
              // noop too complicated
            } else {
              navigationUrl += "?ref=sejda-desktop"
            }

            if(navigationUrl.includes("LICENSE_KEY")) {
              var key = rootRequire('prefs').get()['licenseKey'] || '';
              navigationUrl = navigationUrl.replaceAll('{LICENSE_KEY}', encodeURIComponent(key));
            }
          }

          await shell.openExternal(navigationUrl);
        }

      } catch (e) {
        console.log(e);
        event.preventDefault();
      }
    }

    contents.on('will-navigate', async (event, navigationUrl) => {
      await openExternalUrlIfRequired(event, navigationUrl);
    });
  })

  const defaultWindowOpts = {
    webPreferences: {
      sandbox: false /* preload.js access to nodejs api */,
      nodeIntegration: false,
      nodeIntegrationInSubFrames: false,
      enableRemoteModule: false,
      worldSafeExecuteJavaScript: true,
      contextIsolation: true,
      preload: path.join(__dirname, 'preload.js'),
      spellcheck: false
    },
    title: manifest.productName,
    icon: path.join(__dirname, '/views/public/images/icon_128_sejda.png')
  }

  function createWindowOptsInheritingDefaults(opts) {
    return Object.assign({}, defaultWindowOpts, opts);
  }

  function createWindow() {
    // Create the browser window.
    let window = new BrowserWindow(createWindowOptsInheritingDefaults({
      width: 1200, height: 800
    }));

    configureProxy(window, app);

    window.on('close', function() {
      try {
        const state = {};
        if (!window.isMaximized() && !window.isMinimized() && !window.isFullScreen()) {
          state.bounds = window.getBounds();
        }
        state.isMaximized = window.isMaximized();
        state.isFullScreen = window.isFullScreen();
        prefs.set({ 'windowState': JSON.stringify(state) });
      } catch (e) {}
    });

    // Emitted when the window is closed.
    window.on('closed', function() {
      // Dereference the window object, usually you would store windows
      // in an array if your app supports multi windows, this is the time
      // when you should delete the corresponding element.
      const index = appWindows.indexOf(window);
      if (index > -1) {
        appWindows.splice(index, 1);
      }
    });

    window.webContents.on('will-prevent-unload', (event) => {
      if(isExternalLinkClicked) {
        isExternalLinkClicked = false;
        return event.preventDefault()
      }

      let choice = electron.dialog.showMessageBoxSync(window, {
        type: 'question',
        buttons: ['Leave', 'Stay'],
        title: 'Leave this page?',
        message: 'Changes you made may not be saved.',
        defaultId: 0,
        cancelId: 1
      });
      let leave = (choice === 0);
      if (leave) {
        event.preventDefault()
      }
    });

    window.webContents.setWindowOpenHandler(({ url }) => {
      let allowed = false;
      let navigationUrl = url;

      try {
        const parsedUrl = new URL(navigationUrl);
        if((parsedUrl.origin === null || parsedUrl.origin === 'null') && parsedUrl.protocol === 'file:') {
          allowed = true;
        }

      } catch (e) {
        console.error(e);
        __log('Error: ' + e);
      }

      if (allowed) {
        return {
          action: 'allow',
          overrideBrowserWindowOptions: createWindowOptsInheritingDefaults({})
        }
      }

      console.warn('Denied: ', navigationUrl);
      __log('Denied: ' + navigationUrl);
      return { action: 'deny' }
    })

    appWindows.push(window);

    return window;
  }

  function openAppWindow(argv) {
    // handle Windows/Linux user opening a file with the app
    if(argv.length >= 2) {
      argumentFiles = argumentFiles.concat(argv.slice(1, argv.length));
    }

    if(argumentFiles[0] === 'main.js') {
      argumentFiles.splice(0, 1);
    }

    //argumentFiles.push("/path/to/doc.pdf");

    if(openWithArgumentFiles()) {
      // noop
    } else {
      // open with the last used task, or home screen as default
      var startPage = rootRequire('prefs').get('startPage') || 'index.ejs';
      var startPageExists = fs.existsSync(path.join(__dirname, 'views', startPage));
      // check that page still exists (maybe it was removed in the latest app update)
      if (!startPageExists) {
        startPage = "index.ejs"
      }
      let window = createWindow();
      window.loadURL('file://' + __dirname + '/views/' + startPage);
    }
  }

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
  app.on('ready', function() {

    openAppWindow(process.argv);

    const appMenuUpdater = rootRequire('app-menu.js').init(app, shell, Menu);
    ipcMain.on('electron.menu.pageActions', (event, args) => {
      appMenuUpdater.updatePageActions(args)
      event.returnValue = true;
    });

    const i18n = rootRequire('app-i18n.js')(app);
    ipcMain.on('electron.app.i18n.__', (event, s) => {
      event.returnValue = i18n.__(s);
    });

    appReady = true;

    if(!appCrashedLastStartup) {
      // restore window state/size
      try {
        let window = BrowserWindow.getFocusedWindow() || appWindows[0];

        const windowStatePrefs = prefs.get('windowState');
        if(windowStatePrefs){
          const windowState = JSON.parse(windowStatePrefs);
          function windowWithinBounds(state, bounds) {
            return (
              state.x >= bounds.x &&
              state.y >= bounds.y &&
              state.x + state.width <= bounds.x + bounds.width &&
              state.y + state.height <= bounds.y + bounds.height
            );
          }

          if(windowState.isMaximized) {
            if(windowState.isMaximized) {
              window.maximize();
            }
          } else {
            let offset = appWindows.length * 5;
            windowState.bounds.x += offset;
            windowState.bounds.y += offset;

            const visible = screen.getAllDisplays().some(display => {
              return windowWithinBounds(windowState.bounds, display.bounds);
            });

            if(visible) {
              window.setBounds(windowState.bounds);
            } else {
              console.warn('Window state not restored, would be off screen');
            }
          }
        }
      } catch (e) {
        console.log(e)
      }
    }

    crashDetect.markStartupOk();
  });

  // Enable debug tools
  require('electron-debug')({ isEnabled: true, showDevTools: false, devToolsMode: 'bottom'});
}
