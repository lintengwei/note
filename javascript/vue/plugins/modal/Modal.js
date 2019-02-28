import ModalVm from './ModalVm'

const timeOut = 1500
export default {
  install: function(Vue, opts) {
    const defaultOpts = {
      title: '系统提示',
      message: '弹窗信息占位符',
      cancelText: '取消',
      confirmText: '确定',
      autoClose: true,
      success(res, vm) {
        close(vm)
      }
    }
    const Modal = Vue.extend(ModalVm)
    const megreOpts = Object.assign({}, defaultOpts, opts)

    Vue.prototype.$alter = function(opts) {
      let params = Object.assign({}, megreOpts, opts)
      let vm = new Modal()
      let {
        title,
        message,
        cancelText,
        confirmText,
        success,
        autoClose
      } = params
      let el = vm.$mount().$el
      vm.$once('cancel', () => {
        success(
          {
            cancel: true
          },
          vm
        )
        close(vm, el)
      })
      vm.$once('confirm', () => {
        success(
          {
            confirm: true
          },
          vm,
          function() {
            close(vm, el)
          }
        )
        autoClose && close(vm, el)
      })
      vm.$data.title = title
      vm.$data.message = message
      vm.$data.cancelText = cancelText
      vm.$data.confirmText = confirmText
      document.body.appendChild(el)
      show(vm)
    }
  }
}

function close(v, el) {
  v.$data.show = false
  unMount(v, el)
}

function show(v) {
  v.$data.show = true
}

function unMount(v, el) {
  setTimeout(() => {
    v.$destroy()
    document.body.removeChild(el)
  }, timeOut)
}
