package cn.zkdcloud.dto.acceptMessage.eventMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;

/**
 * 点击菜单拉取事件
 */
public class ClickEventMessage extends AcceptEventMessage {
    /**
     * KEY值与自定义菜单接口中KEY值对应
     */
    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
