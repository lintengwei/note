# Document Object Model - 文档对象模型

## 树形关系图

* Object
  * EventTarget
    * Node
      * Document
      * Element

## EventTarget -

事件对象接口，直接集成自 Object，Element，Document，Window 是最常见的实现了该接口的对象。需要实现的三个方法：

* addEventListener(eventType,func)
* removeListener(eventType,func)
* dispatchEvent(eventType)

## NodeList/HTMLCollection
