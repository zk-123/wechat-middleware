package cn.zkdcloud.component.message.acceptMessage.eventMessage;

import cn.zkdcloud.component.message.acceptMessage.AbstractAcceptEventMessage;

public abstract class AbstractMenuEventMessage extends AbstractAcceptEventMessage {
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
