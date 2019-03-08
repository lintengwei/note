const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path')
const baseConfig = require('./webpack.base.config')
const CleanWebpackPlugin = require('clean-webpack-plugin')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
module.exports = {
  ...baseConfig,
  mode: 'production',
  entry: {
    index: path.resolve(__dirname, '../src/js/index.js'),
    test: path.resolve(__dirname, '../src/js/test.js')
  },
  output: {
    filename: '[name].[chunkhash].js',
    path: path.resolve(__dirname, '../dist/js')
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
      template: path.resolve(__dirname, '../public/index.html'),
      filename: '../index.html',
      chunks: ['index']
    }),
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, '../public/index.html'),
      filename: '../test.html',
      chunks: ['test']
    }),
    new CleanWebpackPlugin('../dist', {
      allowExternal: true
    }),
    new VueLoaderPlugin()
  ]
}
