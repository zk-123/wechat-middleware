package cn.zkdcloud.dto.acceptMessage;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.*;
import cn.zkdcloud.dto.MsgType;
import com.alibaba.fastjson.JSONObject;

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
     * 将json str 根据不同的msgType转化成不同的message
     * @param json request jsonData
     * @return ret
     */
    public static AcceptNormalMessage messageResolver(JSONObject json) throws Exception {
        AcceptNormalMessage ret;

        switch (MsgType.valueOf(json.getString("MsgType").toUpperCase())) {
            case TEXT:
                ret = json.toJavaObject(AcceptTextMessage.class);
                break;
            case IMAGE:
                ret = json.toJavaObject(AcceptImageMessage.class);
                break;
            case VOICE:
                ret = json.toJavaObject(AcceptVoiceMessage.class);
                break;
            case VIDEO:
                ret = json.toJavaObject(AcceptVideoMessage.class);
                break;
            case SHORTVIDEO:
                ret = json.toJavaObject(AcceptVideoMessage.class);
                break;
            case LOCATION:
                ret = json.toJavaObject(AcceptLocationMessage.class);
                break;
            case LINK:
                ret = json.toJavaObject(AcceptLinkMessage.class);
                break;
            default:
                ret = null;
        }
        return ret;
    }
}
