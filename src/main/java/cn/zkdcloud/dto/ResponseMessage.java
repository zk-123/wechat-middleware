package cn.zkdcloud.dto;

/**
 * 回复消息
 */
public abstract class ResponseMessage extends Message{

    public ResponseMessage(){
        this.createTime = System.currentTimeMillis();
    }

    public ResponseMessage(String toUserName, String fromUserName){
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = System.currentTimeMillis();
    }
}
