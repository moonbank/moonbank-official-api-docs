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
| occupation     | user occupation   | NO|String
| position     | user  position  | NO|String
| annual_income     | user annual income    | NO|String| Annual user income in HKD|
| id_type     | user ID type  | YES|String| must be one of these [id types](#id-types)|
| country     | user ID country code  | YES|String| code must be one of these [id countries](#id-countries)|
| number     | user ID number   | YES|String
| expiry_date     | user ID expiry date   | YES|String| YYYY-MM-DD
| frontImg     | user ID front image  | YES|String| [image format](#image-data)|
| backImg     | user ID back image   | NO, For passport types, only one photo needs to be submitted, while for ID card types, two photos need to be submitted.|String|[image format](#image-data)|

**HTTP Response**
  Common response

### 3. User usd Recharge Info(Using digital currency)

**HTTP Request**

```javascript
    # Request
    
    POST /user/usdRechargeInfo
    
    example: https://test.moonbank.me/api-web/user/usdRechargeInfo
    
    #body

    {
        "amount":100.00
    }
```
***request fields***

|field | description|required|type|
| ---------- |:-------:|-------|---|
| amount     | Recharge Amount |NO|Number

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


The testing environment does not provide a charging address. If you want to test this API, please use uid= 'eoi7g774uuuyrasz'


**HTTP Response**
```javascript
    # Response
{
    "success": true,
    "result": "0252e1e822c50faeaa28549b037dee5633f2e2c7664f1fa03fe4d531fb9710a983ffa7002dc27fd3acb29b6442deb266f151e77ee647df196821d505a4c0d7696a860fa49598273a77055d95f9fb84d6547d227ae0479184c049c03fe102f22fd3e0c87a293fe934bf53eecbd8353cf1",
    "code": 1,
    "message": "Successful!"
}
```

result decrypted json string

```javascript
{
        "gateway": "https://test.moonbank.me/payment/#/?orderNo=MB230819193948569135325",
        "orderNo":"MB230819193948569135325"
}
```

**Result fields**


|field | description|type|
| ---------- |:-------:|---|
| gateway    | URL of payment gateway |String
| orderNo    | Unique Order Number |String


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
        "residenceAddress": "",
        "tag":"xxxxx",
        "userBankcardId":171 // NOT Required
    }
```

***extra request http header***

header 'uId' = uid ,value from [user register](#1-user-register) response;


***request fields***

|field | description|required|type|
| ---------- |:-------:|-------|---|
| bankcardId     | bankcard ID from card information list response  | YES |Number|
| userBankcardId     | When the card application is rejected and needs to be reapplied, this field needs to be passed, and the value also comes from this API  | NO |Number|
| residenceAddress     | The user's residential address must be detailed to the building number or house number. |NO, Only PHYSICAL card need this field |String
| tag     | Custom Tag Information  | NO |String|

**HTTP Response**

result decrypted json string

```javascript
    {
        "cardNo": "424242 **** **** ****",
        "userBankcardId": 137,
        "status": "AUDITING",
        "tag":"xxxxx"
    }
```
**Result fields**

|field | description|
| ---------- |:-------:|
|userBankcardId| user bankcard unique ID, parameter of API when doing any card operating |
|cardNo| card Number, Only after the card is approved can the complete card number be obtained |
|status| [card status enum](#card-status) |
| tag     | Custom Tag Information  |

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
* Physical card recharge will directly return the recharge result. In rare cases, it may not be possible to immediately return results due to communication with upstream networks.
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
### 5. Query Bankcard Balance
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
### 6. Query Bankcard Information
**HTTP Request**

```javascript
    # Request
    
    POST /bankcard/cardInfo

    example: https://test.moonbank.me/api-web/bankcard/cardInfo
    
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
        "tag":"xxx"
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
| tag     | Custom Tag Information  |



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
|recordNo| record unique No. |
|occurTime| card Number, Only after the card is approved can the complete card number be obtained. [milliseconds UNIX Time](https://en.wikipedia.org/wiki/Unix_time) |
|localCurrencyAmt| Local currency trans Amount |
|localCurrency| Local currency type |
|transCurrencyAmt| Transaction currency trans Amount|
|transCurrency| Transaction currency type |
|transStatus| Transaction status |
|transType| Transaction type |
|authType| Authorisation type (Purchase or ATM) |
|merchantName| Merchant name |

## Account related APIs
### 1. Query Account Asset
**HTTP Request**

```javascript
    # Request
    
    POST /account/asset

    example: https://test.moonbank.me/api-web/account/asset
    
    #body
    {
    }
```

***request fields***

NONE

**HTTP Response**

result decrypted json string

```javascript
    {
        "availableAmount": 398.000000000000000000,
        "currency": "USD",
        "frozenAmount": 25.000000000000000000
    }
```
**Result fields**

|field | description|
| ---------- |:-------:|
|availableAmount| Account available Amount |
|frozenAmount| Account frozen Amount |
|currency| currency |

### 2. Query Account Recharge records

**HTTP Request**

```javascript
    # Request
    
    POST /account/user/rechargeList

    example: https://test.moonbank.me/api-web/account/user/rechargeList
    
    #body
    {
        "pageNum": 1,
        "pageSize": 10,
        "uid": "ewaoaylm5ueywbib"
    }
```
***request fields***

|field | description|required|type|default value|
| ---------- |:-------:|-------|---|---|
| uid     | Specify UID to query user recharge records   | NO |String|
| pageSize     | Paging query, one page size |NO|Number|10|
| pageNum     | Paging query, which page |NO |Number|1|


**HTTP Response**

result decrypted json string

```javascript
    [{
        "amount": 8.260569000000000000,
        "createTime": 1692618341000,
        "fee": 0E-18,
        "receiveUSDValue": 8.26,
        "rechargeStatus": "SUCCESS",
        "symbol": "USDT",
        "uid": "ewao6vdunfdllre7"
},
        ...
    ]
```
**Result fields**

|field | description|
| ---------- |:-------:|
|address| User recharge to address |
|amount| Coin amount |
|createTime| Recharge create time |
|fee| Recharge fee |
|hash| Coin transaction hash ID|
|receiveUSDValue| recharge USD Value |
|rechargeStatus| Transaction status |
|symbol| Recharge coin type |
|uid| Recharge user ID |


## Notify related 

 ***Response structure***

```javascript
{
	"appId": "app_447770",
	"result": "e9ab9e50d2028d6388322aea74544206ce9df7669e474a7c6c2fb7fdce533cb5714915b4dab8d8369caac53dfb9386804aad938ac33d41a16f63871212d12d452f408be1fddc344849bc5c48c22ec8ace2839d29fb5601c2c52966d4f02b3f5e9e0711a4e55334256d9c14e0119d5d9fe9ae35fec26d2059fad87b755c2b384427e08b898f21a91715ef8268736156cf61a10d18d57d4a57700ebecf9e7afd36e2839d29fb5601c2c52966d4f02b3f5e802f0c406dc0145256bec3ee665c43032b44abe0cdb0fe8a5de3dd9871661c0e88edb4830bb9e22d9a81024e2a6c0a0f97208e8bf2de8bddc7b042d707eabab487c48d9b757e89fe0495a37709cf1ed5fe05e9b65ab8af4270d18e7fd0fdf9fb04495d45589367ef5f3bff5ea8d5b291a85c5bf9db14a120fb4176056bd453fce35f1e124d3a7de068d1fc33865ca334d8ffff9da47a110c15a8cc9a3e6bff208e8af3d7dc052e384f56c7df5e28b375693caa79921be63fc220fafe2d135397828a340ea84b6cf0c5d783eeaa39f6647851c063aacb452c49f6858d61e790304e05dbe488826d8257332914dbe6d4ffee4431070b04512b6cc9b34450c5812f45f02ca6c4b601a7ab0ad0f50577b5e88a77ba00280b1132e149e063b3aac78c547f15d7af052ea38581545bfd706a56",
	"type": "TRANSACTION_CREATED"
}
```

The result field is Base64 encoded and AES128 encrypted with the appSecret, need decrypt and decode to get the JSON response data.

### Notify types
|type | description|
| ---------- |:-------:|
|TRANSACTION_VERIFICATION_CODE| User transaction verification code,need send to user`s mobile or email|
|CARD_STATUS_CHANGE|Card status notify, Trigger when [card status](#card-status) change|
|TRANSACTION_CREATED| Card transactions detail information|
|CARD_RECHARGE_RESULT| Card Result, when [Recharge](#3-recharge-bankcard) return code=99997.When the results are clear, we will send this notification |
|COIN_RECHARGE_RESULT| User coin Recharge Result|

### 1.TRANSACTION_VERIFICATION_CODE
result decrypted json string

```javascript
{
        "cardNo": "4242424242413691", 
        "code": "123456",
        "createAt": 1691249025085,
        "userBankcardId": 145
}
```
**Result fields**

|field | description|
| ---------- |:-------:|
|userBankcardId| user bankcard unique ID |
|cardNo| user bankcard No. |
|code| verification code  |
|createAt| Trigger time |

### 2.CARD_STATUS_CHANGE

result decrypted json string

```javascript
{
        "createAt": 1691248718245,
        "reason": "提交資料不通過審批",
        "status": "AUDIT_NOT_PASS",
        "userBankcardId": 146,
        "cardNo":"4242424242413691"
}
```
**Result fields**

|field | description|
| ---------- |:-------:|
|userBankcardId| user bankcard unique ID |
|status| [Bankcard status](#card-status) |
|reason| Reason for failure to pass the review, only returned when not passed |
|code| verification code  |
|cardNo| Bankcard No. Other status returns except for failed review  |
|createAt| Trigger time |

### 3.TRANSACTION_CREATED

result decrypted json string

```javascript
{
        "cardNo": "4242424242413691",
        "createAt": 1691549830756,
        "currency": "USD",
        "transaction": {
                        "authType": "",
                        "id": 254,
                        "localCurrency": "USD",
                        "localCurrencyAmt": "+99.00",
                        "occurTime": 1691549808050,
                        "recordNo": "ef4248e4-d274-4f33-bc8c-0fad02b90c07",
                        "transCurrency": "HKD",
                        "transCurrencyAmt": "+771.20",
                        "transStatus": "posted",
                        "transType": "topup"
                        },
        "userBankcardId": 145
}
```
**Result fields**

|field | description|
| ---------- |:-------:|
|userBankcardId| user bankcard unique ID |
|status| [Bankcard status](#card-status) |
|currency| Fix value USD  |
|cardNo| Bankcard No. Other status returns except for failed review  |
|createAt| Trigger time |
|transaction| Transaction detail |
|transaction.authType| PURCHASE or ATM |
|transaction.id| Record ID |
|transaction.recordNo| Record unique No. |
|transaction.localCurrency| Card Local currency |
|transaction.localCurrencyAmt| Card Local currency amount |
|transaction.transCurrency| Card transaction currency |
|transaction.transCurrencyAmt| Card transaction currency amount |
|transaction.transStatus| Transaction Status |
|transaction.transType| Transaction Type |

### 4.CARD_RECHARGE_RESULT

result decrypted json string

```javascript
{
        "amount": 100.000000000000000000,
        "cardNo": "1111113485704136",
        "createAt": 1691550780785,
        "currency": "USD",
        "receiveAmount": 99.500000000000000000,
        "status": "SUCCESS",
        "userBankcardId": 140
}
```
**Result fields**

|field | description|
| ---------- |:-------:|
|userBankcardId| user bankcard unique ID |
|amount| User operate Amount |
|receiveAmount| Card receive Amount, Only returned when status is SUCCESS |
|currency| Operate Currency   |
|status| SUCCESS or FAILED , recharge result  |
|cardNo| Bankcard No. Other status returns except for failed review  |
|createAt| Trigger time |

### 5.COIN_RECHARGE_RESULT

result decrypted json string

```javascript
{
        "coinAmount":100,
        "symbol": "USDT",
        "createAt": 1692711247364,
        "currency": "USD",
        "orderNo": "MB230822103810254026143",
        "amount": 100,
        "receiveAmount": 97.75,
        "status": "SUCCESS",
        "uid": "eo3cqhz3lujehlar"
}
```
**Result fields**

|field | description|
| ---------- |:-------:|
|uid| Coin recharge user Id |
|coinAmount| Address receive coin Amount |
|symbol| Address receive coin symbol |
|amount| Address receive USD Value |
|receiveAmount| Account receive USD Value,The difference with ***amount*** is the handling fee(USD) |
|currency| Operate Currency   |
|status| SUCCESS or FAILED , recharge result  |
|orderNo| Payment Order No.  |
|createAt| Occur time |

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