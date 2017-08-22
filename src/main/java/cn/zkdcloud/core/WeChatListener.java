package cn.zkdcloud.core;

import cn.zkdcloud.annotation.MessageProcess;
import cn.zkdcloud.util.FileUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class WeChatListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<Class>  classes = FileUtil.getClassByAnnotion(MessageProcess.class.getName());
        MessageAdapter.init(classes);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
