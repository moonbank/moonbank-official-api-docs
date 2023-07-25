# moonbank官方文档

[Moonbank]API开发者文档([English Docs](./README.md))。

# 介绍

欢迎使用[Moonbank]开发者文档。 本文档提供了开户，设置用户KYC信息，卡片申请，充值，信息查询等相关API的使用方法介绍。

# API接口加密验证

## 生成 appId 和 appSecret

## 发起请求

所有REST请求都必须包含以下参数：

* appId作为一个字符串,需以 **appId** 作为key添加到所有请求的header中。
* sign 使用一定算法得出的签名（请参阅签名信息），需以**sign** 作为key添加到所有请求的header中。
* 所有请求都应该含有 **application/json;charset=UTF-8** 类型内容，并且是有效的JSON字符串。

## 签名

sign 参数是使用对应接口**PATH** 和对 **所有参数按照字典排序之后转换为JSON字符串**拼接，进行 **BASE64**编码，然后使用 **appSecret**作为密钥进行 **AES128** 加密得到。

例如：对于如下的请求参数进行签名,密钥字符串为 **123456**

```bash
curl  "https://test-api.moonbank.me/user/register"
```
* 注册账户，以 mobileNumber=13800138000, mobilePrefix=86 为例
```java
mobilePrefix = 86
mobileNumber = 18888888888 
```
* 按字典排序之后，为
```java
mobileNumber = 18888888888
mobilePrefix = 86
```
* 生成待加密的字符串
```
dataJson = '{"mobileNumber":"18888888888","mobilePrefix":"86"}'
```

* 接口PATH 与 dataJson 拼接
```
originString = '/user/register{"mobileNumber":"18888888888","mobilePrefix":"86"}'
```

* 接口originString 进行**base64**编码
```
base64String = 'L3VzZXIvcmVnaXN0ZXJ7Im1vYmlsZU51bWJlciI6IjE4ODg4ODg4ODg4IiwibW9iaWxlUHJlZml4IjoiODYifQ=='
```

* 使用secret作为密钥对base64String 进行**AES128** 加密
```
signature = '6EpHJVo/OhmIesRC5z5chgnPrbGf03G5DW3LQj61vkJ1gONBjJrErj3TI2fRJC7zOO80RtqvpxtnpYsPnBBnQkxZ7eDQdHw4mKBmnV1rCfcveZGzy9UyjMywbej0kz4y'
```

* 将appId 与 加密后得到的签名字符串，分别放入请求的header中
``` java
request.header("appId", xxxxxx);
request.header("sign", "6EpHJVo/OhmIesRC5z5chgnPrbGf03G5DW3LQj61vkJ1gONBjJrErj3TI2fRJC7zOO80RtqvpxtnpYsPnBBnQkxZ7eDQdHw4mKBmnV1rCfcveZGzy9UyjMywbej0kz4y");
```

## 响应数据

所有接口返回数据均为有效JSON数据。结构如下：
```javascript
{
        "total":9, 
        "success": true,
	"result": "JkLoxhGUGR8WB3ze8X1HfRAFnv0DJ5zy+Bm//Zvk4TII9XC+n3ppjhm2OFes0Wrh",
	"code": 1,
	"message": "Successful!"
}
```
result 字段为数据实体，需要使用密钥进行AES128 解密，然后进行base64解码。得到完整的JSON字符串。



[Moonbank]: https://www.moonbank.me

