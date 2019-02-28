# Event

## 注意点

+ 每个监听事件默认最多绑定10个回调，如果超过10个，系统会抛出警告，可以通过process.on('warning',function(warning)=>{})监听