package cn.zkdcloud.process;

import cn.zkdcloud.annotation.MessageProcess;
import cn.zkdcloud.component.message.AbstractResponseMessage;
import cn.zkdcloud.component.message.acceptMessage.Event;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.LocationEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.ScanEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.SubscribeEventMessage;
import cn.zkdcloud.component.message.acceptMessage.normalMessage.*;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.ClickEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.MenuScanEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.PhotoEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.ViewEventMessage;
import cn.zkdcloud.component.message.responseMessage.*;
import cn.zkdcloud.util.StreamUtil;

@MessageProcess
public class TestProcess {

    /**
     * 接收文本类型，回复图文消息(order = 1)
     *
     * @param message textMessage
     * @return responseMessage
     */
    public AbstractResponseMessage respNewsMessage(AcceptTextMessage message) {
        ResponseNewsMessage news = new ResponseNewsMessage();
        news.addArticle("title1", "title1",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3457316229,962195637&fm=200&gp=0.jpg", "http://www.baidu.com")
                .addArticle("title1", "title1",
                        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3457316229,962195637&fm=200&gp=0.jpg", "http://www.baidu.com")
                .addArticle("title1", "title1",
                        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3457316229,962195637&fm=200&gp=0.jpg", "http://www.baidu.com");
        return news;
    }

    /**
     * 文本类型处理(order = 2)
     *
     * @param message textMessage
     * @return responseMessage
     */
    public AbstractResponseMessage textMessage(AcceptTextMessage message) {
        ResponseMusicMessage music = new ResponseMusicMessage();
        music.setMusic(ResponseMusicMessage.Music.getMusic("春风十里", "test music",
                "http://music.163.com/outchain/player?type=2&id=33729036", "", null));
        return music;
    }

    /**
     * 图片类型处理
     *
     * @param message message
     * @return text
     */
    public AbstractResponseMessage imageMessage(AcceptImageMessage message) {
        ResponseImageMessage image = new ResponseImageMessage();
        image.setImage(ResponseImageMessage.Image.getImage(message.getMediaId()));
        return image;
    }

    /**
     * 扫描事件处理
     *
     * @param message message
     * @return text
     */
    public AbstractResponseMessage scanEventMessage(ScanEventMessage message) {
        return new ResponseTextMessage("你触发了扫描事件");
    }

    /**
     * 地理位置事件消息
     *
     * @param message message
     * @return text
     */
    public AbstractResponseMessage locationEventMessage(LocationEventMessage message) {
        return new ResponseTextMessage("收到位置");
    }

    /**
     * 主动上传地理位置消息
     *
     * @param message location
     * @return ret
     */
    public AbstractResponseMessage locationMessage(AcceptLocationMessage message) {
        return new ResponseTextMessage(StreamUtil.ObjToXml(message));
    }

    /**
     * 语音识别消息
     *
     * @param message voice
     * @return ret
     */
    public AbstractResponseMessage voiceMessage(AcceptVoiceMessage message) {
        ResponseVoiceMessage voice = new ResponseVoiceMessage();
        voice.setVoice(ResponseVoiceMessage.Voice.getVoice(message.getMediaId()));
        return voice;
    }

    /**
     * 短视频消息
     *
     * @param message video
     * @return ret
     */
    public AbstractResponseMessage shotVideo(AcceptVideoMessage message) {
        ResponseVideoMessage video = new ResponseVideoMessage();
        video.setVideo(ResponseVideoMessage.Video.getVideo(message.getMediaId(),
                "test video", "测试视频"));
        return video;
    }

    /**
     * 关注/取消关注事件
     *
     * @param message sub/un-sub message
     * @return ret
     */
    public AbstractResponseMessage subScribeEventMessage(SubscribeEventMessage message) {
        if (message.getEvent() == Event.SUBSCRIBE) {
            return new ResponseTextMessage("欢迎订阅");
        } else {
            System.out.println("该用户已经取消订阅了-.-");
            return null;
        }
    }

    /**
     * 自定义菜单事件点击
     *
     * @param message clickMessage
     * @return ret
     */
    public AbstractResponseMessage clickEventMessage(ClickEventMessage message) {
        return new ResponseTextMessage("event key is : " + message.getEventKey());
    }

    /**
     * 自定义菜单点击view事件消息
     *
     * @param message message
     * @return ret
     */
    public AbstractResponseMessage viewEventMessage(ViewEventMessage message) {
        return new ResponseTextMessage("event view key is :" + message.getViewKey());
    }

    /**
     * 二维码扫描结果
     *
     * @param menuScanEventMessage message
     * @return ret
     */
    public AbstractResponseMessage scanEventMessage(MenuScanEventMessage menuScanEventMessage) {
        return new ResponseTextMessage("识别结果是:" + menuScanEventMessage.getScanCodeInfo().getScanResult());
    }

    /**
     * 图片事件
     *
     * @param message photoMessage
     * @return ret
     */
    public AbstractResponseMessage photoEventMessage(PhotoEventMessage message) {
        return new ResponseTextMessage("图片事件消息...." + message.getSendPicsInfo().getPicList().get(0).getPicMd5Sum());
    }

}
