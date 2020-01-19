# 微信开发注意点

## 两个access_token

==普通access_token==

普通access_token是客户端接口调用的凭证

关于如何获取？

参考开发文档【https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html】。一般有效期为两个小时，过期客户端接口调用失效，需要重新想服务器请求新的ticket，服务器想微信服务器刷新access_token

如何使用？

服务器向微信服务器请求之后，存储在服务器中，并且过期时候会主动再次请求刷新token（请求的频率有限制）。获取access__token之后，可以根据该token获取前端需要调用的ticket票据，该票据是前端调用jssdk签名需要使用的

```javascript
//  以get的方法获取acces_token
//  appid，secret为公众号后台配置的，grant_type为固定值即可
let access_token_get_url='https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET'

//  上述请求返回一个obj
let returnObject={
  'access_token':'accsss_token',  //  授权令牌
  'expires_in':7200 //  失效时间，在获取之后的两个小时内有效
}

//  根据access_token获取ticket
//  access_token为上述返回的access_token，typpe固定值jsapi
let jsapi_ticket_get_url='https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi'

//  上述请求返回Obj
//  然后服务器把ticket存储在本地并且及时刷新，返回给客户端
let returnObj={
  errcode:0,  //  返回错误码
  errmsg:'ok',  //  返回错误信息
  ticket:'jsapi_ticket',  //  返回的jsapi_ticket
  expires_in:7200 //  ticket的失效时间
}

//  客户端在jssdk配置方法中需要使用
wx.config({
  debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
  appId: '', // 必填，公众号的唯一标识
  timestamp: , // 必填，生成签名的时间戳
  nonceStr: '', // 必填，生成签名的随机串
  signature: '',// 必填，签名
  jsApiList: [] // 必填，需要使用的JS接口列表
});

//  签名参照 https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html#62
let noncestr='Wm3WZYTPz0wzccnW' //  随机字符串，和config方法的参数中的nonceStr已知
let jsapi_ticket='sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg' //  返回的ticket
let timestamp=1414587457  //  时间戳 和config方法的参数中的timestamp已知
let url='http://mp.weixin.qq.com?params=value'  //  调用jssdk的url，不需要进行url转义， 可以不包含#后面呃内容？，因为url有参与签名，所以每次跳转url都需要重新调用配置文件，重新签名

let encrytStr='jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value'

let sign=sha1(encrytStr)  //签名
```


==授权access_token==

是用户对于网页的oauth2.0授权。总共分为四个步骤，具体参考[https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html](https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html)

```javascript
//  浏览器打开url
//  appid 公众号id
//  redirect_uri  重定向的url，会携带code和state
//  state 用户指定的参数
//  response_type 固定为code
//  scode 获取类型 snsapi_base | snsapi_userinfo  ，前者用户无感知直接重定向到开发者指定的网页，后者需要用户主动同意才能重定向
let getCodeUrl='https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect'

//  2. 获取到code，需要用户把code发送给服务器换取access_token
//  该步为安全起见，在服务器操作，客户端值需要把获取的code传送给服务器
let access_token_get_url='https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code'
let returnObj={
  'access_token':'',  //  用户信息访问授权token
  'expires_in':7200,  //  失效时间
  'refresh_token':'', //  用户刷新token，有效期30天
  'openid':'openid',  //  用户openid，未关注也会生成
  'scope':'scope' //  授权域
}

//  因为access_token有效期时间较短，所有需要经常刷新，所以微信提供了refresh_token来刷新，该token的有效期为30天
let refresh_token_url='https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN'

//  如果授权域为snsapi_userinfo，则需要拉取用户信息
let get_userinfo_url='https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN'
let returnUserinfo={
  openid:'',
  nickname:'',
  sex:'',
  province:'',
  city:'',
  country:'',
  headimgurl:'',
  privilege:'',
  unionid:'',
}
```

## 注意点

1. 公众号后台设置jssdk安全域名，不需要协议
2. 如果需要获取用户信息和支付，需要配置用户授权url
3. 支付目录只有当前目录有效，子目录无效