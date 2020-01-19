- [css基本使用](#css%e5%9f%ba%e6%9c%ac%e4%bd%bf%e7%94%a8)
  - [关于文本](#%e5%85%b3%e4%ba%8e%e6%96%87%e6%9c%ac)
  - [可替换元素](#%e5%8f%af%e6%9b%bf%e6%8d%a2%e5%85%83%e7%b4%a0)

# css基本使用

## 关于文本

```css
p{
  /**
   * overflow wrap css属性应用于内联元素，设置浏览器是否应在不可断字符串中插入换行符，以防止文本溢出其行框。
   * */
  word-wrap:normal|break-word;
  overflow-wrap:normal|break-word;

  /**
   * 定义当文本超出内容盒子的时候的换行方式
   * normal 正常模式，该模式下遇到空格符，当内容区不能容下空格符两边的文本的时候，会把后面的文本换行，如果某个文本（不存在空格）超过内容区不会换行
   * break-all  当超出内容区的时候换行。但是不会1以单词作为换行一句，而是最大限度的排布，所以对于英文而言，有可能会打断某个单词的
   * keep-all 分词对于中文，日文，韩文，当超过内容区的时候会换行，但是对于英文文本会按照normal方式
   * break-word 当超出内容区的时候换行。跟break-all唯一的不同就是该模式下会以单词作为换行的依据，不存在单个单词分在两行的情况
   * */
  word-break:normal|break-all|keep-all|break-word;


  /* 小写|大写|首字母大写 */
  text-transform: lowercase|uppercase|capitalize;
  /* 指定元素是否保留文本间的空格、换行；超出边界是否换行
   * normal：默认处理方式。会将序列的空格合并为一个，内部是否换行由换行规则决定，默认换行符不起作用。
   * pre：原封不动的保留你输入时的状态，空格、换行都会保留，并且当文字超出边界时不换行。等同 pre 元素效果
   * nowrap：与normal值一致，不同的是会强制所有文本在同一行内显示。
   * pre-wrap：与pre值一致，不同的是文字超出边界时将自动换行。
   * pre-line：与normal值一致，但是会保留文本输入时的换行。
   * */
  white-space: normal|pre|nowrap|pre-wrap|pre-line;

  /* 设置文本最后一行的对齐方式 兼容性存在问题 */
  text-align-last:auto|start|end|left|right|justify
}

/* 限制文本的行数，需要四个属性一起使用，ie下无效，其他都可以 */
p{
  width: 300px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}
```

## 可替换元素

```css
.img{
  /* object-fit CSS 属性指定可替换元素的内容应该如何适应到其使用的高度和宽度确定的框。
    fill:拉伸或者压缩以满足元素的大小
    containe:保持比例压缩以显示全部
    cover: 会等比例拉伸以填充容器大小，可能会导致部分不可见
    scale-down:内容的尺寸与 none 或 contain 中的一个相同，取决于它们两个之间谁得到的对象尺寸会更小一些。
    none:被替换的内容将保持其原有的尺寸
   */
  object-fit:contain|cover|fill|scale-down|none
}
```
