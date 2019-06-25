# node-formidable

## 基本用法

```javascript
const formidable = require('formidable')
const http = require('http')
const fs = require('fs')
const path = require('path')
const util = require('util')

http
  .createServer((req, res) => {
    if (req.url == '/upload') {
      // parse a file upload
      //  实例化对象
      let form = new formidable.IncomingForm()
      form.uploadDir='/temp'  //  临时上传目录，默认是os.temp()
      form.keepExtensions=false //  是否保持原始的扩展
      form.maxFieldSize=20*1024*1024  //  所有上传的字段的最大限制
      form.maxFileSize=200*1024*1024  //  所有上传文件的大小限制
      form.maxFields=1000  //  上传的字段的数量限制
      form.multiples=false  // 是否可以上传多个文件，使用html的form multiple属性

      //  解析request对象
      form.parse(req, function(err, fields, files) {
        //  fields字段的值为Object，以input的name为键，value为值的对象
        //  files字段的值为Object，以input[type=file]的name为键，值为File对象
        //  其中File对象的path为临时存储的路径，默认不带扩展，name为上传文件的名称
        res.writeHead(200, { 'content-type': 'text/plain' })
        res.write('received upload:\n\n')
        res.end(util.inspect({ fields: fields, files: files }))

        let imgFile = files['img']
        let p = imgFile.path
        let ext = imgFile.name.slice(imgFile.name.lastIndexOf('.'))
        fs.createReadStream(p).pipe(
          fs.createWriteStream(path.resolve(__dirname, './a' + ext)),
          {
            end: true
          }
        )
      })

      return
    }

    fs.createReadStream(path.resolve(__dirname, './index.html')).pipe(
      res,
      {
        end: true
      }
    )
  })
  .listen(9999)

```

## formidable.IncomingForm(options)

构造输入流解析对象，可以传一个参数配置options，表示一些上传规则，通过调用parse方法解析请求

## 事件

```javascript
const formidable=require('formidable')

const form=new formidable.IncomingForm()

//  在解析了每个传入数据块之后发出。可用于滚动您自己的进度条。
//  监听上传进度
form.on('progress',(bytesReceived,bytesExpected)=>{

})

//  当收到键值对的时候触发
//  在这里获取键值对
form.on('field',(name,value)=>{

})

//  每当在上载流中检测到新文件时发出。如果要在文件系统上缓冲上载时将文件传输到其他地方，请使用此事件。
form.on('fileBegin',(name,file)=>{
  
})

//  每当接收到字段/文件对时发出。文件是文件的实例。
//  测试只有当文件上传完才会触发
//  在这里获取完整的file对象
//  当上传多个对象的时候，上传完成会一次触发该事件
form.on('file',(name,file)=>{
  
})

//  发生错误的时候触发
form.on('error',(err)=>{
  
})

//  用户中止操作的时候触发
form.on('aborted',()=>{
  
})

//  在接收到整个请求并且所有包含的文件都已完成刷新到磁盘时发出。这是一个很好的地方，您可以发送您的回复。
//  响应客户端请求可以在这里发出，如果不需要额外的操作在回调函数里面返回结果即可
form.on('end',()=>{
  
})
```

## 文件对象

form解析完请求对象之后会生成一个数据对象；

```javascript
const form=new formidable.IncomingForm()

form.parse(req,(err,fields,files)=>{
  //  fields Object
  //  files Object
  //  <input type='text' name='age' value='12'>
  // fields {age:'12'}
  //  <input type='file' name='img'>
  //  files {img:File}
})
```