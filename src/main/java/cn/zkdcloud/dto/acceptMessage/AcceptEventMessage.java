package cn.zkdcloud.dto.acceptMessage;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.entity.Event;

/**
 * 事件消息
 */
public abstract class AcceptEventMessage extends AcceptMessage {
    /*
    事件类型
     */
    protected Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
