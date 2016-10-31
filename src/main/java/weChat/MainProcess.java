package weChat;

import Entity.TextMessage;
import Service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.*;

/**
 * Created by 郑凯 on 2016/8/12.
 */
@Controller
public class MainProcess {

    @Autowired
    UtilService utilService;

    Map<String,String> map ;

    @RequestMapping(value = "/weChatOn",produces = "text/html;charset=utf-8",method = RequestMethod.GET)
    @ResponseBody
    public String weChatOn(@RequestParam("signature")String signature,@RequestParam("timestamp")String timestamp,@RequestParam("nonce")String nonce,@RequestParam("echostr")String echostr)
    {
        final String token = "zk123";

        ArrayList list = new ArrayList();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);

        Collections.sort(list,new SpellComparator());

        String temp="";
        for(int i =0;i <list.size();i++)
        {
            temp += list.get(i);
        }

        SHA1 sha1 = new SHA1();
        String digest = sha1.getDigestOfString(temp.getBytes());

        if(digest.equalsIgnoreCase(signature))
        {
            return echostr;
        }
        return "";
    }


    @RequestMapping(value = "/weChatOn",produces ="text/html;charset=utf-8",method = RequestMethod.POST)

    public void operator(PrintWriter out, HttpServletRequest request, HttpServletResponse response)
    {
        map = utilService.xmlToMap(request);

        // 发送方帐号（一个OpenID）
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        // 消息类型
        String msgType = map.get("MsgType");
        // 默认回复一个"success"
        String responseMessage = "success";

        if(weChatConstant.MESSAGE_TEXT.equals(msgType))
        {
            TextMessage textMessage = new TextMessage();
            textMessage.setMsgType(weChatConstant.MESSAGE_TEXT);
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setContent("我已经收到你发来的消息了");
            responseMessage = utilService.ObjToXml(textMessage);
        }
        response.setCharacterEncoding("utf-8");
        out.print(responseMessage);
    }
}
