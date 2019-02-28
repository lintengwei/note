## markdown语法说明


<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

* [markdown语法说明](#markdown语法说明)
	* [标题](#id)
	* [强调](#强调)
	* [列表](#列表)
		* [无序列表](#无序列表)
		* [有序列表](#有序列表)
	* [添加图片](#添加图片)
	* [链接](#链接)
	* [引用](#引用)
	* [分割线](#分割线)
	* [代码行数](#代码行数)
	* [任务列表](#任务列表)
	* [上标](#上标)
	* [下标](#下标)
	* [脚注](#脚注)
	* [标记](#标记)
	* [缩略](#缩略)
	* [表格](#表格)

<!-- /code_chunk_output -->


### 标题 {#id}

``` markdown
# 这是<h1> 一级标题 
## 这是<h2> 二级标题
### 这是<h3> 三级标题
#### 这是<h4> 四级标题
##### 这是<h5> 五级标题
###### 这是<h6> 六级标题
```

如果你想要给你的标题添加id或者class，请在标题最后添加  {#id .class1 .class2}。例如：

```markdown
# 这个标题有一个id {#l_id}
# 这个标题有2个class {.class1 .class2}
```

> 这是一个MPE扩展的特性

### 强调

``` markdown
*这是斜体的文字*
_这也是斜体的文字_

**这是粗体的文字**
__这也是粗体的文字__

~~横线删除的问题？~~
```

### 列表

#### 无序列表

```markdown
+ item 1
+ item 2
  + item a
  + item b
```

#### 有序列表

```markdown
1. item 1
2. item 2
3. item 3
```

### 添加图片

例子：
![show_text](https://cs-op.douyucdn.cn/dypart/2018/03/15/66435f2f10691ba07368b291bcb3f963.jpg)

```markdown
![alt text](img url)
```

### 链接

例子：
[GitHub](http://github.com)

```markdown
[展示字符](链接地址)
[GitHub](http://github.com)
```

### 引用

李白《静夜思》：
> 床前明月光
> 疑是地上霜
> 举头望明月
> 低头思故乡

```markdown
> 床前明月光
> 疑是地上霜
...
```

### 分割线

例子：
___
至少三个！！！！！！

```markdown
--- 连字符
*** 星号
___ 下划线
```

### 代码行数

例子：

``` javascript {.line-numbers}
function add(a,b){
  return a+b;
}
```

``` markdown
---markdown
function add(a,b){
  return a+b;
}
---
```

### 任务列表

例子：

- [x] item1_done
- [x] item2_done
- [ ] item3_doing

``` markdown
> - 后面需要空格 大括号未填充的需要补空格
- [x] item1_done
- [x] item2_done
- [ ] item3_doing
```

### 上标

例子：
20^262^

``` markdown
20^262^
```

### 下标

例子：
h~2~o

```markdown
h~2~o
```

### 脚注

以父之名[^1]
我还想她[^2]

[^1]:周杰伦
[^2]:林俊杰

```markdown
引用:
以父之名[^1]

脚注：
[^1]:周杰伦
```

### 标记

==这是一行有标记的字==

```markdown
==这是一行有标记的字==
```

### 缩略

例子：
*[HTML]: Hyper Text Markup Language

the HTML specification

```markdown
缩略注明：
*[HTML]: Hyper Text Markup Language

缩略引用 =>当鼠标放在title提示
the HTML specification
```

### 表格
| a   | b   |
| --- | --- |
| 1   | 2   |
|     | 3   |

```markdown
| a   | b   |
| --- | --- |
| 1   | 2   |
|     | 3   |
```