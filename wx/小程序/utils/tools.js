const SystemInfo=wx.getSystemInfoSync()

module.exports={
  wx:{
    getWxVersion(){
      return SystemInfo.version
    },
    getSDKVersion(){
      return SystemInfo.SDKVersion
    },
    getPlatform(){
      return SystemInfo.platform
    }
  },
  size:{
    getScreenWidth(){
      return SystemInfo.screenWidth
    },
    getScreenHeight(){
      return SystemInfo.screenHeight
    },
    getWindowWidth(){
      return SystemInfo.windowWidth
    },
    getWindownHeight(){
      return SystemInfo.windowHeight
    },
    getStatusBarHeight(){
      return SystemInfo.statusBarHeight
    }
  }
}