package cn.zkdcloud.core;

import cn.zkdcloud.annotation.MessageProcess;
import cn.zkdcloud.component.message.AbstractAcceptMessage;
import cn.zkdcloud.component.message.AbstractResponseMessage;
import cn.zkdcloud.util.FileUtil;
import cn.zkdcloud.util.StreamUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * message适配器
 */
public class MessageComponent implements Component {

    private static Logger logger = Logger.getLogger(MessageComponent.class);

    /**
     * message适配方法
     */
    private static Map<Method, Class> map;

    @Override
    public void init() {
        try {
            List<Class> classes = FileUtil.getClassByAnnotion(MessageProcess.class.getName());
            init(classes);
            logger.info("message component init finish");
        } catch (Exception e) {
            logger.error("message component init fail!");
            e.printStackTrace();
        }
    }

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
                    if (AbstractAcceptMessage.class.isAssignableFrom(paraClazzs[0])) {//is parent from AbstractAcceptMessage
                        AdapterMap.getAdapterMap().put(method, clazz); // add access method
                    }
                }
            }
        }
    }

    /**
     * 适配方法
     *
     * @param acceptMessage acceptMessage
     * @return response ret
     */
    public static String doAdapter(AbstractAcceptMessage acceptMessage) {
        Class exceptClass = acceptMessage.getClass();
        AbstractResponseMessage ret = null;

        for (Map.Entry<Method, Class> entry : AdapterMap.getAdapterMap().entrySet()) {
            Method tar = entry.getKey();
            try {
                if (exceptClass == tar.getParameters()[0].getType()) {
                    ret = (AbstractResponseMessage) tar.invoke(entry.getValue().newInstance(), acceptMessage);

                    ret.setFromUserName(acceptMessage.getToUserName());// addFromUsername
                    ret.setToUserName(acceptMessage.getFromUserName());// addToUsername
                    if (ret == null) {
                        return "success";
                    }
                    return StreamUtil.ObjToXml(ret);
                }
            } catch (Exception e) {
                continue;
            }
        }
        logger.info("no fit method for this request, please check it");
        return "success";
    }

    /**
     * 适配方法
     *
     * @param request httpServletRequest
     * @return response ret
     */
    public static String doAdapter(HttpServletRequest request) {
        AbstractAcceptMessage acceptMessage;
        try {
            acceptMessage = AbstractAcceptMessage.prepareMessage(request);
        } catch (Exception e) {
            logger.error(e.getMessage() + "---初始化消息失败");
            return "success";
        }
        return MessageComponent.doAdapter(acceptMessage);
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
