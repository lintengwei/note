- [electron](#electron)
  - [基本使用](#%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)
  - [api](#api)
    - [app](#app)
    - [BrowserWindow](#browserwindow)

# electron

[https://electronjs.org/](https://electronjs.org/)

## 基本使用

```javascript
const { app, BrowserWindow } = require('electron')

function createWindow() {
  //  BrowserWindow 用于创建窗口来加载网页
  let window = new BrowserWindow()
  window.loadFile('index.html')
}

//  app是应用的核心，用于控制应用的生命周期
app.on('ready', createWindow)
```

## api

### app

### BrowserWindow
