# util

## uril.callbackify(asyncFunction/Promise)
  - 将 async 异步函数(或者一个返回值为 Promise 的函数)转换成遵循异常优先的回调风格的函数，例如将 (err, value)

## util.deprecate(fn, msg[, code])
  - 函数弃用声明方法

## util.format(format[, ...args])
  - %s - 字符串。
  - %d - 数值（整数或浮点数）。
  - %i - Integer.
  - %f - Floating point value.
  - %j - JSON。如果参数包含循环引用，则用字符串 '[Circular]' 替换。
  - %o - Object. A string representation of an object with generic JavaScript object formatting. Similar to util.inspect() with options { showHidden: true, depth: 4, showProxy: true }. This will show the full object including non-enumerable symbols and properties.
  - %O - Object. A string representation of an object with generic JavaScript object formatting. Similar to util.inspect() without options. This will show the full object not including non-enumerable symbols and properties.
  - %% - 单个百分号（'%'）。不消耗参数。
  - note
    - 参数多余占位符，以空格加入末端
    - 参数少于占位符，以字符串格式显示占位符

## util.inspect(object[, options])
  - util.inspect() 方法返回 object 的字符串表示，主要用于调试。 附加的 options 可用于改变格式化字符串的某些方面。
  - options
    - showHidden <boolean> 如果为 true，则 object 的不可枚举的符号与属性也会被包括在格式化后的结果中。 默认为 false。
    - depth <number> 指定格式化 object 时递归的次数。 这对查看大型复杂对象很有用。 默认为 2。 若要无限地递归则传入 null。
    - colors <boolean> 如果为 true，则输出样式使用 ANSI 颜色代码。 默认为 false。 颜色可自定义，详见自定义 util.inspect 颜色。
      - number - yellow
      - boolean - yellow
      - string - green
      - date - magenta
      - regexp - red
      - null - bold
      - undefined - grey
      - special - cyan （暂时只用于函数）
      - name - （无样式）
    - customInspect <boolean> 如果为 false，则 object 上自定义的 inspect(depth, opts) 函数不会被调用。 默认为 true。
    - showProxy <boolean> 如果为 true，则 Proxy 对象的对象和函数会展示它们的 target 和 handler 对象。 默认为 false。
    - maxArrayLength <number> 指定格式化时数组和 TypedArray 元素能包含的最大数量。 默认为 100。 设为 null 则显式全部数组元素。 设为 0 或负数则不显式数组元素。
    - breakLength <number> 一个对象的键被拆分成多行的长度。 设为 Infinity 则格式化一个对象为单行。 默认为 60。

## util.promisify(original)
- 把异步函数变成Promise格式的形式
  
  ## Class: util.TextEncoder

  - encode(input)
    - 转换成十进制的数字编码