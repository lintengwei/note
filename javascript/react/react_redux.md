# react-redux

## redux

> 全局数据管理中心

1. action
2. 定义 types 和 actions
3. reducers
4. 定义接收 action 的动作
5. combineReducers
   1. 把所有 reducers 集中成一个
   2. combineReducers({reducer1,reducer2})
6. store
7. import {createStore } from 'redux'
8. const store= createStore(reducers)
9. export store

## react-redux

> react 官方的 readux 注入仓库

1. 注入 store
1. import {Provider} from 'react-redux'
1. import store from '/store/store.js'
1. index.js
   1. <Provider store={store}><App/></Provider>
1. 注入 state 和 action 到组件
1. connect(function(state){return {state:state}},{addAction})(componentName)
1. 注入到组件的 props 属性上
