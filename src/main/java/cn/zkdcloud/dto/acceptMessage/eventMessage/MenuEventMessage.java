package cn.zkdcloud.dto.acceptMessage.eventMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;

public abstract class MenuEventMessage extends AcceptEventMessage {
    /**
     * 事件key
     */
    protected String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
