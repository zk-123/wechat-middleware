package cn.zkdcloud.process;

import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.dto.responseMessage.ResponseMusicMessage;
import cn.zkdcloud.dto.responseMessage.ResponseNewsMessage;
import cn.zkdcloud.dto.responseMessage.ResponseTextMessage;
import cn.zkdcloud.service.MainOperatorService;
import cn.zkdcloud.service.UtilService;
import cn.zkdcloud.util.ConfirmUtil;
import cn.zkdcloud.util.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UtilService utilService;

    /**
     * 主服务
     */
    @Autowired
    MainOperatorService mainOperatorService;


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
     * @param request request
     * @return ret
     */
    @RequestMapping(value = "/weChatOn", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String operator(HttpServletRequest request) {
        Map<String,String> map = StreamUtil.xmlToMap(request);
        ResponseNewsMessage message = new ResponseNewsMessage(map.get("FromUserName"),map.get("ToUserName"));
        message.addArticle("title1","title1",
                "https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=2618178600,2784742614&fm=85&s=A23A76874A313A9E7ABE51370300A048",
                "http://www.baidu.com")
                .addArticle("title1","title1",
                        "https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=2618178600,2784742614&fm=85&s=A23A76874A313A9E7ABE51370300A048",
                        "http://www.baidu.com");

        return StreamUtil.ObjToXml(message);
    }
}
