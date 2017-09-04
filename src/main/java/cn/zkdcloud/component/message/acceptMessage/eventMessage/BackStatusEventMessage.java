package cn.zkdcloud.component.message.acceptMessage.eventMessage;

import cn.zkdcloud.component.message.acceptMessage.AbstractAcceptEventMessage;

/**
 * 微信返回事件消息
 *
 * @author zk
 * @version 2017/8/30
 */
public class BackStatusEventMessage extends AbstractAcceptEventMessage {

    /**
     * 1.如果是模板消息事件，返回success为成功接收，返回failed: system failed 或其他为发送失败
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
