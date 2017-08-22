package cn.zkdcloud.dto;

import cn.zkdcloud.dto.responseMessage.ResponseTextMessage;
import cn.zkdcloud.entity.MsgType;

/**
 * 回复消息
 */
public abstract class ResponseMessage extends Message{

    public ResponseMessage(){

    }

    public ResponseMessage(String toUserName, String fromUserName){
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = System.currentTimeMillis();
    }
}
