# java.security 包

Java Security API 中，一个 engine class 就是定义了一种加密服务，不同的 engine class 提供不同的服务。 engine class：

1. MessageDigest：对消息进行 hash 算法生成消息摘要（digest）。
2. Signature：对数据进行签名、验证数字签名。
3. KeyPairGenerator：根据指定的算法生成配对的公钥、私钥。
4. KeyFactory：根据 Key 说明（KeySpec）生成公钥或者私钥。
5. CertificateFactory：创建公钥证书和证书吊销列表（CRLs）。
6. KeyStore：keystore 是一个 keys 的数据库。Keystore 中的私钥会有一个相关联的证书链，证书用于鉴定对应的公钥。一个 keystore 也包含其它的信任的实体。
7. AlgorithmParameters：管理算法参数。KeyPairGenerator 就是使用算法参数，进行算法相关的运算，生成 KeyPair 的。生成 Signature 时也会用到。
8. AlgorithmParametersGenerator：用于生成 AlgorithmParameters。
9. SecureRandom：用于生成随机数或者伪随机数。
10. CertPathBuilder：用于构建证书链。
11. CertPathValidator：用于校验证书链。
12. CertStore：存储、获取证书链、CRLs 到（从）CertStore 中。

从上面这些 engine class 中，可以看出 JCA（Java 加密框架）中主要就是提供了 4 种服务：Digest、Key、Cert、Signature、Alogorithm。

1.  对消息内容使用某种 hash 算法就可以生成 Digest。
2.  利用 KeyFactory、KeyPairGenerator 就可以生成公钥、私钥。
3.  证书中心使用公钥就可生成 Cert。
4.  可以使用私钥和 Digest 就可以消息进行签名 Signature。
5.  不论是 Digest、Key、Cert、Signature，都要使用到算法 Algorithm。

## MessageDigest

此 MessageDigest 类为应用程序提供消息摘要算法的功能，例如 SHA-1 或 SHA-256。消息摘要是安全的单向散列函数，它采用任意大小的数据并输出固定长度的散列值。
MessageDigest 对象开始初始化。使用更新方法通过它处理数据。在任何时候都可以调用重置来重置摘要。一旦更新了所有要更新的数据，就应该调用其中一个摘要方法来完成哈希计算。
对于给定数量的更新，可以调用摘要方法一次。调用摘要后，MessageDigest 对象将重置为其初始化状态。

Java 规定每个平台都需要实现的这三种加密类型：

- MD5
  - 16 位字节长度
- SHA-1
  - 20 位字节长度
- SHA-256
  - 32 位字节长度

基本使用步骤：

1. 实例化一个 MessageDigest
2. 调用 digest 方法获取编码的字节数组
3. 把字节数组转换为 String 输出

```java
//  instance
MessageDigest md5=MessageDigest.getInstance("MD5");
String password="123456";
byte[] digestBytes=md5.digest(password.getBytes());
String cryptoPassword=byte2hex(digestBytes);

public String byte2hex(byte[] bytes){
  String hs="";
  String temp="";
  for(int n=0;n<bytes.length;n++){
    temp=Integet.toHexString(bytes[n]&&0xFF);
    if(temp.length()==1){
      temp="0"+temp;
    }
    hs+=temp;
  }
  return hs;
}
```
