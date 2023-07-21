# moonbank官方文档

[Moonbank]API开发者文档([English Docs]())。

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

例如：对于如下的请求参数进行签名


[Moonbank]: https://www.moonbank.me

[English Docs]: https://github.com/moonbank/moonbank-official-api-docs/blob/master/README.md