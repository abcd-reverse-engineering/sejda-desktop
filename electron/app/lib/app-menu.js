function createWebContentDispatcher(id) {
  return function (item, focusedWindow) {
    if (focusedWindow)
      focusedWindow.webContents.send('main.appMenuClicked', id);
  }
}

function capitalizeEachWord(s) {
  return s.toLowerCase().split(' ').map(word => word.charAt(0).toUpperCase() + word.substring(1)).join(' ');
}

function createMenuTemplate (app, pageActions) {
  const isMac = process.platform === 'darwin';
  const name = app.name;

  const i18n = rootRequire('app-i18n.js')(app);
  const __ = i18n.__;
  
  const template = [{
    label: 'File',
    submenu: [
      {
        label: capitalizeEachWord(__('Home screen')),
        click: createWebContentDispatcher('file-home')
      },
      {
        label: __('Settings'),
        click: createWebContentDispatcher('file-settings')
      },
      {
        type: 'separator'
      },
      {
        label: __('Purchase'),
        click: createWebContentDispatcher('file-purchase')
      },
      {
        label: capitalizeEachWord(__('Enter license key')),
        click: createWebContentDispatcher('file-enter-license-key')
      },
    ]
  },
    {
      label: 'Edit',
      submenu: [
        {
          label: 'Cut',
          accelerator: 'CmdOrCtrl+X',
          role: 'cut'
        },
        {
          label: 'Copy',
          accelerator: 'CmdOrCtrl+C',
          role: 'copy'
        },
        {
          label: 'Paste',
          accelerator: 'CmdOrCtrl+V',
          role: 'paste'
        },
        {
          label: 'Select All',
          accelerator: 'CmdOrCtrl+A',
          role: 'selectall'
        },
      ]
    },
    {
      label: 'Window',
      role: 'window',
      submenu: [
        {
          label: 'Minimize',
          accelerator: 'CmdOrCtrl+M',
          role: 'minimize'
        },
        {
          label: 'Close',
          accelerator: 'CmdOrCtrl+W',
          role: 'close'
        },
      ]
    },
    {
      label: __('Help'),
      submenu: [
        {
          label: __('Contact Support'),
          click: createWebContentDispatcher('file-contact-support')
        },
        {
          label: __('About') + ' ' + name,
          click: createWebContentDispatcher('file-about')
        }
      ]
    },
  ];
  
  if(pageActions && Array.isArray(pageActions) && pageActions.length > 0) {
    template[0].submenu.unshift({
      type: 'separator'
    });
    
    for(var i = pageActions.length - 1; i >= 0; i--) {
      const pageAction = pageActions[i];
      if(!pageAction.label || !pageAction.id){
        console.warn('Ignoring invalid page action:', pageAction);
        continue;
      }
      
      template[0].submenu.unshift({
        label: pageAction.label,
        click: createWebContentDispatcher(pageAction.id)
      });
    }
  }

  if (isMac) {
    template.unshift({
      label: name,
      submenu: [
        {
          label: __('About') + ' ' + name,
          click: createWebContentDispatcher('file-about')
        },
        {
          label: __('Settings'),
          click: createWebContentDispatcher('file-settings')
        },
        {
          type: 'separator'
        },
        {
          label: 'Services',
          role: 'services',
          submenu: []
        },
        {
          type: 'separator'
        },
        {
          label: 'Hide ' + name,
          accelerator: 'Command+H',
          role: 'hide'
        },
        {
          label: 'Hide Others',
          accelerator: 'Command+Shift+H',
          role: 'hideothers'
        },
        {
          label: 'Show All',
          role: 'unhide'
        },
        {
          type: 'separator'
        },
        {
          label: __('Quit'),
          accelerator: 'Command+Q',
          click: function () {
            app.quit();
          }
        },
      ]
    });
    const windowMenu = template.find(function (m) {
      return m.role === 'window'
    })
    if (windowMenu) {
      windowMenu.submenu.push(
        {
          type: 'separator'
        },
        {
          label: 'Bring All to Front',
          role: 'front'
        }
      );
    }
  }
  
  return template;
}

function setAppMenu(Menu, appMenuTemplate) {
  Menu.setApplicationMenu(Menu.buildFromTemplate(appMenuTemplate));
}

module.exports = {
  init: function(app, shell, Menu) {
    setAppMenu(Menu, createMenuTemplate(app));
    
    return  {
      updatePageActions: function(pageActions){
        setAppMenu(Menu, createMenuTemplate(app, pageActions));
      }
    }
  }
}
