// components/deleteInput/deleteInput.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    type:{
      type:String,
      value:'text'
    },
    placeholder:{
      type:String,
      value:'占位字符'
    },
    name:{
      type:String,
    },
    value:{
      type:String,
      value:''
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    show:false,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    focus(){
      this.setData({
        show:true
      })
    },
    blur(){
      this.setData({
        show:false
      })
    },
    deleteTxt(){
      this.setData({
        value:''
      })
    },
    textChange(e){
      this.triggerEvent('input',e)
    }
  }
})
