// components/loadingView/loadingView.js

let btnFunc=null

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    top: {
      type: String,
      value: 100
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    show:false,
    text:'当前无记录',
    btnText:'跳转字符',
    showBtn:false,
    showEmpty:false,
    animation:null,
    animationContent:null,
    data:null,
    dataContent:null
  },

  /**
   * 组件的方法列表
   */
  methods: {
    showEmpty(showBtn, btnText,text,func){
      this.setData({
        showEmpty:true,
        showBtn,
        btnText,
        text
      })
      btnFunc=func
    },
    showLoading(){
      this.setData({
        showEmpty:false,
        show:false
      })
    },
    showContent(){
      this.setData({
        showEmpty:false,
        show:true,
      })
    },
    buttonClick(){
      btnFunc && btnFunc()
    }
  }
})
