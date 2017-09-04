package cn.zkdcloud.core;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/** weChat
 * @author zk
 * @version 2017/8/26
 */
public class WeChat {

    private static Logger logger = Logger.getLogger(WeChat.class);

    private static WeChat weChat;

    private static List<Component> components = new ArrayList<>();

    private WeChat() {

    }

    /**
     * 启动weChat组件
     */
    public void start() {
        try {
            for (Component component : components) {
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
        addComponent(MessageComponent.class);//消息组件
        addComponent(MenuComponent.class);//菜单组件
        addComponent(TemplateComponent.class);//模板消息组件
        addComponent(Oauth2AuthorizeComponent.class);//网页授权组件

        start();
    }

    /**
     * 添加组件
     *
     * @param componentClass component class
     */
    public WeChat addComponent(Class<? extends Component> componentClass) {
        try {
            Method getInstance = componentClass.getDeclaredMethod("getInstance");
            components.add((Component) getInstance.invoke(null));
            logger.info("add component : " + componentClass);
        }catch (Exception e){
            logger.info("add" + componentClass + "fail");
        }
        return this;
    }

    /**
     * getInstance
     *
     * @return weChat
     */
    public static WeChat getInstance() {
        if (null == weChat) {
            weChat = new WeChat();
        }
        return weChat;
    }
}
