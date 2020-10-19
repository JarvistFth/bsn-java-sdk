# 区块链服务网络（BSN）接入Java-SDK说明文档

## 1、配置对象设定
在发送http请求与bsn交互之前，必须先构造出一个Config对象，然后在config对象中填充下列字段，作为配置。

config对象的字段意义如下：

字段|类型|含义|必填字段
| :-----: | :------: |:----------------------------- |:---:|
AppCode|String|bsn中我们部署应用的AppCode，目前是“app0001202006081111440843077”|是
UserCode|String|参加bsn节点交互的userCode，每个userCode对应有自己的证书和私钥，目前我们的UserCode是“USER0001202006050930126612022”|是
Api|String|与bsn节点进行通信的地址，目前有三个节点，分别在衢州、北京、和广州南基<br>南基api地址为:https://nanjinode.bsngate.com:17602<br>北京api地址为：http://180.76.133.31:17502/<br>衢州api地址为：https://quzhounode.bsngate.com:17602/|是
Cert|String|bsn节点进行https通讯时所用的https证书，目前在resources文件夹下的certs/bsn_gateway_https.crt|https通讯则是
Prk|String|用户自己的私钥，用于对上传数据进行签名；bsn节点会对上传的数据用用户的公钥进行验签，目前存放在resources目录下对应三个节点的文件夹下的userAppCert/private_key.pem|是
Puk|String|bsn网关节点的公钥，当用户接收到bsn节点返回的消息时，可以用这个公钥对bsn节点返回的消息进行验签。目前存放在resources目录下对应三个节点的文件夹下的gatewayCert/gateway_public_cert_secp256r1.pem|是
MspDir|String|这里是设置本地上传公钥模式下的用户自己生成的公私钥对和密钥证书的存放地址|否。本地上传公钥模式则需要设置。
---
<b>示例代码：</b>

```java
//first init config
        //创建config对象
        Config config = new Config();
        //设置bsn上对饮的AppCode
        config.setAppCode("app0001202006081111440843077");
        //设置参加bsn节点交互的userCode，每个userCode对应有自己的证书和私钥
        config.setUserCode("USER0001202006050930126612022");
        //与bsn节点进行通信的地址，目前有三个节点，分别在衢州、北京、和广州南基
        config.setApi("https://quzhounode.bsngate.com:17602");
        //设置与bsn节点交互的https证书
        config.setCert("certs/bsn_gateway_https.crt");
        //用户自己的私钥，用于对上传数据进行签名；bsn节点会对上传的数据用用户的公钥进行验签
        config.setPrk("certs/quzhou/userAppCert/private_key.pem");
        //bsn网关节点的公钥，当用户接收到bsn节点返回的消息时，可以用这个公钥对bsn节点返回的消息进行验签。
        config.setPuk("certs/quzhou/gatewayCert/gateway_public_cert_secp256r1.pem");
        //这里是设置本地上传公钥模式下的用户自己生成的公私钥对和密钥证书的存放地址
        config.setMspDir("D:/test");

```

## 2、调用智能合约

调用智能合约主要分为两步：
1. 构造调用智能合约的http请求。在bsn-java-sdk中，是构造ReqKeyEscrow类的对象，然后在对象中填充必填字段。
2. 将构造好的对象，传入TransactionService类的reqChaincode方法中作为参数，进行正式调用。



构造的请求对象ReqKeyEscrow的字段参数含义如下：
```java
    String userName;
    String nonce;
    String chainCode;
    String funcName;
    String[]  args;
    Map<String,String> transientData;
```
字段|类型|含义|必填字段
| :---: | :---: | :----------------- | :--------:|
userName|String|bsn网络用户注册时的用户名|否
nonce|String|随机字符串，使用base64编码的24位随机byte数据，在reqChaincode方法内部已经嵌入了设置nonce的方法，所以可以不需要再在调用的时候设置nonce。|是
chainCode|String|bsn网络上调用智能合约的名称标识，目前的值是“cc_app0001202006081111440843077_00”|是
funcName|String|要调用的智能合约的方法名称。一个智能合约可能有很多方法可供调用，所以要传入要调用的方法名称。具体每个智能合约方法名称在文档第三点中说明。|是
args|String[]|调用智能合约的时候，可能需要往智能合约中传递参数。args字符串数组是传递给智能合约对应方法参数的json形式。当调用智能合约的方法不需要传入参数时，可以为空。<br>具体每个合约的每个方法的传入参数请看第三点。|否
transientData|Map<String,String>|保存到bsn节点上的一个暂时性的键值对数据|否

