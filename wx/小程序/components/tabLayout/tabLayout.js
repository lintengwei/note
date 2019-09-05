// components/tabLayout/tabLayout.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    tabList: {
      type: Array,
      value: ['标题', '标题']
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    currIndex: 0
  },

  /**
   * 组件的方法列表
   */
  methods: {
    changeTab(e) {
      let index = e.currentTarget.dataset.index;
      this.changeIndex(index);
      this.triggerEvent('on-tab-item-click', index);
    },
    changeIndex(index) {
      this.setData({
        currIndex: index
      })
    }
  }
})
