package cn.zkdcloud.component.message;

import cn.zkdcloud.component.message.acceptMessage.AbstractAcceptEventMessage;
import cn.zkdcloud.component.message.acceptMessage.AbstractAcceptNormalMessage;
import cn.zkdcloud.util.StreamUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * @author zk
 * @version 2017/8/20
 */
public abstract class AbstractAcceptMessage extends Message {
    private static Logger logger = Logger.getLogger(AbstractAcceptMessage.class);

    /**
     * 填充消息
     *
     * @param request request
     * @return acceptMessage
     */
    public static AbstractAcceptMessage prepareMessage(HttpServletRequest request) throws Exception {
        AbstractAcceptMessage ret;
        JSONObject json = JSON.parseObject(StreamUtil.xmlToJson(request));
        json.put("MsgType", json.getString("MsgType").toUpperCase());// fastJson why can't ignore upper and lower in enum

        if (MsgType.EVENT.toString().equalsIgnoreCase(json.getString("MsgType"))) {
            ret = AbstractAcceptEventMessage.eventResolver(json); //事件消息
        } else {
            ret = AbstractAcceptNormalMessage.messageResolver(json); //一般消息
        }
        return ret;
    }
}
