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
# 被动接受消息/回复消息

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
## 自定义菜单

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
        
MenuComponent.createMenu(menu);
````
该代码快创建了一个有两个一级菜单，一级菜单下分别有三个和两个按钮的菜单（一级菜单最多3个，二级菜单最多5个）

### 获取
````java
MenuComponent.getMenu();
````
### 删除
````java
MenuComponent.deleteMenu();
````
### 初始化
如果想服务启动的时候，就能自动创建自定义菜单。
+ 1.创建一个新类继承`cn.zkdcloud.core.MenuComponent`并重写其`init()`方法。
+ 2.创建一个新类继承`cn.zkdcloud.core.WeChatListener`并重写其`contextInitialized`方法（该方法中，支持添加自定义组件），添加该组件。
+ 3.好像有点麻烦了。

> [代码示例](https://github.com/zk-123/weChatDemo/tree/master/menu)

## 模板消息
模板消息初始化时，会查询所有的模板消息，并缓存到本地。
### 查询
````java
 //根据模板名或shotId获取模板，先会从本地查找，若没有，则重新刷新本地模板并查找
 TemplateComponent.getTemplateByName(name);
 TemplateComponent.getTemplateId(shotId)
 
 //查询模板列表（参数1：是否刷新本地缓存）
 TemplateComponent.getListTemplateMessage(true);
 TemplateComponent.getListTemplateMessage();
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
TemplateComponent.sendTemplateMessage(templateMessage);
````
### 删除
````java
//根据name或id删除
TemplateComponent.deleteTemplateMessageByName("话费提醒2");
TemplateComponent.deleteTemplateMessageById("template id");
````
> [代码示例](https://github.com/zk-123/weChatDemo/tree/master/template)
## 更新中
更新中......

## 最后
> 当你在穿山越岭的另一边，我在孤独的路上没有尽头。

> 一辈子有多少的来不及，发现已经失去最重要的东西。
