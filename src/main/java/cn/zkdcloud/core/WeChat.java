package cn.zkdcloud.core;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/** weChat
 * @author zk
 * @version 2017/8/26
 */
public class WeChat {

    private static Logger logger = Logger.getLogger(WeChat.class);

    private static WeChat weChat;

    private static List<Component> components;

    private WeChat() {

    }

    /**
     * 启动weChat组件
     */
    public void start() {
        try {
            for (Component component : getComponents()) {
                component.init();
            }
            logger.info("init weChat success");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("init weChat fail:" + e.getMessage());
        }
    }

    /**
     * 默认启动weChat组件
     */
    public void startDefault(){
        addComponent(MessageComponent.class).addComponent(MenuComponent.class);
        start();
    }

    /**
     * 添加组件
     *
     * @param componentClass component class
     */
    public WeChat addComponent(Class<? extends Component> componentClass) {
        try {
            getComponents().add(componentClass.newInstance());
            logger.info("add component : " + componentClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 获取组件
     *
     * @return components
     */
    public List<Component> getComponents() {
        if (components == null) {
            components = new ArrayList<>();
        }
        return components;
    }

    /**
     * 获取weChat实例
     *
     * @return weChat
     */
    public static WeChat  create() {
        if (null == weChat) {
            weChat = new WeChat();
        }
        return weChat;
    }
}
