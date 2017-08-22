package cn.zkdcloud.dto;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;
import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;
import cn.zkdcloud.dto.acceptMessage.Event;
import cn.zkdcloud.util.StreamUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
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

        if (MsgType.EVENT.toString().equalsIgnoreCase(data.get("MsgType"))) {
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
            Class tarClass = getFieldByName(key.substring(0, 1).toLowerCase() + key.substring(1),clazz);// find type

            Method method = clazz.getMethod("set" + key, tarClass);//normalFile
            method.invoke(t, parseTo(tarClass,data.get(key)));
        }
        return t;
    }

    /**
     * 根据字段名，寻找该类和其父类中的字段
     * @param fieldName
     * @return
     */
    public static Class getFieldByName(String fieldName,Class clazz){
        for(Field field : clazz.getDeclaredFields()){
            if(field.getName().equalsIgnoreCase(fieldName)){
                return field.getType();
            }
        }

        if(Object.class != clazz.getSuperclass()){// if not find in this class ,then find in it's superclass
            return getFieldByName(fieldName,clazz.getSuperclass());
        }
        return null;
    }

    /**
     *  想着直接强制转化，无奈不能，只能根据接收的类型来强制转化
     * @param tarClass tar
     * @param source source
     * @return obj
     */
    public static Object parseTo(Class tarClass,String source){
        if(tarClass == String.class){
            return source;
        } else if (tarClass == long.class){
            return Long.valueOf(source);
        } else if (tarClass == MsgType.class){
            return MsgType.valueOf(source.toUpperCase());
        } else if(tarClass == Event.class){
            return Event.valueOf(source.toUpperCase());
        } else {
            return source;
        }
    }
}
