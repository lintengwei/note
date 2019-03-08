- [tsconfig](#tsconfig)
  - [compilerOptions](#compileroptions)
  - [files](#files)
  - [include/exclude](#includeexclude)

# tsconfig

## compilerOptions

> Object

可以被忽略，这时编译器会使用默认值。[编译选项](https://www.tslang.cn/docs/handbook/compiler-options.html)

- module
- noImplicitAny
- removeComments
- preserveConstEnums
- sourceMap
- lib
  - 编译期间导入的扩展库

## files

> Array

指定一个包含相对或绝对文件路径的列表

## include/exclude

> Array

"include"和"exclude"属性指定一个文件 glob 匹配模式列表。 支持的 glob 通配符有：

- 匹配 0 或多个字符（不包括目录分隔符）
- ? 匹配一个任意字符（不包括目录分隔符）
- \*\*/ 递归匹配任意子目录

```json
{
  "compilerOptions": {
    "module": "commonjs",
    "noImplicitAny": true,
    "removeComments": true,
    "preserveConstEnums": true,
    "sourceMap": true
  },
  "files": [
    "core.ts",
    "sys.ts",
    "types.ts",
    "scanner.ts",
    "parser.ts",
    "utilities.ts",
    "binder.ts",
    "checker.ts",
    "emitter.ts",
    "program.ts",
    "commandLineParser.ts",
    "tsc.ts",
    "diagnosticInformationMap.generated.ts"
  ],
  "include": ["src/**/*"],
  "exclude": ["node_modules", "**/*.spec.ts"]
}
```
