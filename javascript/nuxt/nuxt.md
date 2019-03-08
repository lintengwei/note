# nuxt 服务端渲染框架

## 安装

```bat
rem 安装nuxt-cli
cnpm install -g create-nuxt-app
rem 生成新项目
npx create-nuxt-app projectDir
rem cd
cd projectDir
npm run dev
rem 打开 http://localhost:3000
```

## 项目目录

### layouts 目录

pages 目录下面组件使用的 html 模版，初始化项目之后会自动生成一个默认布局文件。可以根据需求自定义，在 pages 下面的组件如果没有指定 layout 属性，默认使用 default，如果指定了则可以使用指定布局文件

### store

全局数据管理中心 vuex，可以使用两种模式。
当新建之后是否需要重启重新渲染？

普通模式：

1. 新建 index.js 模块
2. 导出一个返回 vuex.store 实例的方法

```javascript
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = () =>
  new Vuex.Store({
    state: {
      // ...
      amount:1
    },
    mutations: {
      //  ...
      add(state,addAmount){
        state.amount+=addAmount
      },
      sub(state,subAmount){
        state.amount-=subAmount
      }
    }
  })

export default store

//  in pages
export default{
  fetch({store}){
    console.log(store.state)
  },
  methods:{
    test(){
      console.log(this.$store.state)
      console.log(this.$store.commit('test'))
    },
    add(){
      this.$store.commit('add',2)
    },
    sub(){
      this.$store.commit('sub',2)
    }
  }
}
```

模块模式：

状态树还可以拆分成为模块，store 目录下的每个 .js 文件会被转换成为状态树指定命名的子模块。
[https://zh.nuxtjs.org/guide/vuex-store](https://zh.nuxtjs.org/guide/vuex-store)

例如：

```javascript
//  主模块index.js  次模块 sub.ks
//  index.js
//  state 必须是函数，并且返回一个state初始状态
export const state=()=>({
  num:1
})
export const mutations={
  add(state){
    state.num++
  },
  sub(state){
    state.num--
  }
}

//  sub.js
export const state=()=>({
  sub:1
})
export const mutations={
  add(state){
    state.sub++
  },
  sub(state){
    state.sub--
  }
}

//  最终的state
state={
  num:1,
  sub:{
    sub:1
  }
}

//  in pages
export default{
  methods:{
    add(){
      //  commit index mutation
      this.$store.commit('add')
      this.$store.commit('sub/add')
    },
    sub(){
      //  ....
    }
  }
}
```

## FAQ

1. 如何修改默认端口?

[https://zh.nuxtjs.org/faq/host-port/#%E5%A6%82%E4%BD%95%E6%9B%B4%E6%94%B9%E5%BA%94%E7%94%A8%E7%9A%84%E4%B8%BB%E6%9C%BA%E5%92%8C%E7%AB%AF%E5%8F%A3%E9%85%8D%E7%BD%AE-](https://zh.nuxtjs.org/faq/host-port/#%E5%A6%82%E4%BD%95%E6%9B%B4%E6%94%B9%E5%BA%94%E7%94%A8%E7%9A%84%E4%B8%BB%E6%9C%BA%E5%92%8C%E7%AB%AF%E5%8F%A3%E9%85%8D%E7%BD%AE-)

2. 配置 css 预编译器?

[https://zh.nuxtjs.org/api/configuration-css/#css-%E9%85%8D%E7%BD%AE](https://zh.nuxtjs.org/api/configuration-css/#css-%E9%85%8D%E7%BD%AE)

```bat
rem 需要安装webpack的loaders
npm install --save-dev node-sass sass-loader
npm install -D stylus stylus-loader
```

3. 页面权限控制?

使用中间件来解决。
[授权页面](https://zh.nuxtjs.org/examples/auth-routes)
[https://zh.nuxtjs.org/guide/routing/#%E4%B8%AD%E9%97%B4%E4%BB%B6](https://zh.nuxtjs.org/guide/routing/#%E4%B8%AD%E9%97%B4%E4%BB%B6)
