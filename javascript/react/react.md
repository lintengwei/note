# react

## react 的生命周期图解

![sda](./static/4118241-d979d05af0b7d4db.webp)

## note

- 由于 JSX 编译后会调用 React.createElement 方法，所以在你的 JSX 代码中必须首先声明 React 变量。
- 首字母必须大写，否则会被编译器认定为 html 标签
- react 元素的不可变性，如果需要更新需要重新组件一个组件，react 会负责优化和渲染？？将界面视为一个个特定时刻的固定内容（就像一帧一帧的动画），而不是随时处于变化之中（而不是处于变化中的一整段动画），将会有利于我们理清开发思路，减少各种 bug。当需要刷新 ui 时，render 会执行并且会对比上一帧的状态来决定更新哪一块
- false、true、null 或 undefined 不会渲染，0 会渲染
- props
  - children
    - 该属性为包含在组件内部的子组件
    - 如果只有一个子组件，则 children 表示子组件，否则所有子组件集合成一个数组，如下
    - 顺序从上到下依次为 children[0],children[1]...
- 使用 bind 绑定事件
  - onClick={this.clickHandle.bind(this,...args)}
  - clicnHandle(...args,\$e)
  - 事件对象放在最后面

## 事件绑定

> 默认组件方法不绑定 this，方法里面访问 this 为 undefined，并且如果需要传递参数，则事件对象作为最后一个参数传给方法
> react 内部使用的是事件池，如果退出当前事件的回调，事件对象会被放回事件池，如果外部对象持有事件对象的引用，可能会出现补课预支的问题，如果确实需要引用事件对象，可以调用事件对象的 persist()方法让事件对象脱离事件池。

```javascript
//  in render func
<button className="test" onClick={this.testChange}>click</button>

//  methods
testChange(){
  console.log(this) // undefined
}

// in render bind this
// 下面的方法会在每次组件渲染时创建一个新的函数，可能会影响性能？？
<button onClick={(e)=>{this.testChange(e)}}></button>
<button className="test" onClick={this.testChange.bind(this,1)}>click</button>

// a=1 , e is Event
testChange(a,e){
  console.log(this) // Component ins
}

// or in constructor
<button className="test" onClick={this.testChange}>click</button>
constructor(props){
  super(props)
  this.testChange=this.testChange.bind(this)
}
//  属性初始化器语法
testChange=()=>{
  console.log(this)
}

```

## 获取 dom 引用

```javascript
// defined in class
constructor(props){
  this.myRef=this.createRef()
  this.inputDom=null
  this.setInputDom=d=>{
    this.inputDom=d
  }
}
render(){
  return(
    <div>
      <input ref={this.myRef} />
      <input ref={this.setInputDom}/>
    </div>
  )
}
componentWillMount(){
  //  usage
  let inputDom=this.myRef.current
}

//  defined in pure function
function InputItem(props){
  let dom=null
  return (
    <div>
      <input  ref={inputDom=>{dom=inputDom}}/>
    </div>
  )
}
```

## 数据传递

> 默认父子组件数据传递的方式是采用皮 props 的方式，如果需要跨越多层数据传递，则可以使用 Context 上下文。当应用规模较小的时候，app 层级的组件可以作为数据中心使用。

```javascript
import React from 'react';

//  数据上下文 通过 Context.Provider传递数据 Context.Consumer获取数据
const DataContext = React.createContext();
//  数据中心
const appData = {};
//  如何更新的问题???

class App extends React.Component {
  render() {
    return (
      <DataContext.Provider value={appData}>
        <ChildCom />
      </DataContext.Provider>
    );
  }
}

function ChildCom(props) {
  return (
    <DataContext.Consumer>
      {data => {
        //  data为appData
      }}
    </DataContext.Consumer>
  );
}
```

## 模块化 css

1. css 文件名，[filename].module.css
2. 引用，import style from 'css_path'.
3. [filename].css 不需要另外赋值，直接引入
4. 使用，className={style.className}

### 预编译 css【sass,scss,stylus】

1. npm install --save node-sass or yarn add node-sass /// npm install --save sass-loader???
2. scss 文件名. [filename].module.scss
3. import styles from [filename].module.scss
4. 直接引入 filename.scss 不需要另外赋值
5. 格式化 - install plugin 【Beautify css/sass/scss/less】【vscode】
