package cn.zkdcloud.dto.acceptMessage.eventMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;

/**
 * 用户扫码事件(这个不知道什么时候触发)
 */
public class ScanEventMessage extends AcceptEventMessage {
    /**
     * 事件key值,是一个32位无符号整数，即创建二维码时的二维码scene_id
     */
    private String eventKey;

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
