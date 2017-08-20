package cn.zkdcloud.dto;

import cn.zkdcloud.entity.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zk on 2016/10/30.
 */
public abstract class Message {
    /**
     * 开发者微信号
     */
    @XStreamAlias("ToUserName")
    protected String toUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    @XStreamAlias("FromUserName")
    protected String fromUserName;

    /**
     * 消息创建时间 （整型）
     */
    @XStreamAlias("CreateTime")
    protected long createTime;

    /**
     * 消息类型
     */
    @XStreamAlias("MsgType")
    protected MsgType msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
