/**
 * @author ltw
 */

/**
 * 对象深拷贝
 * 浅拷贝使用 Object.assign()
 * @param {} obj
 */
export function ObjectDeepClone(obj) {
  if (typeof obj === 'object') {
    let jsonStr = JSON.stringify(obj);
    return JSON.parse(jsonStr);
  } else {
    throw new Error('param should be Object');
  }
}

/**
 * 是否为null
 * @param {String} input
 */
export function isFalsey(input) {
  return !(isFalse() || isNull() || isUndefined() || isStringNullOrUndefined());
}

export function isFalse(input) {
  return !!input;
}

export function isStringNullOrUndefined(input) {
  if (typeof input === 'string') {
    return input === 'null' || input === 'undefined';
  }
  return false;
}

export function isNull(input) {
  return Object.prototype.toString.call(input) === '[object Null]';
}

export function isUndefined(input) {
  return Object.prototype.toString.call(input) === '[object Undefined]';
}

/**
 * @func 获取url搜索map
 * @param {String} search
 */
export function getQueryMap(search) {
  let map = {};
  let temp;
  search = search.slice(1, search.length);
  search.split('&').forEach(function(v) {
    temp = v.split('=');
    map[temp[0]] = temp[1];
  });
  return map;
}

/**
 * @func 获取url搜索key的值
 * @param {String} search
 * @param {String} keyName
 */
export function getQueryValue(search, keyName) {
  if (
    arguments.length < 2 ||
    typeof search !== 'string' ||
    typeof keyName !== 'string'
  ) {
    throw new TypeError('参数错误');
  }
  let regStr = `&?${keyName}(?==)=([^&]+)&?`;
  let reg = new RegExp(regStr);
  let a = reg.exec(search);
  return a && a.length > 1 && a[1];
}

/**
 * Regexp
 */

export const Reg = Object.create(null);

Reg.isPhoneRight = input => {
  if (typeof input === 'string' || typeof input === 'number') {
    return /^1\d{10}$/gi.test(input);
  }
  return false;
};
