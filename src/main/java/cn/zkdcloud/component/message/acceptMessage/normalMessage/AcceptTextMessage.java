package cn.zkdcloud.component.message.acceptMessage.normalMessage;

import cn.zkdcloud.component.message.acceptMessage.AbstractAcceptNormalMessage;

/**
 * 文本消息
 * Created by zk on 2016/10/30.
 */
public class AcceptTextMessage extends AbstractAcceptNormalMessage {

    /**
     * 文本消息内容
     */
    private String content;

    public AcceptTextMessage() {
        this.content = "success";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
