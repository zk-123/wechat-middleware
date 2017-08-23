package cn.zkdcloud.process;

import cn.zkdcloud.annotation.MessageProcess;
import cn.zkdcloud.core.MessageAdapter;
import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.dto.acceptMessage.Event;
import cn.zkdcloud.dto.acceptMessage.eventMessage.*;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.ClickEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.MenuScanEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.PhotoEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.ViewEventMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.*;
import cn.zkdcloud.dto.responseMessage.ResponseTextMessage;
import cn.zkdcloud.util.StreamUtil;

@MessageProcess
public class TestMessage extends MessageAdapter{
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

    /**
     * 图片类型处理
     * @param message message
     * @return text
     */
    public ResponseMessage imageMessage(AcceptImageMessage message){
        return new ResponseTextMessage("收到 你的图片消息了");
    }

    /**
     * 扫描事件处理
     * @param message message
     * @return text
     */
    public ResponseMessage scanEventMessage(ScanEventMessage message){
        return new ResponseTextMessage("你触发了扫描事件");
    }

    /**
     * 地理位置事件消息
     * @param message message
     * @return text
     */
    public ResponseMessage locationEventMessage(LocationEventMessage message){
        return new ResponseTextMessage("收到位置");
    }

    /**
     * 主动上传地理位置消息
     * @param message location
     * @return ret
     */
    public ResponseMessage locationMessage(AcceptLocationMessage message){
        return new ResponseTextMessage(StreamUtil.ObjToXml(message));
    }

    /**
     * 语音识别消息
     * @param message voice
     * @return ret
     */
    public ResponseMessage voiceMessage(AcceptVoiceMessage message){
        return new ResponseTextMessage("这条语音识别为：" + message.getRecognition());
    }

    /**
     * 短视频消息
     * @param message video
     * @return ret
     */
    public ResponseMessage shotVideo(AcceptVideoMessage message){
        return new ResponseTextMessage("video is" + StreamUtil.ObjToXml(message));
    }

    /**
     * 关注/取消关注事件
     * @param message sub/unsub message
     * @return ret
     */
    public ResponseMessage subScribeEventMessage(SubscribeEventMessage message){
        if(message.getEvent() == Event.SUBSCRIBE){
            return new ResponseTextMessage("欢迎订阅");
        } else {
            System.out.println("该用户已经取消订阅了-.-");
            return null;
        }
    }

    /**
     * 自定义菜单事件点击
     * @param message clickMessage
     * @return ret
     */
    public ResponseMessage clickEventMessage(ClickEventMessage message){
        return new ResponseTextMessage("event key is : " + message.getEventKey());
    }

    /**
     * 自定义菜单点击view事件消息
     * @param message message
     * @return ret
     */
    public ResponseMessage viewEventMessage(ViewEventMessage message){
        return new ResponseTextMessage("event view key is :" + message.getViewKey());
    }

    /**
     * 二维码扫描结果
     * @param menuScanEventMessage message
     * @return ret
     */
    public ResponseMessage scanEventMessage(MenuScanEventMessage menuScanEventMessage){
        return new ResponseTextMessage("识别结果是:" + menuScanEventMessage.getScanCodeInfo().getScanResult());
    }

    /**
     * 图片事件
     * @param message photoMessage
     * @return ret
     */
    public ResponseMessage photoEventMessage(PhotoEventMessage message){
        return new ResponseTextMessage("图片事件消息...." + message.getSendPicsInfo().getPicList().get(0).getPicMd5Sum());
    }

}
