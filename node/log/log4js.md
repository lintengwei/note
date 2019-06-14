# log4js使用

[https://log4js-node.github.io/log4js-node/index.html](https://log4js-node.github.io/log4js-node/index.html)

## 安装

```bat
rem npm
cnpm install --save log4js
rem yarn
yarn add log4js
```

## 基本使用

```javascript
const log4js=require('log4js')
const logger=log4js.getLogger()

//  level 
//  默认使用 process.stdout输出
logger.debug(msg)
logger.info(msg)
logger.warn(msg)
logger.error(msg)
```

## 配置文件

配置方法接收一个参数，如果参数是String类型，表示的是配置文件的位置，否则是配置对象。配置方法应该在引入log4js之后立刻调用，否则系统会默认使用【LOG4JS-CONFIG】对象（如果存在的话）或者使用默认的配置格式。默认的appender是【stdout】输出格式是【colour】并且输出级别为【off】，默认日志不输出！

如果使用集群，那么在工作进程和主进程中包含要配置的调用。这样，工作进程将为您的类别以及您可能定义的任何自定义级别选择正确的级别。追加器将只在主进程上定义，因此不存在多个进程试图写入同一追加器的危险。与以前的版本不同，在集群中使用log4js不需要特殊的配置。

如使用自定义的配置文件，则必须定义至少一个appender，和至少一个日志类别category

- log4js.configure(options:Object|String)
  - appenders<Object>
    - appenderName
      - 自定义的appender对象
      - type
        - 必须参数【stdout|file|fileDate】
        - stdout
          - 当type为stdout的呃时候，可以选择附带参数layout
          - layout<Object>
            - type
              - 指定输出格式
              - basic
              - colour
              - messagePassThrough
              - dummy
              - pattern
                - pattenr<String>
                  - 指定格式
                - tokens
        - file
          - filename
            - 日志文件的名称
          - maxLogSize
            - 日志文件的最大值，当超过最大值的时候，会把文件分隔
          - backups
            - 当日志超过最大值的时候会产生分隔文件，该值表示保存多少分隔文件，默认为5
          - layout
        - fileDate
          - filename
          - pattern<String>
            - 什么时候分隔日志的标识，默认为'yyyy-MM-dd'
          - layout
  - categories<Object>
    - default
      - 默认的日志对象，如果getLogger方法没有带参数，则会选择该对象作为日志记录对象
    - logName<Object>
      - 自定义的日志对象
      - appenders<Array<String>>
        - 选择options.appenders中定义的日志类型
      - level<String/i>
        - 记录级别
        - trace<debug<info<warn<error<fatal
      - enableCallStack<Boolean>
        - 可选参数，默认为false
        - 将此设置为true将使此类别的日志事件使用调用堆栈在事件中生成行号和文件名。有关如何在附加器中输出这些值的信息，请参阅模式布局。
  - levels
  - pm2<Boolean>
    - 如果使用PM2运行应用程序，则将此设置为true，否则日志将不起作用（您还需要将PM2内部通信安装为PM2模块：PM2安装PM2内部通信）
  - pm2InstanceVar<String>
    - 默认为NODE_APP_INSTANCE
  - disableCluster<Boolean>
- log4js.getLogger([category])
  - 获取日志对象
- log4js.shutdown(cb:Function)
  - 关闭日志进程？
- log4js.addLayout(type, fn)
  - 添加自定义日志格式layout

```javascript
const log4js=require('log4js')
log4js.configure()
```

## 【layout】显示格式

日志文件耳朵显示格式，log4js包内置了几个布局格式。

> Basic

Basic格式将输出时间错，级别，类型和其他用户的输出，如果当配置的时候未指定【layout】属性，则默认会使用Basic布局

> Coloured

输出格式和Basic一样，除了会对特定的方法输出指定的颜色：

- trace
  - blue
- debug
  - cyan
- info
  - green
- warn
  - yellow
- error
  - red
- fatal
  - megenta
  
> Message Pass-Through

此布局只是格式化日志事件数据，不输出时间戳、级别或类别。它通常用于使用特定格式序列化事件的附加程序中。

> Dummy

此布局只输出日志事件数据中的第一个值。它是为logstashhudp附加器添加的，我不确定它在这之外有多大用处。

> Pattern

- 可附带的参数
  - pattern<String>
  - tokens<String|Function>

pattern指定格式，可选的值有：

- %r time in toLocaleTimeString format
- %p log level
- %c log category
- %h hostname
- %m log data
- %d date, formatted - default is ISO8601, format options are: ISO8601, ISO8601_WITH_TZ_OFFSET, ABSOLUTE, DATE, or any string compatible with the date-format library. e.g. %d{DATE}, %d{yyyy/MM/dd-hh.mm.ss}
- %% % - for when you want a literal % in your output
- %n newline
- %z process id (from process.pid)
- %f filename (requires enableCallStack: true on the category, see configuration object)
- %l line number (requires enableCallStack: true on the category, see configuration object)
- %o column postion (requires enableCallStack: true on the category, see configuration object)
- %s call stack (requires enableCallStack: true on the category, see configuration object)
- %x{<tokenname>} add dynamic tokens to your log. Tokens are specified in the tokens parameter.
- %X{<tokenname>} add values from the Logger context. Tokens are keys into the context values.
- %[ start a coloured block (colour will be taken from the log level, similar to colouredLayout)
- %] end a coloured block

tokens可用于存放变量，存放的变量可以在pattern中通过{varName}引用

```javascript
log4js.configure({
  appenders: {
    out: { type: 'stdout', layout: {
      type: 'pattern',
      pattern: '%d %p %c %x{user} %m%n',
      tokens: {
        user: function(logEvent) {
          return AuthLibrary.currentUser();
        }
      }
    }}
  },
  categories: { default: { appenders: ['out'], level: 'info' } }
});
const logger = log4js.getLogger();
logger.info('doing something.');

//  output
//  2017-06-01 08:32:56.283 INFO default charlie doing something.
```

### 自定义显示格式

```javascript
const log4js = require('log4js');

log4js.addLayout('json', function(config) {
  return function(logEvent) { return JSON.stringify(logEvent) + config.separator; }
});

log4js.configure({
  appenders: {
    out: { type: 'stdout', layout: { type: 'json', separator: ',' } }
  },
  categories: {
    default: { appenders: ['out'], level: 'info' }
  }
});

const logger = log4js.getLogger('json-test');
logger.info('this is just a test');
logger.error('of a custom appender');
logger.warn('that outputs json');
log4js.shutdown(() => {});

//  output
/* 
{"startTime":"2017-06-05T22:23:08.479Z","categoryName":"json-test","data":["this is just a test"],"level":{"level":20000,"levelStr":"INFO"},"context":{}},
{"startTime":"2017-06-05T22:23:08.483Z","categoryName":"json-test","data":["of a custom appender"],"level":{"level":40000,"levelStr":"ERROR"},"context":{}},
{"startTime":"2017-06-05T22:23:08.483Z","categoryName":"json-test","data":["that outputs json"],"level":{"level":30000,"levelStr":"WARN"},"context":{}},
*/
```