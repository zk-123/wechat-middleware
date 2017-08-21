package cn.zkdcloud.dto;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;
import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;
import cn.zkdcloud.entity.MsgType;
import cn.zkdcloud.util.StreamUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

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
        Map<String, String> data = StreamUtil.xmlToMap(request);

        if (data.get("MsgType").equalsIgnoreCase(MsgType.EVENT.toString())) {
            ret = AcceptEventMessage.eventResolver(data); //事件消息
        } else {
            ret = AcceptNormalMessage.messageResolver(data); //一般消息
        }

        return ret;
    }

    /**
     * 根据不同类型，填充message
     *
     * @param data  requestData
     * @param clazz clazz
     * @param <T>   message
     * @return ret
     * @throws Exception e
     */
    public static <T> T fillMessage(Map<String, String> data, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();

        for (String key : data.keySet()) { //fill message
            Method method = clazz.getMethod("set" + data.get(key));
            if (method != null) {
                method.invoke(t, data.get(key));
            }
        }

        return t;
    }
}
