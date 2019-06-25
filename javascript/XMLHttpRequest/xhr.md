# XMLHttpRequest

## 使用

```javascript
//  实例化一个xhr对象
const xhr=new XMLHttpRequest()
//  实例化一个表单对象
const formData=new FormData()

const upload=xhr.upload

formData.append('name','ltw')

//  监听上传进度
upload.addEventListener('progress',e=>{
  let total=e.total //  需要发送的全部数据大小
  let sended=e.loaded //  已经发送的数据大小
})

//  监听状态变化
xhr.onreadystatechange=function(){

}

//  打开数据通道
xhr.open('post','http://server.com/')
//  当发送对象为FormData的时候，浏览器会自动添加头部【content-type:multipart/form-data;bounary=---***】
//  发送数据
xhr.send(formData)
```

## 属性

```javascript
const xhr=new XMLHttpRequest()

//  readystate的值改变的时候，会触发该方法
xhr.onreadystatechange=function(){
  //  通过判断当前xhr的状态来左特定的操作
}

//  当前xhr的状态
//  已创建，但为调用open方法
//  open方法被调用
//  send方法被调用
//  下载中，responseText已有数据
//  下载完成
xhr.readystate=0|1|2|3|4

//  响应的状态码
xhr.status

//  响应的状态描述
xhr.statusText

//  响应的主体数据
xhr.response

xhr.responseText

//  返回数据的类型
xhr.responseType

xhr.responseURL

xhr.responseXML

//  设置的超时时间，默认为0，不超市
xhr.timeout

//  返回一个XMLHttpRequestUpload对象，可以通过在该对象上监听事件来查看上传进度
//  onloadstart|onprogress|onabort|onerror|onload|ontimeout|onloadend
//  开始上传|上传中|中止上传|发生错误|上传成功|上传超时|完成（成功与否都会调用，相当于finally）
xhr.upload 

//  Boolean值。如果在发送来自其他域的XMLHttpRequest请求之前，未设置withCredentials 为true，那么就不能为它自己的域设置cookie值。而通过设置withCredentials 为true获得的第三方cookies，将会依旧享受同源策略，因此不能被通过document.cookie或者从头部相应请求的脚本等访问。
xhr.withCredentials
```

## 方法

```javascript
const xhr=new XMLHttpRequest()

//  中止请求
xhr.about()

//  返回原始的headers字符，\r\n分隔
xhr.getAllResponseHeaders()

//  返回指定的头部
xhr.getResponseHeader(name)

//  初始化一个xhr请求，调用之后readystate变为1
//  方法|请求地址|是否为异步|用户|密码
xhr.open(method,utl[,async][,user][,password])

//  重写请求的数据类型
xhr.overrideMimeType()

//  发送数据，调用之后readystate变为2
xhr.send(data)

//  设置http的header，
//  【注意】该方法必须在open和send之间调用！！！！！
xhr.setRequestHeader(name,value)
```

## 事件

```javascript
//  xhr事件是对响应的监听，upload对象是对上传的监听
const xhr=new XMLHttpRequest()

// readystate状态改变的时候会触发
xhr.addEventListener('readystatechange',e=>{

})

//  当资源和其依赖加载完成之后会触发
xhr.addEventListener('load',e=>{
  
})

//  程序开始加载的时候，loadstart会触发
xhr.addEventListener('loadstart',e=>{
  
})

//  loadend事件总是在一个资源的加载进度停止之后被触发
xhr.addEventListener('loadend',e=>{
  
})

//  收到数据的时候会定期触发该事件
xhr.addEventListener('progress',e=>{
  
})

//  请求超时的时候触发
xhr.addEventListener('timeout',e=>{
  
})

//  发生错误的时候触发
xhr.addEventListener('error',e=>{
  
})
```