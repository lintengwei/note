# Document Object Model - 文档对象模型

在 html 文档中，任何的东西都呗当做节点。包括有文档节点 document，元素节点（标签 p，div 等），注释节点 comment（<!---->），属性节点（id='tt'）。

document 作为最上层的节点，管理这全局的节点。只有 document 可以创建其他任何的节点，包括（element，attr，comment 等）

## 树形关系图

- Object
  - EventTarget
    - Node
      - Element
        - nodeType===1
        - HTMLElement
          - HTMLDivElement
          - HTMLButtenElement
          - ...
      - Attr
        - nodeType===2
      - Document
        - 9
      - DocumentFragment
        - 11
      - Comment
        - 8

## EventTarget -

事件对象接口，直接集成自 Object，Element，Document，Window 是最常见的实现了该接口的对象。需要实现的三个方法：

- addEventListener(eventType,func)
- removeListener(eventType,func)
- dispatchEvent(eventType)

## Document
