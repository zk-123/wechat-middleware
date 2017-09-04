package cn.zkdcloud.component.message.acceptMessage;

import cn.zkdcloud.component.message.acceptMessage.eventMessage.LocationEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.ScanEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.SubscribeEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.ClickEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.MenuScanEventMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.PhotoEventMessage;
import cn.zkdcloud.component.message.AbstractAcceptMessage;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.*;
import cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage.ViewEventMessage;
import com.alibaba.fastjson.JSONObject;

/**
 * 事件消息
 */
public abstract class AbstractAcceptEventMessage extends AbstractAcceptMessage {
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
     * @param json json
     * @return acceptEventMessage
     * @throws Exception exception
     */
    public static AbstractAcceptEventMessage eventResolver(JSONObject json) throws Exception {
        AbstractAcceptEventMessage ret;
        json.put("Event", json.getString("Event").toUpperCase());

        switch (Event.valueOf(json.getString("Event").toUpperCase())) {
            case SCAN://扫描事件
                ret = json.toJavaObject(ScanEventMessage.class);
                break;
            case VIEW://视图事件
                ret = json.toJavaObject(ViewEventMessage.class);
                break;
            case CLICK://点击事件
                ret = json.toJavaObject(ClickEventMessage.class);
                break;
            case LOCATION://地理位置事件
                ret = json.toJavaObject(LocationEventMessage.class);
                break;
            case SUBSCRIBE://订阅事件
                ret = json.toJavaObject(SubscribeEventMessage.class);
                break;
            case UNSUBSCRIBE://取消订阅事件
                ret = json.toJavaObject(SubscribeEventMessage.class);
                break;
            case SCANCODE_PUSH://扫描二维码事件
                ret = json.toJavaObject(MenuScanEventMessage.class);
                break;
            case SCANCODE_WAITMSG://扫描二维码显示消息事件
                ret = json.toJavaObject(MenuScanEventMessage.class);
                break;
            case PIC_SYSPHOTO://调用系统相机
                ret = json.toJavaObject(PhotoEventMessage.class);
                break;
            case PIC_PHOTO_OR_ALBUM://调用系统或相册
                ret = json.toJavaObject(PhotoEventMessage.class);
                break;
            case TEMPLATESENDJOBFINISH://模板事件反馈
                ret = json.toJavaObject(BackStatusEventMessage.class);
                break;
            default:
                ret = null;
        }
        return ret;
    }

}
