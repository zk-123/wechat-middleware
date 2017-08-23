package cn.zkdcloud.core;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.dto.responseMessage.ResponseMusicMessage;
import cn.zkdcloud.util.StreamUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * message适配器
 */
public abstract class MessageAdapter {

    private static Logger logger = Logger.getLogger(MessageAdapter.class);

    /**
     * adapterMap
     */
    private static Map<Method, Class> map;

    /**
     * init classes
     *
     * @param classes classes include(@MessageProcess)
     */
    public static void init(List<Class> classes) {
        for (Class clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                Class[] paraClazzs = method.getParameterTypes();
                if (paraClazzs != null && 1 == paraClazzs.length) {
                    if (AcceptMessage.class.isAssignableFrom(paraClazzs[0])) {//is parent from AcceptMessage
                        AdapterMap.getAdapterMap().put(method, clazz); // add access method
                    }
                }
            }
        }
    }

    /**
     * 适配方法
     *
     * @param acceptMessage
     * @return
     */
    public static String doAdapter(AcceptMessage acceptMessage) {
        for (Map.Entry<Method, Class> entry : AdapterMap.getAdapterMap().entrySet()) {
            Method tar = entry.getKey();
            try {
                ResponseMessage ret = (ResponseMessage) tar.invoke(entry.getValue().newInstance(), acceptMessage);

                ret.setFromUserName(acceptMessage.getToUserName());// addFromUsername
                ret.setToUserName(acceptMessage.getFromUserName());// addToUsername

                if(ret == null){
                    return "success";
                }

                return StreamUtil.ObjToXml(ret);
            } catch (Exception e) {
                continue;
            }
        }
        logger.info("no fit method for this request, please check it");
        return "success";
    }

    /**
     * 单实例模式获取该map（可能也是多余了，因为它的继承类之间的加载本来就是异步的，所以不需要）
     */
    public static class AdapterMap {
        public static Map<Method, Class> getAdapterMap() {
            if (map == null) {
                map = new HashMap<Method, Class>();
            }
            return map;
        }
    }
}
