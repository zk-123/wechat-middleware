package cn.zkdcloud.process;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageProcess {
    private static Logger logger = Logger.getLogger(PageProcess.class);

    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    public String addMenuPage(){
        return "menu";
    }
}
