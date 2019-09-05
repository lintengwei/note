// components/lazyload/lazyload.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    url:{
      type:String
    },
    sty:{
      type: String
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    loaded:false,
    startload:false,
    observe:null
  },

  /**
   * 组件的方法列表
   */
  methods: {
    createObserve(){
      let observe = this.createIntersectionObserver()
      observe.relativeToViewport({ bottom: 0 }).observe('.img', (res) => {
          this.setData({
            startload:true,
            observe
          })
      })
    },
    loaded(){
      if (this.data.startload){
        this.setData({
          loaded: true
        })
        if (this.data.observe){
          this.data.observe.disconnect()
          this.data.observe=null;
        }
      }
    }
  },
  ready(){
    this.createObserve()
  }
})
