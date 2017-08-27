package cn.zkdcloud.component.message;

/**
 * 回复消息
 */
public abstract class AbstractResponseMessage extends Message {

    public AbstractResponseMessage() {
        this.createTime = System.currentTimeMillis();
    }

    public AbstractResponseMessage(String toUserName, String fromUserName) {
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = System.currentTimeMillis();
    }
}
