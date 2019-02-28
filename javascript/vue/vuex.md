# vuex

[vuex 文档](https://vuex.vuejs.org/zh/)

## 基本

> 开启严格模式 strict: process.env.NODE_ENV !== 'production'。如果变更 state 不是以 mutation 完成的。会报错

**不要在发布环境下启用严格模式！**严格模式会深度监测状态树来检测不合规的状态变更——请确保在发布环境下关闭严格模式，以避免性能损失。

### store

> vuex 对外公布的接口调用对象

```javascript {.line-numbers}
//  提交mutation
store.commit({
  type: name,
  payload
});

//  获取state
const state = store.state;
```

### state

> spa 的数据中心

```javascript {.line-numbers}
const state = {
  // ...defaule attr
};

export default state;
```

### mutations

> 更新 state 的最终执行，纯函数，不能异步执行。
> 数据更新方式遵循 vue 的更新规则。

```javascript {.line-numbers}
//  定义
const mutations = {
  //  定义一个mutation
  add(state, payload) {
    //  state 为上一个状态的数据
    //  payload 为当前动作附带的参数
    //   在redux的reducer中提交action之后在reducer中完成逻辑操作，最后返回新的state来完成更新，这和vuex的实现方式不同！
    state.attrName = newValue;
  }
};
export default mutations;

//  提交mutation实现更新
store.commit('mutationname', ...args);
store.commit({
  type: 'mutationname',
  ...args
});

//  注入mutation到组件
import { mapMutations } from 'vuex';

//  在组件中
const component = {
  methods: {
    //  way1
    //  equals  this.$store.commit('type1'),this.$store.commit('type2')
    //  如果需要参数，则直接在引用方法处注入即可
    ...mapMutations(['type1', 'type2']),
    ...mapMutations({
      alias: 'type1' // this.alias() equals this.$store.commit('type1')
    })
  }
};
```

### actions

> 提交 mutation 来更新，可以异步执行。
> 在 redux 中，当需要更新 state 的数据时，action 作为修改意图传递传递给 reducer。这个与 vuex 略有不同，需要区别。

```javascript {.line-numbers}
const actions = {
  action(context) {}
};

export default actions;
```

### getters

> 计算属性

```javascript {.line-numbers}
const getters = {
  //  参数为全局state每一次计算属性的依赖发生癌变都会触发更新
  isLogin(state, getters) {}
};

export default getters;
```

## 辅助方法

### mapState,mapGetters,mapMutations

```javascript
const computed = {
  ...mapState(['name1', 'name2']),
  ...mapState({
    alias: state => state.name1,
    alias1: 'name1'
  })
  // mapGetters同上
};

const methods = {
  //  参数在调用里面加上
  ...mapMutations(['mutation1', 'mutation2'])
};
```

## 模块化

> vuex 模块化，各个模块各自管理，便于拓展和管理

```javascript {.line-numbers}
const moduleA = {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {},
  getters: {}
};

const moduleB = {
  state: {},
  mutations: {},
  actions: {}
};

const store = new Vuex.Store({
  modules: {
    a: moduleA,
    b: moduleB
  }
});

store.state.a; // -> moduleA 的状态
store.state.b; // -> moduleB 的状态
```

> 命名空间的问题？如何定义模块 getters 等？如何提交 commit 等？

```javascript {.line-numbers}
import { mapState, mapGetters, mapMutations } from 'vuex';

const component = {
  computed: {
    //  modulePath 明名控件名称 ,state1 module下的state
    ...mapState('modulePath', ['state1', 'state2']),
    ...mapState('modulePath', {
      alias1: state.count
    })
  },
  methods: {
    //  同上
    ...mapMutations('modulePath', ['mutation1', 'mutation2']),
    test() {
      this.$store.commit({
        //  模块名称和mutationType组合成
        type: 'a/add'
      });
    }
  }
};
```
