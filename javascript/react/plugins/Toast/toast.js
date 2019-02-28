/*
 * @Author: ltw
 * @Date: 2018-12-12 10:17:14
 * @Last Modified by: lin_tengwei@163.com
 * @Last Modified time: 2018-12-12 15:35:30
 */
import NoticeManage from './noticeManage';

const id = '__plugin_root_toast';
let noticeId = 1;
let opts = {
  duration: 2000
};

function createToastRootDom() {
  let body = document.body;
  let div = document.createElement('div');
  div.id = id;
  body.appendChild(div);
}

function hasInit() {
  let dom = document.getElementById(id);
  return dom !== null;
}

/**
 * @func 对外接口
 * @param init 初始化方法
 * @param info 普通
 * @param warn 警告
 * @param error 错误
 * @param success 成功
 *
 * @param message 显示的消息
 * @param customObj 通用方法 {type:String<info|warn|error|success>,message:String}
 */
export default {
  init(options) {
    if (options && typeof options.duration === 'number') {
      opts = Object.assign({}, opts, options);
    }
    hasInit() || createToastRootDom();
  },
  custom(customObj) {
    let { type, message, duration } = customObj;
    if (!!type) {
      pushMessage(type, message, duration);
    } else {
      throw new TypeError('paramter type is required');
    }
  },
  info(message, duration) {
    pushMessage('info', message, duration);
  },
  warn(message, duration) {
    pushMessage('warn', message, duration);
  },
  error(message, duration) {
    pushMessage('error', message, duration);
  },
  success(message, duration) {
    pushMessage('success', message, duration);
  }
};

function pushMessage(type = 'info', message = 'message is empty', duration) {
  NoticeManage.push({
    type,
    message,
    id: ++noticeId,
    duration: typeof duration === 'number' ? duration : opts.duration
  });
}
