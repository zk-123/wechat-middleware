package cn.zkdcloud.dto.acceptMessage;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.*;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.ClickEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.MenuScanEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.PhotoEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage.ViewEventMessage;
import com.alibaba.fastjson.JSONObject;

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
     * @param json json
     * @return acceptEventMessage
     * @throws Exception exception
     */
    public static AcceptEventMessage eventResolver(JSONObject json) throws Exception {
        AcceptEventMessage ret;
        json.put("Event",json.getString("Event").toUpperCase());

        switch (Event.valueOf(json.getString("Event").toUpperCase())) {
            case SCAN:
                ret = json.toJavaObject(ScanEventMessage.class);
                break;
            case VIEW:
                ret = json.toJavaObject(ViewEventMessage.class);
                break;
            case CLICK:
                ret = json.toJavaObject(ClickEventMessage.class);
                break;
            case LOCATION:
                ret = json.toJavaObject(LocationEventMessage.class);
                break;
            case SUBSCRIBE:
                ret = json.toJavaObject(SubscribeEventMessage.class);
                break;
            case UNSUBSCRIBE:
                ret = json.toJavaObject(SubscribeEventMessage.class);
                break;
            case SCANCODE_PUSH:
                ret = json.toJavaObject(MenuScanEventMessage.class);
                break;
            case SCANCODE_WAITMSG:
                ret = json.toJavaObject(MenuScanEventMessage.class);
                break;
            case PIC_SYSPHOTO:
                ret = json.toJavaObject(PhotoEventMessage.class);
                break;
            case PIC_PHOTO_OR_ALBUM:
                ret = json.toJavaObject(PhotoEventMessage.class);
                break;
            default:
                ret = null;
        }
        return ret;
    }

}
