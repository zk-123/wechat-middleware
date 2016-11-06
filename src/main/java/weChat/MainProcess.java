package weChat;

import Entity.BaseMessage;
import Entity.NewsMessage;
import Entity.TextMessage;
import Service.MainOperatorService;
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

    /**
     * 主服务
     * */
    @Autowired
    MainOperatorService mainOperatorService;


    String responseMessage ;

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


    @RequestMapping(value = "/weChatOn",produces ="text/plain;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public String operator(HttpServletRequest request, HttpServletResponse response)
    {
        /**
         * request对象封装成map
        * */
        map = utilService.xmlToMap(request);

        /**
         * map封装成基本的回复消息类
         * */
        BaseMessage baseMessage =  mainOperatorService.mapToBase(map);



        if(weChatConstant.MESSAGE_TEXT.equals(baseMessage.getMsgType()))
        {
            /**
             * 是文本类的先转化成文本消息类
             * */
            TextMessage textMessage = mainOperatorService.baseToText(baseMessage);
            textMessage.setContent("收到");
            responseMessage = utilService.ObjToXml(textMessage);
            System.out.println(responseMessage);
            return responseMessage;
        }
        else if(weChatConstant.MESSAGE_EVENT.equals(baseMessage.getMsgType()))
        {
            /**
             * 事件类消息
             * */
            NewsMessage newsMessage = mainOperatorService.baseToNews(baseMessage);

            newsMessage.setArticleCount(4);

            for(int i = 0;i< newsMessage.getArticleCount();i++)
            {
                /**
                 * 依次添加 文章的题目，描述，展示图片的URL,点击的链接
                 * */
                newsMessage.addArticle("题目一","描述题目一","https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=af9259bf03082838770ddb148898a964/6159252dd42a2834bc76c4ab5fb5c9ea14cebfba.jpg","http://mp.weixin.qq.com/s?__biz=MzI5MTUwMjE5OQ==&mid=2247483658&idx=2&sn=ed9dd671a26e97bba5af91411bcd4da5&chksm=ec0ee012db7969048129fd96a7c9b1b8e451e5f4e5a40b0f9cd1e14d7b526f65781f91970469&scene=0#wechat_redirect");
            }

            responseMessage = utilService.ObjToXml(newsMessage);
            System.out.println(responseMessage);
            return responseMessage;

        }
        else
        {
            return "success";
        }


    }
}
