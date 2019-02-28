import ToastVm from './ToastVm'

const T_info = 'info'
const T_warn = 'warn'
const T_success = 'success'
const T_error = 'error'

let timeId = -1

function enQueue(v, obj, duration, max) {
  if (!checkMax(v, max)) {
    deQueue(v)
    v.$data.msgQueue.push(obj)
    clearTimeout(timeId)
    timeId = setTimeout(() => {
      deQueue(v)
    }, duration)
  }
}

function deQueue(v) {
  let queue = v.$data.msgQueue
  queue.shift()
}

function checkMax(v, max) {
  return v.$data.msgQueue.length >= max
}

function initToast(el) {
  document.body.appendChild(el)
}

export default {
  install(Vue, opts) {
    let id = 0
    const preId = 'tm_toast_'
    const defaultOpts = {
      duration: 1500,
      max: 5
    }
    const Toast = Vue.extend(ToastVm)
    const vm = new Toast()
    const megreOpts = Object.assign({}, defaultOpts, opts)
    initToast(vm.$mount().$el)
    Vue.prototype.$toast = {
      show(obj) {
        let { type, msg } = obj
        let currentId = ++id
        let icon = vm.$data.iconObj[type]
        let cs = vm.$data.cObj[type]
        enQueue(
          vm,
          {
            id: preId + currentId,
            icon,
            cs,
            msg
          },
          megreOpts.duration,
          megreOpts.max
        )
      },
      info(msg) {
        this.show({
          msg,
          type: T_info
        })
      },
      warn(msg) {
        this.show({
          msg,
          type: T_warn
        })
      },
      success(msg) {
        this.show({
          msg,
          type: T_success
        })
      },
      error(msg) {
        this.show({
          msg,
          type: T_error
        })
      }
    }
  }
}
