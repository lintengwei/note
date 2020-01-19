- [grid](#grid)
  - [基本用法](#%e5%9f%ba%e6%9c%ac%e7%94%a8%e6%b3%95)
  - [可配置属性](#%e5%8f%af%e9%85%8d%e7%bd%ae%e5%b1%9e%e6%80%a7)
    - [容器属性](#%e5%ae%b9%e5%99%a8%e5%b1%9e%e6%80%a7)
    - [子项属性](#%e5%ad%90%e9%a1%b9%e5%b1%9e%e6%80%a7)
  - [FAQ](#faq)

# grid

## 基本用法

[[https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Grid_Layout/Basic_Concepts_of_Grid_Layout](https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Grid_Layout/Grid_Template_Areas)](<[https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Grid_Layout/Basic_Concepts_of_Grid_Layout](https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Grid_Layout/Grid_Template_Areas)>)
[https://www.html.cn/archives/8510](https://www.html.cn/archives/8510)

基本概念：

网格线

> 网格线组成了网格，包围各个网格的线称为网格线，可以根据网格线来设置子项的大小。==配合【grid-column-start，grid-row-start 等一起使用】==

网格轨道

> 两条网格线之间的空间，可以理解成表格里面的行或者列，网格里面为 grid-row 和 grid-column，网格轨道可以设置大小，来控制高度或者宽度。

网格单元格

> 四条网格线之间的空间，是最小的单位，值包含单个单元

网格区域

> 四条网格线组成的空间，可能包含一个或者多个单元格。


注意点：

1. 当放置元素时，我们使用 网格线 定位，而非 网格轨道。
2. 当为某个item设置了定位，其他未设置定位的item会依次的放进未被占据的网格


```css
.container {
  /* 定义容器为栅格布局 */
  display: grid;
  /* 定义每行多少列 ，可以指定列的宽度，auto表示自适应剩余的空间
    repeat(times,width)   循环定义
  */
  grid-template-columns: 100px auto repeat(3, 1fr) 20px;
  /* 定义每行的宽度 */
  grid-template-rows: 200px 40px;
  /*  设置网格的间隙，全局设置 */
  grid-gap: 20px;
}

/* 如之上的container 的定义，其三列两行，其行网格线为1，2，3，4
   列网格线为1，2，3。grid-column/grid-row的优先级高于正常布局
   当设置了grid-column/grid-row后，剩余的网格控件会排布该子项之后的item 
*/
.item {
  /* 横向占据位置，根据纵向网格线[column]划分 */
  grid-column-start: 1;
  grid-column-end: 3;
  /* 缩写 */
  grid-column: start/end;
  /* 纵向占据位置，根据横向网格线[row]划分 */
  grid-row-start: 1;
  grid-row-end: 3;
  /* 缩写 */
  grid-row: start/end;
  /* 全部合并 */
  grid-area: grid-row-start / grid-column-start / grid-row-end / grid-colun-end;
}

/* grid-area  grid-template-area的使用 */

.container {
  display: grid;
  /* 定义一个三列的布局 */
  grid-template-columns: 100px auto 200px;
  /* 重复写表示该元素占据多少列
      如下表示 header占据三列 footer占据两列
   */
  grid-template-areas: 'header header header' 'footer footer';
}

.header {
  grid-area: header;
}
.footer {
  grid-area: footer;
}
```

## 可配置属性

### 容器属性

- display
  - grid
    - 设置该容器为栅格布局
- grid-template-columns
  - <track-size> | <line-name>
  - 可以是长度值，百分比，或者等份网格容器中可用空间（使用 fr 单位）
  - fr 单位表示均分单位
- grid-template-rows
  - 同上
- grid-template-area
  - 配合 grid-area 一起使用
- grid-template
  - grid-template-rows/grid-template-columns
- grid-comlun-gap
  - 指定横向网格间隔
- grid-row-gap
  - 指定纵向网格间隔
- grid-gap
  - 指定横纵向网格间隔
- grid-auto-columns
  - 指定未具体设置的单元格的默认大小，当单元格超出的时候的使用值？
- grid-auto-rows
  - 指定未具体设置的单元格的默认大小
- grid-auto-flow
  - 指定未具体设置的单元格的默认大小
- justify-items
  - 对其子项到容器的横轴，就是设置每个子项的 justify-self
    - justify-item
      - 子项可以通过设置自己的 justify-self 来修改定位方式
- align-items
  - 规范中对 align-self 的默认行为是拉伸（stretch）。例外的情况是若项目具有固定宽高比，行为就改为与轴起点对齐（start
  - 对其子项到容器的列轴，就是设置每个子项的 align-self
  - align-self
    - 子项可以通过设置自己的 align-self 来修改定位方式
- place-items
  - place-self
- justify-content
  - 当使用非灵活布局（如使用 px 的绝对单位布局的时候，父级可能会多出剩余空间，该属性设置如何使用剩余空间）的时候可以指定轴线方向的布局
  - 类似于 flex justify-content
- align-content
  - 非轴线方向的布局
- place-content
  - justify-content/align-content 的简写

### 子项属性

==分界线是从1开始计数的==

- grid-column-start
  - 指定横向网格开始==分界线==
- grid-column-end
  - 指定横向网格结束分界线
- grid-column
  - start/end
  - 指定开始和结束分界线
  - ==如果只有一个参数，默认是该分界线之后一格==
  - ==span number 表示跨越几个轨道==
- grid-row-start
  - 指定纵向网格开始分界线
- grid-row-end
  - 指定纵向网格结束分界线
- grid-row
  - start/end
  - 指定开始和结束分界线
- grid-area
  - name | grid-row-start/grid-column-start/grid-row-end/grid-column-end
  - 指定名称和网格横纵向的分界线
  - 名称可以通过 grid-template-areas 引用
- justify-self
- align-self
- place-self

## FAQ

1. 显示网格和隐式网格

通过 grid-template-columns 和 grid-templte-rows 可以显示的指定网格布局。当未设置行或者列的时候，可以隐式的通过 grid-auto-columns 和 grid-auto-rows 来隐式的指定网格的大小

2. 当通过设置子项的显示空间的时候可能导致子项之间相互覆盖的问题？

可以通过设置 z-index 来指定子项的显示层级

3. 对自动定位的元素的定位原则？

如果某些子项已经设置了确切的定位位置。自动定位的项目将按在 DOM 中的顺序从已定位项目的前面开始摆放，虽然有两个项目已经事先定位好，但其他项目不是从已经定位的项目之后才开始摆放的。

4. 子项的对齐规则？
