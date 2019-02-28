const query=wx.createSelectorQuery()

function createQuery(component){
  let query= wx.createSelectorQuery()
  if (component){
    return query.in(component)
  }
  return query
}

function createIntersectionObserver(){
  return wx.createIntersectionObserver()
}

module.exports={
  dom:{
    /**
   * 查询第一个匹配的wxml节点的信息
   */
    select(dom, callback, component) {
      createQuery(component).select(dom).boundingClientRect(callback).exec()
    },
    /**
     * 查询所有匹配的wxml节点信息，结果以数组返回
     */
    selectAll(dom, callback, component) {
      createQuery(component).selectAll(dom).boundingClientRect(callback).exec()
    },
    /**
     * 查询视口的信息
     */
    selectViewport(callback, component) {
      createQuery(component).selectViewport().boundingClientRect(callback).exec()
    },
    /**
     * 查询滚动视图的信息，只能是viewport scroll-view
     */
    getScrollOffset(callback, dom, component) {
      let query = createQuery(component)
      if (dom) {
        query.select(dom).scrollOffset(callback).exec()
      } else {
        query.selectViewport().scrollOffset(callback).exec()
      }
    }
  },
  observe:{
    toDom(sourceDom, targetDom,callback,margins){
      createIntersectionObserver().relativeTo(sourceDom, margins).observe(targetDom,callback)
    },
    toViewport(targetDom,callback,margins){
      createIntersectionObserver().relativeToViewport(margins).observe(targetDom,callback)
    }
  }
}