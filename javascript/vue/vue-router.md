# vue-router

## 使用

```javascript
import Router from 'vue-router'

const Index=r=>require.ensure([],r(require('./pages/index.vue')),'index')

const router=new Router({

  mode:'hash|history',  //  路由模式
  base:'',  //  项目部署目录
  routes:[
    {
      path:'/',
      name:'',
      component:Index
    }
  ]
})

export default router
```

## 路由模式

> history

使用的是history.pushState方法，需要浏览器和服务器的支持

```conf
# nginx配置

server{
  location /{
    root webapps
    try_files $uri $uri/ /projectName/index.html
  }
}
```

```javascript
//  webpack.config.js
module.exports={
  output:{
    publicPath:'/projectName/'
  }
}
```