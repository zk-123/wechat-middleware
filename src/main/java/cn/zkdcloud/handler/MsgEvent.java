package cn.zkdcloud.handler;

import cn.zkdcloud.entity.Event;
import cn.zkdcloud.entity.MsgType;

/*
 msgType和Event 用来准确定位一个接受消息类型
 */
public class MsgEvent {
    /*
    msgType
     */
    private MsgType msgType;
    /*
    event
     */
    private Event event;

    public MsgEvent(MsgType msgType,Event event) {
        this.msgType = msgType;
        this.event = event;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
