package cn.zkdcloud.process;

import cn.zkdcloud.annotation.MessageProcess;
import cn.zkdcloud.core.MessageAdapter;
import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.LocationEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.ScanEventMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.AcceptImageMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.AcceptLocationMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.AcceptTextMessage;
import cn.zkdcloud.dto.responseMessage.ResponseImageMessage;
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
     * @param message
     * @return
     */
    public ResponseMessage locationMessage(AcceptLocationMessage message){
        return new ResponseTextMessage(StreamUtil.ObjToXml(message));
    }
}
