# react-router-dom

> react 官方的路由管理库

1. 在根组建下面使用 <Router><App/></Router>即可，不需要每个组件下面使用
1. Router 作为记录历史记录的组件，作为 app 组件的 wapper，记录浏览历史记录

note:::

```javascript
//  重定向
<Route exact path="/user" render={() => (
  loggedIn ? (
    <User/>
  ) : (
    <Redirect to="/login?next=user"/>
  )
)}/>

//  页面之间传递参数
//  search 在 location对象中
Route component as this.props.location
Route render as ({ location }) => ()
Route children as ({ location }) => ()
withRouter as this.props.location

//  params 在match对象中
Route component as this.props.match
Route render as ({ match }) => ()
Route children as ({ match }) => ()
withRouter as this.props.match
matchPath as the return value
```

## 相关组件

> Route

三种渲染方式，[component|render|children]

```javascript
// 使用 component props渲染
<Route component={SomeComponent} path="/path" />
//  如果使用inline function 的方式，每次路由匹配都会卸载旧的组件，加载新的组件。
//  如从 /path/1 跳到 /path/2，如果使用上面的只会更新必要的界面
//  参数改变会触发【componentWillUpdate】钩子
<Route component={()=>{return <SomeComponent/>}} path="/path/:id" />

//  使用render inline function ,从 /path/1 跳到 /path/2，只有更新需要刷新的界面
<Route path="/path" render={(props)=>{return <SomeComponent />}} >
```

> Prompt

离开当前路由的弹窗确认。默认使用系统的 prompt，可以在 Router 组件上使用【getUserConfirmation】重写

```javascript
getUserConfirmation = (message, callback) => {
  //  ...重写方法 返回true 可以跳转，返回false，不需跳转
  return callback(true); // 允许跳转
};
```

## 切换路由使用动画

```css
/* base */
.base-page {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}

.page {
  transition: all 1s;
}

/* fade */

.page-fade-enter {
  opacity: 0;
}

.page-fade-enter-active {
  opacity: 1;
}

.page-fade-exit {
  opacity: 1;
}

.page-fade-exit-active {
  opacity: 0;
}

/* left-exit right-enter */

.page-rtl-enter {
  transform: translateX(100%);
}

.page-rtl-enter-active {
  transform: translateX(0);
}

.page-rtl-exit {
}

.page-rtl-exit-active {
  transform: translateX(-100%);
}

/* left-enter right-exit */

.page-ltr-enter {
  transform: translateX(-100%);
}

.page-ltr-enter-active {
  transform: translateX(0);
}

.page-ltr-exit {
}

.page-ltr-exit-active {
  transform: translateX(100%);
}

/* page-popup */

.page-popup-enter {
  transform: translateY(200px);
  opacity: 0;
}

.page-popup-enter-active {
  transform: translateY(0px);
  opacity: 1;
}

.page-popup-exit {
}

.page-popup-exit-active {
  transform: translateY(200px);
  opacity: 0;
}
```

```jsx
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import { TransitionGroup, CSSTransition } from 'react-transition-group';
<div className="Body">
  <Link to="/user">user</Link>
  <Link to="/order">order</Link>
  <Link to="/">index</Link>
  <Route
    // 必须要用【render】，并且把【location】注入进去？
    render={({ location }) => {
      return (
        <TransitionGroup>
          <CSSTransition
            //  必须要有【key】来阻止【key】复用组件
            key={location.key}
            timeout={1000}
            classNames="page page-rtl"
          >
            <Switch
              // 必须要有【location】？
              location={location}
            >
              <Route exact path="/order" component={Order} />
              <Route exact path="/" component={Index} />
            </Switch>
          </CSSTransition>
        </TransitionGroup>
      );
    }}
  />
</div>;
```
