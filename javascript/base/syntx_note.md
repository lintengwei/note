# javascript语法注意点

> 变量提升

```javascript
function test(){
  console.log(a)  //  undefined
  let a=1
}
//  ===
function test(){
  let a
  console.log(a)
  a=1
}
```

> 函数声明提升，函数表达式不提升

```javascript
test()
function test(){}

foo() //  Error，foo is not a function
let foo=function(){}
```

> ===和==

[https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Equality_comparisons_and_sameness](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Equality_comparisons_and_sameness)

```javascript
undefined==false  //  false
null=false  //  false
```

> javascript的特殊字符

\0	Null字节
\b	退格符
\f	换页符
\n	换行符
\r	回车符
\t	Tab (制表符)
\v	垂直制表符
\'	单引号
\"	双引号
\\	反斜杠字符（\）
\XXX	由从0到377最多三位八进制数XXX表示的 Latin-1 字符。例如，\251是版权符号的八进制序列。
\xXX	由从00和FF的两位十六进制数字XX表示的Latin-1字符。例如，\ xA9是版权符号的十六进制序列。
\uXXXX	由四位十六进制数字XXXX表示的Unicode字符。例如，\ u00A9是版权符号的Unicode序列。见Unicode escape sequences (Unicode 转义字符).
\u{XXXXX}	Unicode代码点 (code point) 转义字符。例如，\u{2F804} 相当于Unicode转义字符 \uD87E\uDC04的简写。