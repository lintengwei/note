const baseConfig = require('./webpack.base.config')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path')
const webpack = require('webpack')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const Test = require('../plugins/test')

module.exports = {
  mode: 'development',
  ...baseConfig,
  devServer: {
    contentBase: path.resolve(__dirname, '../dist'),
    port: 9999,
    hot: true,
    open: false,
    watchContentBase: true
  },
  entry: {
    index: path.resolve(__dirname, '../src/js/index.js'),
    test: path.resolve(__dirname, '../src/js/test.js')
  },
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, '../dist')
  },
  module: {
    rules: [
      {
        test: /\.html$/,
        use: 'raw-loader'
      },
      {
        test: /\.vue$/,
        use: 'vue-loader'
      },
      {
        test: /\.styl(us)$/,
        use: ['vue-style-loader', 'css-loader', 'stylus-loader']
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, '../src/html/index.html'),
      filename: 'index.html',
      chunks: ['index']
    }),
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, '../src/html/test.html'),
      filename: 'test.html',
      chunks: ['test']
    }),
    new webpack.HotModuleReplacementPlugin(),
    new VueLoaderPlugin(),
    new Test()
  ],
  resolve: {
    alias: {
      vue: 'vue/dist/vue.js'
    }
  }
}
