# 微信中间件

[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)]()
[![Jenkins coverage](https://img.shields.io/jenkins/c/https/jenkins.qa.ubuntu.com/view/Utopic/view/All/job/address-book-service-utopic-i386-ci.svg?style=plastic)]()
[![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg?style=plastic)]()

# 使用
## maven 引入
````xml
<dependency>
    <groupId>cn.zkdcloud</groupId>
    <artifactId>weChat</artifactId>
    <version>0.4-RELEASE</version>
</dependency>
````
# 被动接受消息/回复消息（MessageComponent）

## 消息bean
```` tree
message
├── Message.java  --abstract class，是所有接受消息和回复消息父类
├── AbstractAcceptMessage.java --abstract class,所有接受消息的父类
├── AbstractResponseMessage.java --abstract class,所有回复消息的父类
├── MsgType.java  --enum,message类型
├── AbstractacceptMessage 
│   ├── AbstractAcceptEventMessage.java --abstract class,接受事件类型消息的父类
│   ├── AbstractAcceptNormalMessage.java --abstract class,接受其他类型消息的父类
│   ├── Event.java --enum，所有事件类型
│   ├── eventMessage
│   │   ├── LocationEventMessage.java --上传地理位置事件，当用户同意上传地位位置后，被动的上传
│   │   ├── AbstractMenuEventMessage.java --abstract class,所有菜单事件的父类
│   │   ├── ScanEventMessage.java -- 二维码扫描事件
│   │   ├── SubscribeEventMessage.java --关注/取消关注事件
|   |   └── menuEventMessage --菜单类型触发的事件
│   │       ├── ClickEventMessage.java --点击事件
│   │       ├── MenuScanEventMessage.java --扫描事件
│   │       ├── PhotoEventMessage.java --照片事件
│   │       └── ViewEventMessage.java --链接事件
│   └── normalMessage
│       ├── AcceptImageMessage.java --图片消息 
│       ├── AcceptLinkMessage.java --链接消息
│       ├── AcceptLocationMessage.java --地理位置消息                         
│       ├── AcceptTextMessage.java --文本消息 
│       ├── AcceptVideoMessage.java --视频消息
│       └── AcceptVoiceMessage.java --语音消息
└── responseMessage
    ├── ResponseImageMessage.java --回复图片消息
    ├── ResponseMusicMessage.java --回复音乐消息
    ├── ResponseNewsMessage.java --回复新闻消息
    ├── ResponseTextMessage.java --回复文本消息
    ├── ResponseVideoMessage.java --回复视频消息
    └── ResponseVoiceMessage.java --回复语音消息
 
````
### 新建一个控制类 
如：`TestProcess.java`
##### 这样定义

```` java
@MessageProcess
public class TestProcess{
    /**
     * 文本类型处理
     * @param message textMessage
     * @return  responseMessage
     */
    public ResponseMessage textMessage(AcceptTextMessage message){
        ResponseTextMessage text = new ResponseTextMessage();
        text.setContent("我接收到你的文本信息了");
        return text;
    }
}
````
+ 加` @MessageProcess`声明，标识该类为一个微信处理器
+ 该类下的所有方法都是消息处理方法，参数必须只有一个标识期望接受一个什么类型的消息（请仔细阅读上面的消息bean）,如果多重复的消息参数，只有第一个声明的方法有效
+ 处理方法返回需要返回的消息类型，方法体重只处理消息内容，其他细节，比如：创建时间，发送人，发送方等等均不需要处理.....
> [代码示例](https://github.com/zk-123/weChatDemo/tree/master/message)
## 自定义菜单（MenuComponent）

### 创建/修改
````java
Menu menu = new Menu();
menu.addButton(NormalButton.creaetOne("菜单一")
        .addSubButton(ViewButton.createOne("百度链接", "http://www.baidu.com"))
        .addSubButton(NormalButton.createOne(MenuType.PIC_PHOTO_OR_ALBUM, "ptoto", "拍照"))
        .addSubButton(NormalButton.createOne(MenuType.CLICK, "click1", "click")))
    .addButton(NormalButton.creaetOne("菜单二")
        .addSubButton(NormalButton.createOne(MenuType.LOCATION_SELECT, "location", "发送位置"))
        .addSubButton(NormalButton.createOne(MenuType.SCANCODE_WAITMSG, "scancode", "扫描带提示")));
        
menuComponent.createMenu(menu);
````
该代码快创建了一个有两个一级菜单，一级菜单下分别有三个和两个按钮的菜单（一级菜单最多3个，二级菜单最多5个）

### 获取
````java
menuComponent.getMenu();
````
### 删除
````java
menuComponent.deleteMenu();
````
### 初始化
如果想服务启动的时候，就能自动创建自定义菜单。
+ 1.创建一个新类继承`cn.zkdcloud.core.MenuComponent`并重写其`init()`方法。
+ 2.创建一个新类继承`cn.zkdcloud.core.WeChatListener`并重写其`contextInitialized`方法（该方法中，支持添加自定义组件），添加该组件。
+ 3.好像有点麻烦了。

> [代码示例](https://github.com/zk-123/weChatDemo/tree/master/menu)

## 模板消息（TemplateComponent）
模板消息初始化时，会查询所有的模板消息，并缓存到本地。
### 查询
````java
 //根据模板名或shotId获取模板，先会从本地查找，若没有，则重新刷新本地模板并查找
 TemplateComponent.getTemplateByName(name);
 TemplateComponent.getTemplateId(shotId)
 
 //查询模板列表（参数1：是否刷新本地缓存）
 templateComponent.getListTemplateMessage(true);
 templateComponent.getListTemplateMessage();
````
### 发送
````java
//新建模板消息
TemplateMessage templateMessage = new TemplateMessage("openId", "templateId", "http://www.baidu.com");
//填充data域
templateMessage.addData("first", "本月话费提醒", "#526dea");
templateMessage.addData("money", "1个亿");
templateMessage.addData("paly", "10个亿");
templateMessage.addData("donate", "100个亿");
templateMessage.addData("remark", "您的余额不足100亿，请续缴话费！", "#cccccc");
//send
templateComponent.sendTemplateMessage(templateMessage);
````
### 删除
````java
//根据name或id删除
TemplateComponent.deleteTemplateMessageByName("话费提醒2");
TemplateComponent.deleteTemplateMessageById("template id");
````
> [代码示例](https://github.com/zk-123/weChatDemo/tree/master/template)

## 网页授权管理（Oauth2AuthorizeComponent）
### 初始化或在init中重置初始化这些
````java
public void setSnsapiUrl(String redirect_uri, String state) 
````
### 获取认证链接
````java
/**
 * scope为snsapi_base 授权
 */
Oauth2AuthorizeComponent.SNSAPI_BASE_URL
 /**
  * scope为snsapi_userinfo 授权
  */
Oauth2AuthorizeComponent.SNSAPI_USERINFO_URL
````
### 拉取用户信息（需要code或token）
````java
/**
 * 拉取用户信息(需scope为 snsapi_userinfo)
 *
 * @param access_token 网页授权接口调用凭证
 * @param openid       用户的唯一标识
 * @return 用户信息jsonStr
 * {
 * "openid":" OPENID",
 * " nickname": NICKNAME,
 * "sex":"1",
 * "province":"PROVINCE"
 * "city":"CITY",
 * "country":"COUNTRY",
 * "headimgurl":    "http://wx.qlogo.cn/Q1dZuTOgvLLrhJbERQQ,
 * "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
 * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
 * }
 */
public String getUserInfo(String access_token, String openid, String lang) 
public String getUserInfo(String access_token, String openid)
/**
 * 根据code 获取用户信息
 *
 * @param code code 5分钟未被使用自动过期
 * @return userInfo
 */
public String getUserInfo(String code)
````
### 用户认证token
````java
    /**
     * 检查该access是否有效
     *
     * @param access_token access_token
     * @param openid       openid
     * @return is or not effective
     */
    public boolean isEffective(String access_token, String openid)
    
    /**
     * 刷新拉取用户access token(GET/POST)
     *
     * @param refresh_token refresh token
     * @return jsonStr
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     */
    public String refreshUserAccessToken(String refresh_token)
    
        /**
     * 根据code获取用户授权的access_token
     *
     * @param code code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
     * @return jsonStr
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     */
    public String getUserAccessToken(String code)
````

## 素材管理（MaterialComponent）

### 上传素材
````java
    /**
     * 上传临时素材(image,video,thumb,voice保存3天)
     *
     * @param file file material
     * @param type 素材类型
     * @return media_id
     */
    public String uploadTempMaterial(File file, MaterialType type)
    
    /**
     * 上传永久图文素材
     *
     * @param articles articles
     * @return media_id
     */
    public String uploadPersistentMaterial(List<MaterialArticle> articles)
    
    /**
     * 上传永久图文素材
     *
     * @param articles articles 图文json
     * @return media_id
     */
    public String uploadPersistentMaterial(String articles)
    
    /**
     * 上传图片获取URL(注意返回的是url不是media_id,所以其不占素材库5000个限制)
     *
     * @param file img(jpg/png)
     * @return image's url
     */
    public String uploadPersistentImage(File file)
    
    /**
     * 上传其他永久素材
     *
     * @param file file material
     * @return media_id and url
     * {
     * "media_id":MEDIA_ID,
     * "url":URL
     * }
     */
    public String uploadPersistentMaterial(File file, MaterialType type, String title, String introduction) 
```` 
### 下载素材
````java
    /**
     * 下载临时素材视频url
     *
     * @param media_id media_id
     * @return video url
     */
    public String downloadVideo(String media_id)
    
    /**
     * 下载临时素材(不包括视频)
     *
     * @param media_id media_id
     * @param filePath filePath(带文件名+文件格式)
     * @return file
     */
    public File downloadTempMaterial(String media_id, String filePath) 
    
    /**
     * 下载临时素材(不包括视频)
     *
     * @param media_id    media_id
     * @param fileDirPath 下载文件夹路i纪念馆
     * @param suffix      后缀名
     * @return
     */
    public File downloadTempMaterial(String media_id, String fileDirPath, String suffix)
    
    /**
     * 下载永久图文素材
     *
     * @param media_id media_id
     * @return List<news>
     */
    public List<MaterialArticle> downloadPersistentNews(String media_id)
    
    /**
     * 获取永久视频素材的url
     *
     * @param media_id media_id
     * @return video download url
     */
    public String downloadPersistentVideo(String media_id) 
    
    /**
     * 下载永久素材(不包括视频，图文)
     *
     * @param media_id media_id
     * @param filePath filePath(带文件名+文件格式)
     * @return file
     */
    public File downloadPersistentMaterial(String media_id, String filePath)
    
    /**
     * 下载永久素材(不包括视频，图文)
     *
     * @param media_id    media_id
     * @param fileDirPath 下载文件夹路径
     * @param suffix      后缀名
     * @return
     */
    public File downloadPersistentMaterial(String media_id, String fileDirPath, String suffix)
    
    /**
     * 统计永久素材总数
     *
     * @return countJson
     * {
     * "voice_count":COUNT, 语音
     * "video_count":COUNT, 视频
     * "image_count":COUNT, 照片
     * "news_count":COUNT   图文
     * }
     */
    public String countPersistentMaterial()
    
    /**
     * 获取图文素材列表
     *
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return <media_id,materialArticle>
     */
    public Map<String, List<MaterialArticle>> getListNewsPersistentMaterial(Integer offset, Integer count)
    
     /**
     * 获取其它素材列表
     *
     * @param type   type
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return list<material>
     */
    public List<Material> getListPersistentMaterial(MaterialType type, Integer offset, Integer count)
    
    /**
     * 更新某个图文永久素材
     *
     * @param media_id meida_id
     * @param index    要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @param article  新图文素材内容
     * @return is or not success
     */
    public boolean updateNewsPersistentMaterial(String media_id, Integer index, MaterialArticle article)
    
    /**
     * 删除指定永久素材
     *
     * @param media_id media_id
     * @return is or not success
     */
    public boolean deletePersistentMaterial(String media_id)
````
## 粉丝标签管理（UserTagComponent）
### 添加
````java
    /**
     * 添加标签
     *
     * @param tagName tagName 标签名
     * @return ret;
     * {
     * "tag":{
     * "id":134,//标签id
     * "name":"广东"
     * }
     * }
     */
    public String createTag(String tagName) throws WeChatException
    
    /**
     * 批量为用户打标签
     *
     * @param openid_list 用户open_id列表
     * @param tagid       标签id
     * @return is or not success
     */
    public boolean batchTags(List<String> openid_list, Integer tagid)
````
### 获取
````java
    /**
     * 获取某用户身上的标签
     *
     * @param open_id user's openid
     * @return List<tagid>
     */
    public List<Integer> getTagsByUser(String open_id) 
    
    /**
     * 根据标签id获取用户列表
     *
     * @param tagid tagid
     * @return list<useopenid>
     */
    public List<String> getUserByTagId(Integer tagid)
    
    /**
     * 根据标签id获取用户列表
     *
     * @param tagid       tagid
     * @param next_openid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return list<useropenid>
     */
    public List<String> getUserByTagId(Integer tagid, String next_openid) 
    
    /**
     * 获取标签列表
     *
     * @return jsonArray
     * [
     * {
     * "id":1,
     * "name":"每天一罐可乐星人",
     * "count":0 //此标签下粉丝数
     * },
     * {
     * "id":2,
     * "name":"星标组",
     * "count":0
     * },
     * {
     * "id":127,
     * "name":"广东",
     * "count":5
     * }
     * ]
     */
    public JSONArray getTags() 
````
### 删除和更新
````java
    /**
     * 修改标签
     *
     * @param id      tag's id
     * @param tagName 标签名
     * @return is or not success
     */
    public boolean updateTag(Integer id, String tagName)
    
    /**
     * 删除标签
     * <p>
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。
     * 此时，开发者可以对该标签下的openid列表，先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
     *
     * @param id 标签id
     * @return is or not delete
     */
    public boolean deleteTag(Integer id)
    
    /**
     * 批量为用户取消标签
     *
     * @param openid_list 用户open_id 列表
     * @param tagid       标签id
     * @return is or not success
     */
    public boolean batchUnTags(List<String> openid_list, Integer tagid)
````
## 用户管理(UserManagerComponent)
### 获取
````java
    /**
     * 根据用户openid获取用户信息(language default:zh-cn)
     *
     * @param openid openid
     * @return userInfoBean
     */
    public UserInfo getUserInfoByOpenId(String openid)
    
    /**
     * 根据用户openid 获取用户信息
     *
     * @param openid openid
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return userInfoBean
     */
    public UserInfo getUserInfoByOpenId(String openid, String lang)
    
    /**
     * 批量获取用户信息 ×
     *
     * @param openids openids
     * @return userInfos
     */
    public List<UserInfo> getBatchUserInfo(List<String> openids)
    
    /**
     * 批量获取用户信息
     *
     * @param openids openids
     * @param lang    lang default 'zh_cn'
     * @return userInfos
     */
    public List<UserInfo> getBatchUserInfo(List<String> openids, String lang)
    
    /**
     * 批量获取用户openid
     *
     * @return List<openId>
     */
    public List<String> getUserOpenids()
    
    /**
     * 批量获取用户openid
     *
     * @param next_openid 开始的第一个openId
     * @return List<openId>
     */
    public List<String> getUserOpenids(String next_openid) 
    
    /**
     * 获取公账号黑名单列表（一次最多10000）
     *
     * @return List<openId>
     */
    public List<String> getBlackList()
    
    /**
     * 获取公众号黑名单列表
     *
     * @param begin_openid 开始的openid
     * @return List<openid>
     */
    public List<String> getBlackList(String begin_openid)
````
### 其它
````java
    /**
     * 设置用户备注名
     *
     * @param openid     open_id
     * @param remarkName remarkName
     * @return is or not success
     */
    public boolean remarkUser(String openid, String remarkName)
    
    /**
     * 批量拉黑用户
     *
     * @param openIds List<openId>
     * @return is or not success
     */
    public boolean batchBlackUsers(List<String> openIds)
    
    /**
     * 批量取消拉黑用户
     *
     * @param openIds List<OpenId></>
     * @return is or not success
     */
    public boolean batchUnblackUsers(List<String> openIds) 
````
## 二维码(QrcodeComponent)
### 生成二维码
````java
    /**
     * 创建临时/永久整型参数值
     *
     * @param scene_id scene_id
     * @param type     type
     * @return 二维码url地址(用户扫描后会出发扫描事件)
     */
    public String createIntegerQr(Integer scene_id, QrType type)
    
    /**
     * 创建临时/永久字符串参数值
     *
     * @param scene_str scene_str 场景字符串
     * @param type     type
     * @return 二维码url地址(用户扫描后会出发扫描事件)
     */
    public String createStrQr(String scene_str, QrType type)
````

## 最后
> 当你在穿山越岭的另一边，我在孤独的路上没有尽头。

> 一辈子有多少的来不及，发现已经失去最重要的东西。
