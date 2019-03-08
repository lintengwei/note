- [typescript 的使用](#typescript-%E7%9A%84%E4%BD%BF%E7%94%A8)
  - [安装](#%E5%AE%89%E8%A3%85)
  - [基本使用](#%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)
    - [接口](#%E6%8E%A5%E5%8F%A3)
    - [函数](#%E5%87%BD%E6%95%B0)

# typescript 的使用

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
