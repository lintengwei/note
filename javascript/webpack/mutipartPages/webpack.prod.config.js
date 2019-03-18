const path = require('path')
const CleanWebpackPlugin = require('clean-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const utils = require('./utils')

const func = utils.get(path.resolve(__dirname, utils.sourceJsPath))
const scriptList = func()
const baseConfig = utils.generateConfig(
  scriptList,
  path.resolve(__dirname, utils.sourceHtmlPath)
)

module.exports = {
  mode: 'production',
  entry: baseConfig.entry,
  output: {
    filename: 'js/[name].[chunkhash].js',
    path: path.resolve(__dirname, '../dist')
  },
  optimization: {
    splitChunks: {
      cacheGroups: {
        styles: {
          name: 'common',
          test: /[\\/]styles[\\/]common[\\/]/,
          chunks: 'all',
          enforce: true
        }
      }
    }
  },
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: ['ts-loader']
      },
      {
        test: /\.(c|sc)ss$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader
          },
          'css-loader',
          'sass-loader'
        ]
      }
    ]
  },
  plugins: [
    new CleanWebpackPlugin(),
    new MiniCssExtractPlugin({
      filename: 'css/[name].[chunkhash].css'
    }),
    ...baseConfig.plugins
  ]
}
