package cn.zkdcloud.dto.acceptMessage;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.*;
import cn.zkdcloud.dto.MsgType;

import java.util.Map;

/**
 * 一般消息
 */
public abstract class AcceptNormalMessage extends AcceptMessage{
    /*
    消息id
     */
    protected String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * 将data 根据不同的msgType转化成不同的message
     * @param data request data
     * @return ret
     */
    public static AcceptNormalMessage messageResolver(Map<String, String> data) throws Exception {
        AcceptNormalMessage ret;

        switch (MsgType.valueOf(data.get("MsgType").toUpperCase())) {
            case TEXT:
                ret = fillMessage(data, AcceptTextMessage.class);
                break;
            case IMAGE:
                ret = fillMessage(data, AcceptImageMessage.class);
                break;
            case VOICE:
                ret = fillMessage(data, AcceptVoiceMessage.class);
                break;
            case SHORTVIDEO:
                ret = fillMessage(data, AcceptShotVideoMessage.class);
                break;
            case LOCATION:
                ret = fillMessage(data, AcceptLocationMessage.class);
                break;
            case LINK:
                ret = fillMessage(data,AcceptLinkMessage.class);
                break;
            default:
                ret = null;
        }
        return ret;
    }
}
