# Vue component

```javascript
function createComponent(Ctor, data, context, children, tag) {
  if (isUndef(Ctor)) {
    return
  }

  var baseCtor = context.$options._base

  // plain options object: turn it into a constructor
  if (isObject(Ctor)) {
    Ctor = baseCtor.extend(Ctor)
  }

  // if at this stage it's not a constructor or an async component factory,
  // reject.
  if (typeof Ctor !== 'function') {
    {
      warn('Invalid Component definition: ' + String(Ctor), context)
    }
    return
  }

  // async component
  var asyncFactory
  if (isUndef(Ctor.cid)) {
    asyncFactory = Ctor
    Ctor = resolveAsyncComponent(asyncFactory, baseCtor)
    if (Ctor === undefined) {
      // return a placeholder node for async component, which is rendered
      // as a comment node but preserves all the raw information for the node.
      // the information will be used for async server-rendering and hydration.
      return createAsyncPlaceholder(asyncFactory, data, context, children, tag)
    }
  }

  data = data || {}

  // resolve constructor options in case global mixins are applied after
  // component constructor creation
  resolveConstructorOptions(Ctor)

  // transform component v-model data into props & events
  if (isDef(data.model)) {
    transformModel(Ctor.options, data)
  }

  // extract props
  var propsData = extractPropsFromVNodeData(data, Ctor, tag)

  // functional component
  if (isTrue(Ctor.options.functional)) {
    return createFunctionalComponent(Ctor, propsData, data, context, children)
  }

  // extract listeners, since these needs to be treated as
  // child component listeners instead of DOM listeners
  var listeners = data.on
  // replace with listeners with .native modifier
  // so it gets processed during parent component patch.
  data.on = data.nativeOn

  if (isTrue(Ctor.options.abstract)) {
    // abstract components do not keep anything
    // other than props & listeners & slot

    // work around flow
    var slot = data.slot
    data = {}
    if (slot) {
      data.slot = slot
    }
  }

  // install component management hooks onto the placeholder node
  installComponentHooks(data)

  // return a placeholder vnode
  var name = Ctor.options.name || tag
  var vnode = new VNode(
    'vue-component-' + Ctor.cid + (name ? '-' + name : ''),
    data,
    undefined,
    undefined,
    undefined,
    context,
    {
      Ctor: Ctor,
      propsData: propsData,
      listeners: listeners, //  事件
      tag: tag,
      children: children
    },
    asyncFactory
  )

  return vnode
}

//  创建component实例
function createComponentInstanceForVnode(
  vnode, // we know it's MountedComponentVNode but flow doesn't
  parent // activeInstance in lifecycle state
) {
  var options = {
    _isComponent: true,
    _parentVnode: vnode, //  设置节点
    parent: parent //  设置父实例
  }
  // check inline-template render functions
  var inlineTemplate = vnode.data.inlineTemplate
  if (isDef(inlineTemplate)) {
    options.render = inlineTemplate.render
    options.staticRenderFns = inlineTemplate.staticRenderFns
  }
  return new vnode.componentOptions.Ctor(options) //  创建实例
}

//  注册实例钩子
function installComponentHooks(data) {
  var hooks = data.hook || (data.hook = {})
  for (var i = 0; i < hooksToMerge.length; i++) {
    var key = hooksToMerge[i]
    var existing = hooks[key]
    var toMerge = componentVNodeHooks[key]
    if (existing !== toMerge && !(existing && existing._merged)) {
      hooks[key] = existing ? mergeHook$1(toMerge, existing) : toMerge
    }
  }
}
```
