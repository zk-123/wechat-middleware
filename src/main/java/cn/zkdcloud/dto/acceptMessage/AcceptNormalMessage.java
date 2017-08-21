package cn.zkdcloud.dto.acceptMessage;

/**
 * 一般消息
 */
public abstract class AcceptNormalMessage {
    /*
    消息id
     */
    protected String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
