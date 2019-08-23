- [css基本使用](#css%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)
  - [关于文本](#%E5%85%B3%E4%BA%8E%E6%96%87%E6%9C%AC)
  - [关于动画](#%E5%85%B3%E4%BA%8E%E5%8A%A8%E7%94%BB)
  - [关于过渡](#%E5%85%B3%E4%BA%8E%E8%BF%87%E6%B8%A1)

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

## 关于动画

## 关于过渡
