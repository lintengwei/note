# FormData

```html
<input type='file' id='img' multipart />
```

```javascript
//  可以传一个可选参数，表示form dom对象，浏览器会自动序列化数据
const formData=new FormData(?:HTMLFormElement)  
const img=document.getElementById('img')
const xhr=new XMLHttpRequest()

formData.append('name','ltw')

img.addEventListener('change',e=>{
  //  获取待上床的file Array<File>
  const files=img.files 
  files.forEach(file=>{
    formData.append('img',file)
  })
})

xhr.addEventListener('readystatechange',e=>{
  if(xhr.readystate===4&&xhr.status===200){
    //  表示响应成功
  }
})

xhr.open('post','http://server.com/upload',true)
xhr.setRequestHeader(name,value)
xhr.send(formData)  //  发送数据，如果值为FormData，会自动添加头部【Content-Type:multipart/form-data;bounary=--****】

```

## 方法

```javascript
const formData=new FormData()

//  添加键值对
//  如果已存在会把值存为数组的形式
formData.append(name,value)

//  删除键值对
formData.delete(name)

//  返回键值对
//  
let entries=formData.entries()

for(let entry of entries){
  console.log(`key:${entry[0]},value:${entry[1]}`)
}

//  返回第一个存入的值
formData.get(name)

//  以数组的形式返回所有键为name的值
formData.getAll(name) 

//  判断是否已存在该键
formData.has(name)

//  遍历所有key
formData.keys()

//  替换掉当前name的值
formData.set(name,value)

//  遍历所有值
formData.values()
```

## File

属性：

- lastModified: 1559191099929
  - 最后修改的时间戳
- lastModifiedDate: Thu May 30 2019 12:38:19 GMT+0800 (中国标准时间) {}
  - 最后修改的时间
- name: "timg (1).jpg"
  - 上传的文件名称
- size: 32312
  - 上传的文件大小
- type: "image/jpeg"
  - 上传的文件类型
- webkitRelativePath: ""

```html
<input type='file' id='img' />
```

```javascript
const img=document.getElementById('img')

//  选择的问价的本地路径
const path=img.value
//  选择的第一个文件
const files=img.files[0]
```