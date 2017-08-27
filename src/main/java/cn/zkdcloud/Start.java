package cn.zkdcloud;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {
    public static void main(String args[]) throws Exception {
        Server server = new Server(2000);
        String webappPath = Thread.currentThread().getContextClassLoader().getResource("")
                .getPath().replace("/target/classes/", "/src/main/webapp/");

        WebAppContext context = new WebAppContext();
        context.setDescriptor(webappPath + "WEB-INF/web.xml");
        context.setResourceBase(webappPath);
        context.setContextPath("/");

        server.setHandler(context);
        server.start();
    }
}
