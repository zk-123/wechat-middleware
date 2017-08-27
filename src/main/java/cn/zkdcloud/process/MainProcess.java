package cn.zkdcloud.process;

import cn.zkdcloud.component.message.AbstractAcceptMessage;
import cn.zkdcloud.core.MessageComponent;

import cn.zkdcloud.util.ConfirmUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by 郑凯 on 2016/8/12.
 */
@Controller
public class MainProcess {

    private static Logger logger = Logger.getLogger(MainProcess.class);


    String responseMessage;

    Map<String, String> map;

    /**
     * 微信接口认证 (GET)
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   字符串
     * @return ret
     */
    @RequestMapping(value = "/weChatOn", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String weChatOn(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        return ConfirmUtil.confirm(signature, timestamp, nonce, echostr);
    }

    /**
     * 用户请求主接口
     *
     * @param request request
     * @return ret
     */
    @RequestMapping(value = "/weChatOn", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String operator(HttpServletRequest request) {

        AbstractAcceptMessage acceptMessage;

        try {
            acceptMessage = AbstractAcceptMessage.prepareMessage(request);
        } catch (Exception e) {
            logger.error(e.getMessage() + "---初始化消息失败");
            return "success";
        }

        return MessageComponent.doAdapter(acceptMessage);
    }
}