---
<b>示例代码如下：</b>

```java
//调用智能合约的 http请求对象
@Test
    public void reqCreateBusinessActivity() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Business business = new Business();
        business.setUid("0001");
        business.setBid("test01");
        business.setMenu("test01");
        business.setActivity("actest01");
        business.setFunds("100");
        business.setCreate_time("testdate");
        String[] args = {JSON.toJSONString(business)};
        reqkey.setArgs(args);
        reqkey.setFuncName("createBusinessActivity");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }


```
# 3、调用返回字段说明：

序号|字段名称|字段|类型|是否必填|备注
| :---: | :---: | :---: | :--------:| :---: | :---: |
1|信息头|header|ResHeader|是|
2|信息体|body|ResKeyEscrow|是|
3|签名值|mac|String|是
---
header字段
序号|字段名称|字段|类型|是否必填|备注
| :---: | :---: | :---: | :--------:| :---: | :---: | 
1|响应标识|code|int|是|0：校验成功；-1：校验失败
2|响应信息|msg|String|否|code==0时可为null
---
body字段：
序号|字段名称|字段|类型|是否必填|备注
| :---: | :---: | :---: | :--------:| :---: | :---: | 
1|块信息|blockInfo|BlockInfo|否|code不为0时为空|
2|智能合约响应结果|ccRes|CcRes|否|code不为0时为空|

blockInfo字段：
序号|字段名称|字段|类型|是否必填|备注
| :---: | :---: | :---: | :--------:| :---: | :---: | 
1|交易Id|txId|String|是|
2|块哈希|blockHash|String|否|同步接口返回块哈希|
3|状态值|status|Int|是|详见交易状态描述

ccRes字段：
序号|字段名称|字段|类型|是否必填|备注
| :---: | :---: | :---: | :--------:| :---: | :---: | 
1|智能合约响应状态|CCCode|Int|是|200：成功；500：失败|
2|智能合约响应结果|ccData|String|否|具体智能合约响应的结果|

返回示例：
```json
{
	"header": {
		"code": 0,
		"msg": "success"
	},
	"mac": "MEQCIAcXHapS44cG2ObeEOGKYXJI/kEpGZhEYFjacWudsuh7AiAwr2QBQFQhDWQylNeuIAKU9x+uCh3SVqvrNP+gLd61EQ==",
	"body": {
		"blockInfo": {
			"txId": "c75091475e9cdab5af1f8325d19069c9aab865862d9434a5f772f1159d569048",
			"blockHash": "",
			"status": 0
		},
		"ccRes": {
			"ccCode": 200,
			"ccData": "test2"
		}
	}
}
```

# 4、智能合约方法及参数说明

为方便参数的传递，在bsn-java-sdk中的chaincodeEntities包中，封装了要传递的参数的java类，当需要将参数传递给智能合约时，只需要创建一个对象实例，然后通过JSON.toJSONString，将对象序列化为json字符串，然后传入第二点中提到的args参数即可。

## 4-1 人才数据智能合约方法名称及参数说明

方法名称|功能|参数类型|参数含义|返回值类型|返回值|备注
| :---: | :---: | :---: | :---: | :---: | :---| :---: |
createProfile|保存人才数据到区块链上|String[]|Profile类的json序列化字符串|String|成功返回OK，否则返回Error的信息
getProfileByID|查询人才数据|String[]|Profile类的id，json序列化字符串|String|成功则返回Profile的各个字段；否则返回Error的信息
updateProfile|更新人才数据|String[]|Profile类的json序列化字符串|String|成功则返回OK；否则返回Error的信息
deleteProfile|删除人才数据|String[]|Profile类的json序列化字符串|String|成功则返回OK；否则返回Error的信息
getProfileHistory|查询人才档案的历史修改记录|String[]|Profile类的id，json序列化字符串|String|成功返回历史记录的数据结构，否则返回Error信息
---

bsn-java-sdk中的人才数据实体类：

