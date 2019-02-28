## openssl

1. 生成原始 RSA私钥文件 private_key.pem

openssl genrsa -out private_key.pem 1024
2. 将原始 RSA私钥转换为 pkcs8格式

openssl pkcs8 -topk8 -inform PEM -in private_key.pem -outform PEM -nocrypt -out rsa_private_key.pem
3. 生成 RSA公钥 rsa_public_key.pem

openssl rsa -in private_key.pem -pubout -out rsa_public_key.crt
4. 从公钥 rsa_public_key.pem 获取十六进制的公钥（第一段16进制字符串）

openssl asn1parse -out temp.ans -i -inform PEM<private_key.pem
最终获取文件列表:

rsa_private_key.pem

rsa_public_key.crt

16进制公钥字符串用于js端加密