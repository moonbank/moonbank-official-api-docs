# Moonbank-official-api-docs
Official Documentation for the [Monnbank][] APIs and Streams([简体中文版文档](./README_ZH.md)). 
This project is the java version demo. you can download and run it for testing. 

# Introduction

Welcome to [Monnbank][] API document for developers.

This document provides an introduction to the use of related APIs such as account management,account kyc, card apply and recharge etc.

# Encrypted Verification of API

## Get an API Key

Before signing any request, you must get the API from sales.

Demo appId and appSecret:

* appId='app_447770'

* appSecret='b635dd5c87f7bf73387929203321b1e1'

## Initiate a Request

All REST requests must use ***POST*** method ,include the following headers, and the request body is valid JSON.

* appId, need use **appId** as the key, put in all requests header;
* Signature generated using a certain algorithm, need use **sign** as the key, put in all requests header;
* All requests should set header ***'content-type'*** as  **application/json;charset=UTF-8** and request body be valid JSON(***if the path not need parameters, the body should be {}***).
* Some requests should set header ***'uId'***  with the value from user register interface. 

## Signature

sign is generated by the request path + dictionary sorted parameters JSON string (+ means string connection), then ***Base64*** encode, then ***AES128*** encrypt, then encrypt the string with ***MD5 (32 bits)***.

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

* base64String **AES128** encrypt using ***appSecret*** as the key
```javascript
aes128String = 'aa71f61b66b0436856be41b40ea099029bc25e43ae01d39f7332a8a56da3214d5b054c32c702142ade9a87f98cc3fca43a7625413671dff58fc6678faa760466b012d42a2b396c6218d04973b1fba60de82e593d22ddcf9f54f7fccb2cef876fe2e96e2276f672b44ebf6207ca823b0a0229ad71fa98c286f14adede9283d160d6ec88adf38227242f491052e1bd3edc'
```

* aes128String **MD5(32bit)** encrypt
```javascript
signature = 'f61ff896372b9e5836cb67a069dd810a'
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

**For example: Parse the following response** (the AppSecret=***b635dd5c87f7bf73387929203321b1e1***)

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

Please refer to the detailed meaning of the [Response code](#response-codes).


# Moonbank APIs

## User related APIs

### 1. User Register

**HTTP Request**

```javascript
    # Request
    
    POST /user/register
    
    example: https://test.moonbank.me/api-web/user/register
    
    #body

    {
        "email": "188888888662@188.com",
        "mobileNumber": "18888888867",
        "mobilePrefix": "1"
    }
