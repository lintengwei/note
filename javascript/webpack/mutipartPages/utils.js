/**
 * @func 该文件是对多页面项目自动生成入口配置和html自动切入的工具，需要js文件的结构和html结构以及名称一致才能用
 *
 */

const path = require('path')
const fs = require('fs')
const HtmlWebpackPlugin = require('html-webpack-plugin')

//  入口文件的路径
exports.sourceJsPath = '../src/js'
//  html文件路径
exports.sourceHtmlPath = '../src/pages'

/**
 * @func 获取入口的js文件
 * @params js入口文件的绝地地址
 */
exports.get = function get(base) {
  let arr = []
  return function generateEntry(p) {
    let files = fs.readdirSync(p ? p : base)
    files.forEach(file => {
      let pp = p ? path.resolve(p, file) : path.resolve(base, file)
      let stats = fs.statSync(pp)
      if (stats.isDirectory()) {
        generateEntry(pp)
      } else {
        let rp = path.relative(base, pp)
        let obj = path.parse(rp)
        arr.push({
          ...obj,
          basePath: base
        })
      }
    })
    return arr
  }
}

/**
 * @func 获取webpack配置文件中entry的值和plugins中HtmlWebpackPlugin的值
 * @params  arr get函数返回的文件目录列表
 * @params  templateBase  html的根目录
 * @return {entry:<Object>,plugins:<Array>}
 */
exports.generateConfig = function generateConfig(arr, templateBase) {
  let entry = {}
  let plugins = []
  arr.forEach(ele => {
    let { dir, name, ext, base, basePath } = ele
    let entryKey = dir ? `${dir.replace(path.sep, '.')}.${name}` : name
    let opts = {
      template: path.resolve(templateBase, dir, name + '.html'),
      filename: name + '.html',
      chunks: [entryKey]
    }
    entry[entryKey] = path.resolve(basePath, dir, base)
    plugins.push(new HtmlWebpackPlugin(opts))
  })
  return {
    entry,
    plugins
  }
}