```java
public class Profile {
    //|      id       |  Long  | 人才id                        |
    //|     name      | String | 姓名                          |
    //| identity_card | String | 身份证hash值                  |
    //|   card_type   |  int   | 证件类型 1：身份证；2：护照； |
    //|   passport    | String | 护照hash值                    |
    //|    open_id    | String | 微信唯一标识                  |
    //|    wx_card    | String | 微信会员卡id                  |
    //|  wx_card_num  | String | 会员卡号                      |
    //|    action     | String | 用户动作                      |
    //|    module     | String | 用户使用模块                  |
    //| extra | String | 扩展字段 |


        @JSONField(name = "id")
        String id;
    
        @JSONField(name = "name")
        String name;
    
        @JSONField(name = "identity_card_Hash")
        String identity_card;
    
        @JSONField(name = "card_type")
        String card_type;
    
        @JSONField(name = "passport")
        String passport;
    
        @JSONField(name = "wx_openid")
        String openid;
    
        @JSONField(name = "wx_card")
        String wx_card;
    
        @JSONField(name = "wx_card_num")
        String wx_card_num;
    
        @JSONField(name = "action")
        String action;
    
        @JSONField(name = "module")
        String module;
    
        @JSONField(name = "extra")
        String extra;
}
```


---
## 4-2 人才申请政策智能合约参数字段说明：

方法名称|功能|参数类型|参数含义|返回值类型|返回值|备注
| :---: | :---: | :---: | :---: | :---: | :---| :---: |
apply|人才发起政策申请|String[]|Application类的json序列化字符串|String|如果成功返回OK，否则返回Error的具体信息|
getApplicationInfo|查询某次申请的详细信息|String[]|Application类的id,json序列化字符串|String|返回Application类的各个字段
updateApplication|更新申请政策各个字段|String[]|Application类的json序列化字符串|String|如果成功返回历史状态结构，否则返回Error的具体信息|
getHistoryForApplication|追溯某次申请的状态更改历史|String[]|Application类的json序列化字符串|String|如果成功返回历史状态结构，否则返回Error的具体信息|

---
bsn-java-sdk中的人才申请实体类：
```java
public class Application {
//| id     | String | 申请唯一id |
//| uid    | String | 人才id     |
//| pid    | String | 政策id     |
//| policy | String | 政策名称   |
//| status | String | 申请状态   |
//| ap_id  | String | 审批人id   |
//| extra | String | 扩展字段 |

    //申请唯一id
    @JSONField(name = "id")
    String id;

    //人才id
    @JSONField(name = "uid")
    String uid;
    //政策id
    @JSONField(name = "pid")
    String pid;

    //政策名称
    @JSONField(name = "policy")
    String policy;

//    申请状态
    @JSONField(name = "status")
    String status;

//    审批人id
    @JSONField(name = "ap_id")
    String ap_id;

    public Application() {
    }

    //拓展字段
    @JSONField(name = "extra")
    String extra;

}

```


---
## 4-3 政府部门审批发放政策智能合约参数字段说明：



方法名称|功能|参数类型|参数含义|返回值类型|返回值|备注
| :---: | :---: | :---: | :---: | :---: | :---| :---: |
createBusinessActivity|创建政策数据到区块链上|String[]|Business类的json序列化字符串|String|成功返回OK，否则返回Error的信息
getBusinessActivityInfoByUID|查询政策数据状态|String[]|Business类的id，json序列化字符串|String|成功则返回Business的各个字段；否则返回Error的信息
updateBusinessActivity|更新政策数据状态|String[]|Business类的json序列化字符串|String|成功则返回OK；否则返回Error的信息
deleteBusinessActivity|核销删除政策信息|String[]|Business类的json序列化字符串|String|成功则返回OK；否则返回Error的信息
getBusinessActivityHistory|追溯政策发放过程的状态改变|String[]|Business类的id，json序列化字符串|String|成功则返回历史状态结构；否则返回Error的信息

---
bsn-java-sdk中的政策的Business实体类：
```java
public class Business {

//| uid         | String | 人才id       |
//| bid | String | 商业活动id |
//| menu    | String | 商业活动目录 |
//| activity    | String | 商业活动名称 |
//| funds       | String | 金额         |
//| create_time | String | 创建时间     |
//| extra | String | 扩展字段 |


    public Business() {

    }

    public Business(String uid, String bid, String menu, String activity, String funds, String create_time, String extra) {
        this.uid = uid;
        this.bid = bid;
        this.menu = menu;
        this.activity = activity;
        this.funds = funds;
        this.create_time = create_time;
        this.extra = extra;
    }

    @JSONField(name = "uid")
    String uid;

    @JSONField(name = "bid")
    String bid;

    @JSONField(name = "menu")
    String menu;

    @JSONField(name = "activity")
    String activity;

    @JSONField(name = "funds")
    String funds;

    @JSONField(name = "create_time")
    String create_time;

    @JSONField(name = "extra")
    String extra;
}
```


