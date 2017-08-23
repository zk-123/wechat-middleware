package cn.zkdcloud.dto;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;
import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;
import cn.zkdcloud.dto.acceptMessage.Event;
import cn.zkdcloud.util.StreamUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;


/**
 * @author zk
 * @version 2017/8/20
 */
public abstract class AcceptMessage extends Message {
    private static Logger logger = Logger.getLogger(AcceptMessage.class);

    /**
     * 填充消息
     *
     * @param request request
     * @return acceptMessage
     */
    public static AcceptMessage prepareMessage(HttpServletRequest request) throws Exception {
        AcceptMessage ret;
        JSONObject json = JSON.parseObject(StreamUtil.xmlToJson(request));
        json.put("MsgType",json.getString("MsgType").toUpperCase());// fastJson why can't ignore upper and lower in enum

        if (MsgType.EVENT.toString().equalsIgnoreCase(json.getString("MsgType"))) {
            ret = AcceptEventMessage.eventResolver(json); //事件消息
        } else {
            ret = AcceptNormalMessage.messageResolver(json); //一般消息
        }
        return ret;
    }
}
