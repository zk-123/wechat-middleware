package cn.zkdcloud.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * startWeChatComponent
 *
 * @author zk
 * @version 2017/8/28
 */
public class WeChatListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WeChat.create().startDefault();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