---
## 4-4 历史状态结构返回信息参数说明：

当查询某一ID对象的历史状态，对该对象进行历史追溯时，在返回信息的ccData字段中，会返回该对象的历史状态结构。该结构各参数如下：
字段|类型|含义|
|:---:|:---| :--- |
txId|String|区块链上该事务的ID|
dataInfo|String|在这次事务发生后，该ID对应对象的各个字段的数据值|
txTime|String|事务发生的时间|
isDelete|String|该事务是否被删除过|
---
示例：
```json
{
"header":{
        "code":0,
        "msg":"success"
    },
"mac":"MEQCIF4iYNJRRdRR+NxOnfXKVV2UcKx8k3u/iIvX/daoAoAXAiBHdaNZOqKR2UTPOGhHpJTMs7FH3MXfAzAu0cwieVMftQ==",
"body":{
    "blockInfo":{
    "txId":"6e275137a31cb9f6f34c7ca94ff21d7177c2f3c87dc8c04e5dfde771ae90e57f",
    "blockHash":"",
    "status":0
    },
    "ccRes":{
        "ccCode":200,
        "ccData":"[{\"txId\":\"45c4d1a4ed369c392efaf5f28ea6c43e21e7b9c587587eb4aa7076979cac4064\",\"dataInfo\":\"{\\\"type\\\":\\\"test\\\",\\\"ticketUid\\\":\\\"0001\\\",\\\"ticketName\\\":\\\"testticket\\\",\\\"description\\\":\\\"only for test\\\",\\\"status\\\":\\\"ok\\\"}\",\"txTime\":\"2020-08-24 09:03:06\",\"isDelete\":false},{\"txId\":\"6fac4400101f6476bb35924d383ed9680384063ccbac7a5b72555fa2c38e9880\",\"dataInfo\":\"{\\\"type\\\":\\\"test\\\",\\\"ticketUid\\\":\\\"0001\\\",\\\"ticketName\\\":\\\"testticket\\\",\\\"description\\\":\\\"only for test\\\",\\\"status\\\":\\\"ok\\\"}\",\"txTime\":\"2020-08-24 09:18:10\",\"isDelete\":false},{\"txId\":\"ac5a1f6a3c1d0ca9d227dc05a2b98a28d2f17c1efa538c678f7771fd8d48c904\",\"dataInfo\":\"{\\\"type\\\":\\\"test\\\",\\\"ticketUid\\\":\\\"0001\\\",\\\"ticketName\\\":\\\"testticket\\\",\\\"description\\\":\\\"only for test\\\",\\\"status\\\":\\\"ok\\\"}\",\"txTime\":\"2020-08-24 09:31:51\",\"isDelete\":false}]"
        }
    }
}
```
## 4-5 富查询智能合约：
由于区块链底层数据库的限制，通过智能合约调用查询智能通过某个数据的主键（key）进行查询；当需要通过数据的其他字段进行查询的时候，需要通过另一个接口提供的富查询功能进行查找。

bsn网络中底层的数据库使用couchdb，查找的语法与mongodb类似。
以下展示如何通过自定义的查询语句通过其他字段查询某个数据。

couchdb通过定义selector，可以获取符合该对象的数据。

例如：
```json
"{\"selector\":{\"uid\":\"profile_test01\"，\"dataType\":\"application\"}}"
```
意思是查询所有类型为application中，uid为profile_test01，的所有数据字段。

构造好以后，通过调用名称为“GetQueryResult”的智能合约将构造的selector查询语句传入当智能合约的调用参数，即可进行查询。示例如下：

```java
public void reqGetApplicationInfoByUID(){
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
        String k = "pid";
        String v = "appid01";

        //构造查询selector语句
        String []args = {String.format("{\"selector\":{\"%s\":\"%s\"}}",k,v)};
        //将构造好的查询语句传入智能合约调用参数中
        reqkey.setArgs(args);
        //调用GetQueryResult方法
       reqkey.setFuncName("GetQueryResult");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }
```

方法名称|功能|参数类型|参数含义|返回值类型|返回值|备注
| :---: | :---: | :---: | :---: | :---: | :---| :---: |
GetQueryResult|通过非主键字段进行富查询|String[]|构造的富查询语句|String|所有符合查询条件的数据|无
