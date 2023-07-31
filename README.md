# moonbank-official-api-docs
Official Documentation for the [Monnbank][] APIs and Streams([简体中文版文档](./README_ZH.md))。

# Introduction

Welcome to [Monnbank][] API document for developers.

This document provides an introduction to the use of related APIs such as account management,account kyc, card apply and recharge etc.

# Encrypted Verification of API

## Initiate a Request

All REST requests must include the following headers:

* appId ,need use **appId** as the key, put in all requests header;
* sign Signature generated using a certain algorithm ，need use **sign** as the key, put in all requests header;
* All requests should set 'content-type' as  **application/json;charset=UTF-8** and be valid JSON.

All REST requests must use ***POST*** method, and the request body is valid JSON.

## Signature

sign is generate by the request path + dictionary sorted parameters JSON string (+ means string connection), then ***Base64*** encode, then ***AES128*** encrypt, then encrypt the string with ***MD5 (32 bits)***.

**For example: sign the following request parameters** (the AppSecret=***123456***)

```bash
curl  "https://test-api.moonbank.me/user/register"
```

* Request parameters are passed in JSON format, parameter example:

```javascript
  {
        "mobileNumber": "18888888867", 
        "mobilePrefix": "1",
        "email": "188888888662@188.com"
  }
```

* Dictionary sorted
```javascript
  {
        "email": "188888888662@188.com",
        "mobileNumber": "18888888867",
        "mobilePrefix": "1"
  }
```

* Parameters json string
```javascript
dataJson = '{"email":"188888888662@188.com","mobileNumber":"18888888867","mobilePrefix":"1"}'
```

* request path + json string

```javascript
originString = '/user/register{"email":"188888888662@188.com","mobileNumber":"18888888867","mobilePrefix":"1"}'
```

* originString ***base64*** encode
```javascript
base64String = 'L3VzZXIvcmVnaXN0ZXJ7ImVtYWlsIjoiMTg4ODg4ODg4NjYyQDE4OC5jb20iLCJtb2JpbGVOdW1iZXIiOiIxODg4ODg4ODg2NyIsIm1vYmlsZVByZWZpeCI6IjEifQ=='
```

* base64String **AES128** encrypt
```javascript
aes128String = '6EpHJVo/OhmIesRC5z5chsJFd7HW5jKsoqg1WbV1iflLTqmsyuhXKuvQKdNqN0MKw1wwiWHbbp0cRWltjfXzMZ4Qf0lycz/lEb/OMf/v48S1R7xgwnHkS7qXzFuDY6PA8v9571Y8BXiaoBhUaIbsnn58fbBx8YhPqVs2ioibEkTLU7lmuwCFIuM3N/RMY7Tn'
```

* aes128String**MD5(32bit)** encode
```javascript
signature = 'cc4991ec87ccb8f3118a1987b642bd3b'
```

* put appId and the signature into the request header
``` java
request.header("appId", xxxxxx);
request.header("sign", "cc4991ec87ccb8f3118a1987b642bd3b");
```

## Response structure

All response is valid JSON as the follow structure
```javascript
{
        "total":9, // size of list when the result is list data
        "success": true, 
	"result": "JkLoxhGUGR8WB3ze8X1HfRAFnv0DJ5zy+Bm//Zvk4TII9XC+n3ppjhm2OFes0Wrh", // result data, Base64 encoded and  AES128  encrypted with the appSecret  
	"code": 1, // response code , 1 is success ,other is failed
	"message": "Successful!" // response description
}
```
The result field is Base64 encoded and AES128 encrypted with the appSecret, need decrypt and decode to get the JSON response data.

**For example: parse the following response** (the AppSecret=***123456***)

Response of use register
```javascript

{
    "success": true,
    "result": "9LOSTUUpwaho2yDtmNVfLWgqVcH+wfWKtfLpBxFLvonk8quZi5mMCnhsbN84DT1P",
    "code": 1,
    "message": "Successful!"
}
```
***AES128*** decrypt and ***Base64*** decode,get the valid JSON string

```javascript
{
    "uid": "hgeojte3awbrcp76"
}
```




[Monnbank]: https://www.moonbank.me