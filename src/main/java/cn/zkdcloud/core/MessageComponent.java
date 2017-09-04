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
 * 消息组件
 */
public class MessageComponent implements Component {

    private static Logger logger = Logger.getLogger(MessageComponent.class);
    /**
     * message实例
     */
    public static MessageComponent messageComponent;

    /**
     * message适配方法
     */
    private Map<Method, Class> adapterMap = new HashMap<Method, Class>();

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

    private MessageComponent() {

    }

    /**
     * init classes
     *
     * @param classes classes include(@MessageProcess)
     */
    public void init(List<Class> classes) {
        for (Class clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                Class[] paraClazzs = method.getParameterTypes();
                if (paraClazzs != null && 1 == paraClazzs.length) {
                    if (AbstractAcceptMessage.class.isAssignableFrom(paraClazzs[0])) {//is parent from AbstractAcceptMessage
                        adapterMap.put(method, clazz); // add access method
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
    public String doAdapter(AbstractAcceptMessage acceptMessage) {
        Class exceptClass = acceptMessage.getClass();
        AbstractResponseMessage ret = null;

        for (Map.Entry<Method, Class> entry : adapterMap.entrySet()) {
            Method tar = entry.getKey();
            try {
                if (exceptClass == tar.getParameterTypes()[0]) {
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
    public String doAdapter(HttpServletRequest request) {
        AbstractAcceptMessage acceptMessage;
        try {
            acceptMessage = AbstractAcceptMessage.prepareMessage(request);
        } catch (Exception e) {
            logger.error(e.getMessage() + "---初始化消息失败");
            return "success";
        }
        return doAdapter(acceptMessage);
    }

    /**
     * getInstance 获取实例
     *
     * @return 唯一实例
     */
    public static MessageComponent getInstance() {
        if (null == messageComponent) {
            messageComponent = new MessageComponent();
        }
        return messageComponent;
    }
}