```
***request fields***

|field | description|required|type|
| ---------- |:-------:|-------|---|
| email     | user email, should not be used | YES |String
| mobileNumber     | user phone number,should not be used |YES|String
| mobilePrefix     | user phone country code(China 86, Korea 82, Japan 81 etc. )   | YES|String

**HTTP Response**
```javascript
    # Response
{
    "success": true,
    "result": "bOodDEqstZ82BRjTuLRE5PBmcIixXsMxNPOVqS+iyBfk8quZi5mMCnhsbN84DT1P",
    "code": 1,
    "message": "Successful!"
}
```

result decrypted json string

```javascript
{
    "uid": "hgwoxhlpzvav6m2l"
}
```

**Result fields**


|field | description|type|
| ---------- |:-------:|---|
| uid      | user unique ID, some requests set 'uId' request header with this value |String



### 2. Set User profession

**HTTP Request**

```javascript
    # Request
    
    POST /user/setProfession

    example: https://test.moonbank.me/api-web/user/setProfession
    
    #body
    {
        "annual_income": "100000",
        "birthday": "2000-01-01",
        "country": "CN",
        "expiry_date": "2027-01-01",
        "first_name": "ming",
        "first_name_en": "ming",
        "id_type": "passport",
        "last_name": "li",
        "last_name_en": "li",
        "number": "123456",
        "occupation": "boss",
        "position": "management",
        "frontImg":"data:image/jpg;base64,xxxxxxxx..........",
        "backImg":"data:image/jpg;base64,xxxxxxxx.........."
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|format|
| ---------- |:-------:|-------|---|---|
| first_name     | user first name | YES |String||
| last_name     | user last name |YES|String
| first_name_en     | user english first name  | YES|String
| last_name_en     | user english last name   | YES|String
| birthday     | user birthday   | YES|String| YYYY-MM-DD
| occupation     | user occupation   | YES|String
| position     | user  position  | YES|String
| annual_income     | user annual income    | YES|String| Annual user income in HKD|
| id_type     | user ID type  | YES|String| must be one of these [id types](#id-types)|
| country     | user ID country code  | YES|String| code must be one of these [id countries](#id-countries)|
| number     | user ID number   | YES|String
| expiry_date     | user ID expiry date   | YES|String| YYYY-MM-DD
| frontImg     | user ID front image  | YES|String| [image format](#image-data)|
| backImg     | user ID back image   | YES|String|[image format](#image-data)|

**HTTP Response**
  Common response

## Bankcard related APIs
### 1. Bankcard Information List

**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/template/list
    
    example: https://test.moonbank.me/api-web/bankcard/template/list
    
    #body
    {
        
    }
```
***request fields***

    NONE

***No request parameters ,but should post empty json data "{}" to server***

**HTTP Response**

result decrypted json string

```javascript
    # Response result decode 
    [{
    "applyDiscount": 1,
    "applyFee": 300.000000000000000000,
    "bankCardNature": "PHYSICAL",
    "bankCardSource": "SP",
    "bankCardType": "MASTER",
    "categoryId": 1,
    "ccy": "USD",
    "description1": "闪电支付，无缝衔接",
    "description2": "让你的NFT消费流通起来",
    "enable": true,
    "hot": false,
    "id": 9,
    "img": "https://test.moonbank.me/static-res/bankcard/1688961772590.png",
    "monthFee": 0.00,
    "rechargeFee": 0.010000000000000000,
    "recommend": true,
    "sortParam": 20,
    "title": "Moonbank  优月卡"
    },
    ...
    ]
```
**Result fields**

|field | description|
| ---------- |:-------:|
|applyFee| apply fee(USD) |
|applyDiscount| discount of apply, real cost of applying is applyDiscount*applyFee |
|bankCardNature| PHYSICAL or VIRTUAL |
|bankCardType| MASTER or VISA |
|ccy| card currency |
|description1| card description1 |
|description2| card description2|
|id| ***bankcard ID, parameter of [Apply card api](#2-apply-bankcard)***  |
|enable| is allowed to apply |
|img| card demo image |
|monthFee| Month fee of card(USD); Automatic deduction on the 7th of each month |
|rechargeFee| recharge fee rate, like 0.01 |
|sortParam| sort parameter |
|title| Card name |

### 2. Apply Bankcard

**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/apply

    example: https://test.moonbank.me/api-web/bankcard/apply
    
    #body
    {
        "bankcardId": 9,
        "residenceAddress": ""
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|
| ---------- |:-------:|-------|---|
| bankcardId     | bankcard ID from card information list response  | YES |Number|
| residenceAddress     | The user's residential address must be detailed to the building number or house number. |NO, Only PHYSICAL card need this field |String


**HTTP Response**

result decrypted json string

```javascript
    {
        "cardNo": "424242 **** **** ****",
        "userBankcardId": 137,
        "status": "AUDITING"
    }
```
**Result fields**

|field | description|
| ---------- |:-------:|
|userBankcardId| user bankcard unique ID, parameter of API when doing any card operating |
|cardNo| card Number, Only after the card is approved can the complete card number be obtained |
|status| [card status enum](#card-status) |

### 3. Recharge Bankcard

**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/recharge

    example: https://test.moonbank.me/api-web/bankcard/recharge
    
    #body
    {
    "amount": 16,
    "userBankcardId": 135
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|
| ---------- |:-------:|-------|---|
| userBankcardId     | user bankcard ID from card apply response  | YES |Number|
| amount     | Recharge amount |YES |String


**HTTP Response**

Common response
NOTE
* Physical card recharge will directly return the recharge result. 
* The virtual card has some cards with bin cards and cannot directly return recharge results(The [Response code](#response-codes) is 99997). You need to process notify the recharge results in webhook.

### 4. Set Bankcard ATM Pin

**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/setPin

    example: https://test.moonbank.me/api-web/bankcard/setPin
    
    #body
    {
    "pin": "123456",
    "userBankcardId": 136
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|format|
| ---------- |:-------:|-------|---|---|
| userBankcardId     | user bankcard ID from card apply response  | YES |Number||
| pin     | user ATM password |YES |String|6-digit password|


**HTTP Response**

Common response
### 5. Query Bankcard Information
**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/balance

    example: https://test.moonbank.me/api-web/bankcard/balance
    
    #body
    {
    "userBankcardId": 136
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|default value|
| ---------- |:-------:|-------|---|---|
| userBankcardId     | user bankcard ID from card apply response   | YES |Number|


**HTTP Response**

result decrypted json string

```javascript
    {
    "balance": 456.34
    }
```
**Result fields**

|field | description|
| ---------- |:-------:|
|balance| Bankcard Balance Amount (USD) |
### 6. Query Bankcard Balance
**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/balance

    example: https://test.moonbank.me/api-web/bankcard/balance
    
    #body
    {
    "userBankcardId": 136
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|default value|
| ---------- |:-------:|-------|---|---|
| userBankcardId     | user bankcard ID from card apply response   | YES |Number|


**HTTP Response**

result decrypted json string

```javascript
   {
        "bankCardId": 132,
        "cardCvv": "195",
        "cardNo": "1111112826444128",
        "currency": "USD",
        "expiryDate": "202707",
        "monthFee": 0.00,
        "rechargeDiscount": 1,
        "rechargeFee": 0.01,
        "userBankCardStatus": "ACTIVE",
        "hashHolderInfo": true,
        "vccCardHolderVo": {
            "billingAddress": "3836  Harvest Lane",
            "billingCity": " Los Angeles",
            "billingState": "California",
            "billingZipCode": "90014",
            "birthDate": "1983-10-01",
            "countryCode": "America",
            "email": "123321@moonbank.me",
            "firstName": "jiang",
            "id": 4,
            "lastName": "liu",
            "middleName": "luo",
            "mobile": "18900889980",
            "mobilePrefix": "86"
    }
}
```
**Result fields**

|field | description|
| ---------- |:-------:|
|cardCvv| Bankcard Cvv code, Only Virtual Card return this Field |
|cardNo| Bankcard  Number |
|currency| Bankcard currency |
|expiryDate| Bankcard Expiry Date |
|monthFee| Bankcard Month Fee (USD) |
|rechargeFee| Bankcard Recharge fee rate. like 0.01 |
|rechargeDiscount| Bankcard Recharge Fee discount. rechargeDiscount*rechargeFee= real Recharge fee rate |
|userBankCardStatus| [Bankcard status](#card-status) |
|hashHolderInfo| Some Virtual need card holder information,if this value is true, ***vccCardHolderVo*** is detail holder information  |
|vccCardHolderVo| Card Holder Detail Information |
|vccCardHolderVo.firstName| Card Holder firstName  |
|vccCardHolderVo.middleName| Card Holder middleName  |
|vccCardHolderVo.lastName| Card Holder lastName |
|vccCardHolderVo.mobilePrefix| Card Holder mobilePrefix |
|vccCardHolderVo.mobile| Card Holder mobile |
|vccCardHolderVo.email| Card Holder firstName |
|vccCardHolderVo.countryCode| Card Holder  countryCode|
|vccCardHolderVo.birthDate| Card Holder birthDate|
|vccCardHolderVo.billingAddress| Card Holder billingAddress |
|vccCardHolderVo.billingCity| Card Holder billingCity |
|vccCardHolderVo.billingState| Card Holder billingState |
|vccCardHolderVo.billingZipCode| Card Holder billingZipCode |



### 7. Query Bankcard Transactions

**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/transactions

    example: https://test.moonbank.me/api-web/bankcard/transactions
    
    #body
    {
        "endTimestamp": 1690878578000,
        "fromTimestamp": 1690878577000,
        "pageNum": 1,
        "pageSize": 10,
        "userBankcardId": 135
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|default value|
| ---------- |:-------:|-------|---|---|
| userBankcardId     | user bankcard ID from card apply response   | YES |Number|
| pageSize     | Paging query, one page size |NO|Number|10|
| pageNum     | Paging query, which page |NO |Number|1|
| fromTimestamp     | from time(The time range contains values equal to from time.)  |NO |[milliseconds UNIX Time](https://en.wikipedia.org/wiki/Unix_time)|NONE
| endTimestamp     | end time(The time range does not include an occur time that is equal to or greater than.)  |NO |[milliseconds UNIX Time](https://en.wikipedia.org/wiki/Unix_time)|NONE


**HTTP Response**

result decrypted json string

```javascript
    [{
        "id": 191,
        "localCurrency": "HKD",
        "localCurrencyAmt": "-24.27",
        "merchantName": "MEITUAN FOOD           BEIJING       CHN",
        "occurTime": 1690940850000,
        "recordNo": "57724528-78b6-4ae2-919f-1efff348f460",
        "transCurrency": "CNY",
        "transCurrencyAmt": "-21.90",
        "transStatus": "Pending",
        "transType": "Expenditure"
    },
        ...
    ]
```
**Result fields**

|field | description|
| ---------- |:-------:|
|recordNo| user bankcard unique ID, parameter of API when doing any card operating |
|occurTime| card Number, Only after the card is approved can the complete card number be obtained. [milliseconds UNIX Time](https://en.wikipedia.org/wiki/Unix_time) |
|localCurrencyAmt| Local currency trans Amount |
|localCurrency| Local currency type |
|transCurrencyAmt| Transaction currency trans Amount|
|transCurrency| Transaction currency type |
|transStatus| Transaction status |
|transType| Transaction type |
|authType| Authorisation type (Purchase or ATM) |
|merchantName| Merchant name |


# Fields ENUM Description

### Response codes
|code | description|
| ---------- |:-------:|
| 1| SUCCESS|
|2|Parameters illegal|
|200|Common business error, detail in the message|
|212|Account balance not enough |
|99997|The business result is unknown, and we need to wait for the result notification or actively query the result|

### ID types
|type | description|
| ---------- |:-------:|
| passport| User Passport|
|national-id|User ID card|

### ID countries
|code | country|passport support| id card support|
| ---------- |:-------:|---|---|
| CN| China| YES|YES|
|HK|China Hong Kong|YES|YES|
|KOR|Korea|YES|NO|
|JP|Japan|YES|NO|
|SG|Singapore|YES|NO|
|UK|Britain|YES|NO|
|US|America|YES|NO|

### Card status
|status | description|
| ---------- |:-------:|
|AUDITING|Card approval in progress|
|AUDIT_PASS|Card approval passed|
|AUDIT_NOT_PASS|Card approval not passed|
|TBA|Card to be activated (automatic activation for first time card recharge)|
|ACTIVE_PROCESSING|Card activation processing is in progress (some cards cannot synchronously return results and need to wait for notification or actively query results)|
|ACTIVE|Card active|
|CLOSE_PROCESSING|Card closing processing is in progress(some cards cannot synchronously return results and need to wait for notification or actively query results)|
|CLOSE|Card been closed|
|EXCHANGE_PROCESSING|Card replacement processing in progress|

# Image data

[java demo](./src/main/java/com/moonbank/utils/Base64ImgUtil.java) or [php demo](./PHP-signature-Demo/image-base64-demo.php)

image data format should be :
    "data:"+ image mine info +";base64," + image base64 encode data.
    


[Monnbank]: https://www.moonbank.me