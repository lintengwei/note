- [typescript 的使用](#typescript-%E7%9A%84%E4%BD%BF%E7%94%A8)
  - [安装](#%E5%AE%89%E8%A3%85)
  - [概念](#%E6%A6%82%E5%BF%B5)
  - [编译](#%E7%BC%96%E8%AF%91)
  - [基本使用](#%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)
    - [接口](#%E6%8E%A5%E5%8F%A3)
    - [函数](#%E5%87%BD%E6%95%B0)
  - [重要更新](#%E9%87%8D%E8%A6%81%E6%9B%B4%E6%96%B0)
  - [FAQ](#faq)

# typescript 的使用

[https://www.tslang.cn/docs/home.html](https://www.tslang.cn/docs/home.html)
[https://github.com/Microsoft/TypeScript](https://github.com/Microsoft/TypeScript)
[https://jkchao.github.io/typescript-book-chinese/#why](https://jkchao.github.io/typescript-book-chinese/#why)

## 安装

```bat
rem 全局安装typescript编译器
cnpm install -g typescript
yarn add -g typescript
rem 本地安装typescrpit编译器
cnpm install --save-dev typescript
rem 查看typescript版本
tsc -v
```

## 概念

> 类型声明和变量声明

- 类型声明
  - class
  - interface
  - type
- 变量声明
  - var
  - let
  - const
  - class

> 模块

不会暴露变量到全局，通过 commonjs、amd 等模块系统来进行模块的管理。可以使用模块依赖！

> 命名空间

会暴露一个变量到全局，使用闭包。 如果命名空间需要引入其他模块，则最后也会被编译成一个模块，不在是命名空间！

> 类型别名

使用 type 关键字来定义类型别名。
类型别名会给一个类型起个新名字。 类型别名有时和接口很像，但是可以作用于原始值，联合类型，元组以及其它任何你需要手写的类型。

1. type 和 interface 的区别是什么?

[https://www.tslang.cn/docs/handbook/advanced-types.html](https://www.tslang.cn/docs/handbook/advanced-types.html)

- interface 会创建新的类型，type 不会
- interface 可以继承和实现，type 不能

> 枚举

## 编译

配置编译文件 tsconfig.json

```json
{}
```

## 基本使用

### 接口

```typescript
interface Shape {
  width: number
  readonly text: string //  只读属性，必须在初始化是指定值
  height: number //  必须属性
  color?: string //  可选属性
  [propName: string]: string //  其他属性，如果未定义，只能包含必须是属性和可选属性，否则编译不了
  (start: number, end: number): string //  函数类型，描述函数的参数和返回值
}

//  可索引的类型
//  与使用接口描述函数类型差不多，我们也可以描述那些能够“通过索引得到”的类型，比如a[10]或ageMap["daniel"]
//  Typescript支持两种索引签名：字符串和数字。 可以同时使用两种类型的索引，但是数字索引的返回值必须是字符串索引返回值类型的子类型。
//  index 的key值最后会转换为string来索引，所以其对应的value必须是[key:string]:number的子类，否则编译过不了
interface StringArray {
  [index: number]: string
  [key: string]: number
}

//  类类型
//  构造器签名
//  第二种方法是使用类表达式
interface PersonConstructor {
  new (name: string, age: number): PersonConstructor
}
interface PersonInterface {
  sayHi(msg: string): void
  run(): void
}
class PPerson implements PersonInterface {
  name: string
  age: number
  constructor(name: string, age: number) {
    this.name = name
    this.age = age
  }
  sayHi(msg: string): void {
    console.log(msg)
  }
  run(): void {}
}
```

### 函数

在 typescript 中函数是可以实现重载的，实际就是根据参数的不同来返回不同的函数体

```typescript
//  定义重载规则
function pickCard(x: { suit: string; card: number }[]): number
function pickCard(x: number): { suit: string; card: number }
//  根据规则定义函数实体
function pickCard(x): any {
  // Check to see if we're working with an object/array
  // if so, they gave us the deck and we'll pick the card
  if (typeof x == 'object') {
    let pickedCard = Math.floor(Math.random() * x.length)
    return pickedCard
  }
  // Otherwise just let them pick the card
  else if (typeof x == 'number') {
    let pickedSuit = Math.floor(x / 13)
    return { suit: suits[pickedSuit], card: x % 13 }
  }
}
```

## 重要更新

> 元组

自 2.7 版本之后，元组长度为初始化长度，不能往里面添加数据，只能操作数据。

## FAQ

1. 当引用模块出现报错，'cannot find module xxx'？

typescript 默认的模块搜索方式是 classisc，我们需要设置 tsconfig.json 的 comlipierOptions 的 moduleResolution 为'node'，使用 node 的模块搜索方式
