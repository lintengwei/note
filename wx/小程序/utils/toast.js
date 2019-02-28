const ERROR_IMAGE_PATH='/assets/ic_error.png'
const WARN_IMAGE_PATH='/assets/ic_warn.png'


module.exports={
  success(msg){
    wx.showToast({
      title: msg,
    })
  },
  error(msg){
    wx.showToast({
      title: msg,
      image:ERROR_IMAGE_PATH
    })
  },
  warn(msg){
    wx.showToast({
      title: msg,
      image:WARN_IMAGE_PATH
    })
  }
}