# redux document

[redux文档](https://cn.redux.js.org/#)

> 关注点

1. 单一数据源。全局只有一个store，如果需要分成多个管理，可以细分reducers，最后整合为一个rootReducer返回给createStore()；
2. state是只读的。不允许直接修改state内的数据，只能通过构建新的数据替换掉旧的数据的方式来执行reducer，目的是为了能够清楚的掌握数据更新的前后快照；
3. reducer为纯函数。不允许突变数据的存在；

## base

> action

描述下一步动作的具体操作,一个纯javascrpt对象，约定以字符串type为键区分不同的action

```javascript {.line-numbers}
const ADD="ADD"
const SUB="SUB"

function addAction(data){
  return {
    type:ADD,
    data
  }
}

function subAction(data){
  return {
    type:SUB,
    data
  }
}

//  action creator 
//  action对象生成器 
//  @param argNames 键
//  @param args 值  键值对应
function makeAction(type,...argNames){
  return function(...args){
    let action={type}
    argNames.forEach((key,index)=>{
      action[key]=args[index]
    })
    return action
  }
}
```

> reducer

根据action的描述完成具体的操作，只有一个根reducer，根据实际情况分成多个reducer，最后通过combineReducers({recuder1,reducer2,...reducers})整合多个reducer

```javascript {.line-numbers}
import {combinReducers} from 'redux'

//  common reducer
function reducer1(state,action){
  // code
}

function reducer2(state,action){
  switch(){
    case :;
    defatul:;
  }
}

export default combinReducers({
  reducer1,
  reducer2
})

//  等价于

export default function reducers(state,action){
  return {
    reducer1:reducer1(state.reducer1,action),
    reducer2:reducer2(state.reducer2,action)
  }
}

```

> store

全局的数据存储中心，所有的redux操作都是基于这个对象

```javascript {.line-numbers}
import {createStore} from 'redux'
import reducers from './reducers'

//  initState 为初始化的数据，可以从服务器获取或者缓存在本地数据
const store= createStore(reducers[,initState])

//  api
//  提交action
store.dispatch(action)

//  定义一个监听器，当state数据变化时候会执行
//  返回取消订阅的一个函数，取消订阅直接执行返回的函数
let unLinkFunc=store.subscribe(function(){})

export default store
```

## 计算属性

返回state中根据条件过滤的数据，只有当state数据变化的时候，才会更新。可以使用库[reselect](https://github.com/reduxjs/reselect)来实现。

```javascript {.line-numbers}
// install
// npm install --save reselect

//  usage
```

## 中间件

### redux-thunk

> 支持异步action的中间件