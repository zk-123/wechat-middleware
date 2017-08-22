package cn.zkdcloud.dto.acceptMessage;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.*;

import java.util.Map;

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

    /**
     * 将data根据event类型转成不同的事件
     *
     * @param data requestData
     * @return ret
     */
    public static AcceptEventMessage eventResolver(Map<String, String> data) throws Exception {
        AcceptEventMessage ret;

        switch (Event.valueOf(data.get("Event").toUpperCase())) {
            case SCAN:
                ret = fillMessage(data, ScanEventMessage.class);
                break;
            case VIEW:
                ret = fillMessage(data, ViewEventMessage.class);
                break;
            case CLICK:
                ret = fillMessage(data, ClickEventMessage.class);
                break;
            case LOCATION:
                ret = fillMessage(data, LocationEventMessage.class);
                break;
            case SUBSCRIBE:
                ret = fillMessage(data, SubscribeEventMessage.class);
                break;
            case UBSUBSCRIBE:
                ret = fillMessage(data,SubscribeEventMessage.class);
                break;
            default:
                ret = null;
        }
        return ret;
    }

}
