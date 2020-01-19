# resolve定义模块解析规则

```javascript
module.exports={
  resolve:{
    //  定义模块解析别名
    //  键作为解析时候的引用，值是文件真实位置
    //  绝对目录和相对目录是针对当前文件的文职，其余都是相对于【node_modules】或者父目录的【node_modules】来查找模块
    //  后面可以带【$】表示精确匹配
    alias:Object,
    alias:{

    },

    //  指定解析规范
    aliasFields:String,

    //  是否
    cacheWithContext:Boolean,

    //  
    descriptionFiles:Array,

    //  是否强制文件扩展名
    enforceExtension:Boolean,

    //  对模块是否需要使用的扩展（例如 loader），默认false
    enforceModuleExtension:Boolean,

    //  自动解析时候可用的扩展
    entensions:Array,

    //  当从npm包中导入模块的时候，该字段决定导入的是哪个文件
    //  当target为webworker时，默认值 browser|module|main
    //  当target为其他的时候，默认值为 modlue|main
    //  优先级从前往后
    mainFields:Array,

    //  解析目录时要使用的文件名
    mainFiles:Array,

    //  告诉webpack接续目录时候应该搜索的目录
    modules:Array,

    //  启用，会主动缓存模块，但并不安全。传递 true 将缓存一切
    unsafeCache:Regex | Array | Boolean,

    //  
    plugins:Array,

    // 
    symlinks:Boolean,

    //  
    cachePredicate:Function,

    resolveLoader:Object,
    resolveLoader:{
      moduleExtensions:Array
    },
  }
}
```