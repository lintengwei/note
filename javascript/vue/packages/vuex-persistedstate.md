# vuex的持久化使用

[https://github.com/robinvdvleuten/vuex-persistedstate](https://github.com/robinvdvleuten/vuex-persistedstate)

```javascript
import { Store } from "vuex";
import createPersistedState from "vuex-persistedstate";
import * as Cookies from "js-cookie";

const options={
  //  本地存储的键名，默认为vuex
  key:'vuex',
  //  需要持久化的数据，未设置默认为全部
  paths:['name','userInfo.name'],
  reducer:function(state,paths){
    //  state 所有存储的state
    //  paths 需要持久化的值
    //  返回值为最终存储的
  },
  subscriber(){
    
  },
  //  自定义存储规则，默认是window.localStorage,也可以使用window.sessionStoreage
  //  如下是使用Cookie来存储state
  storage: {
    getItem: key => Cookies.get(key),
    setItem: (key, value) =>
          Cookies.set(key, value, { expires: 3, secure: true }),
    removeItem: key => Cookies.remove(key)
  },
  
  //  获取持久化的state
  getState(){

  },
  //  设置持久化的state
  setState(){

  },
  filter(param){
    // param ={type,payload}
    let {type,payload}=param

    //  如果提交的是someType，则不执行提交，过滤掉
    if(type==='someType'){
      return false
    }
    return true
  },
  arrayMerger(){

  },
  rehydrated(){

  }
}
const store = new Store({
  // ...
  plugins: [
    createPersistedState(options)
  ]
});
```
