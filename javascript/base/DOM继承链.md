# 继承链

## EventTarget

methods:

- addEventListener
- removeEventListener
- dispatchEvent

## Node Extends EventTarget

static:

| 常量                             | 值  | 描述                                                                                    |
| -------------------------------- | --- | --------------------------------------------------------------------------------------- |
| Node.ELEMENT_NODE                | 1   | An Element node such as <p> or <div>.                                                   |
| Node.TEXT_NODE                   | 3   | The actual Text of Element or Attr.                                                     |
| Node.CDATA_SECTION_NODE          | 4   | A CDATASection.                                                                         |
| Node.PROCESSING_INSTRUCTION_NODE | 7   | A ProcessingInstruction of an XML document such as <?xml-stylesheet ... ?> declaration. |
| Node.COMMENT_NODE                | 8   | A Comment node.                                                                         |
| Node.DOCUMENT_NODE               | 9   | A Document node.                                                                        |
| Node.DOCUMENT_TYPE_NODE          | 10  | A DocumentType node e.g. <!DOCTYPE html> for HTML5 documents.                           |
| Node.DOCUMENT_FRAGMENT_NODE      | 11  | A DocumentFragment node.                                                                |

properties:

- childNodes
  - 返回子节点的类数组【NodeList】
- firstChild
  - 返回第一个子节点
  - firstElementChild，【Element，Document】有该属性，返回第一个 nodeType===1 的节点
- lastChild
  - 返回最有一个子节点
  - lastElementChild，【Element，Document】有该属性，返回第一个 nodeType===1 的节点
- previousSibling
  - 返回前一个节点
- nextSiblint
  - 返回下一个节点
- parentNode
  - 返回父节点
- parentElement
  - 返回父元素
- textContent 
  - 返回文本
  - innerHTML
- baseURI
- baseURLObject

methods:

- appendChild
- removeChild
- replaceChild
- cloneNode
- getRootNode
- isEqualNode

### Element Extends Node

### Document Extends Node

### Attr Extends Node
