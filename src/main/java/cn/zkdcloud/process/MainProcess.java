package cn.zkdcloud.process;

import cn.zkdcloud.dto.Message;
import cn.zkdcloud.dto.NewsMessage;
import cn.zkdcloud.dto.acceptMessage.TextMessage;
import cn.zkdcloud.service.MainOperatorService;
import cn.zkdcloud.service.UtilService;
import cn.zkdcloud.util.ConfirmUtil;
import cn.zkdcloud.util.Const;
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
        /**
         * request对象封装成map
         * */
        map = StreamUtil.xmlToMap(request);

        /**
         * map封装成基本的回复消息类
         * */
        Message baseMessage = mainOperatorService.mapToBase(map);

        if (Const.MESSAGE_TEXT.equals(baseMessage.getMsgType())) {
            /**
             * 是文本类的先转化成文本消息类
             * */
            TextMessage textMessage = mainOperatorService.baseToText(baseMessage);
            textMessage.setContent("收到");
            responseMessage = StreamUtil.ObjToXml(textMessage);
            System.out.println(responseMessage);
            return responseMessage;
        } else if (Const.MESSAGE_EVENT.equals(baseMessage.getMsgType())) {
            /**
             * 事件类消息
             * */
            NewsMessage newsMessage = mainOperatorService.baseToNews(baseMessage);

            newsMessage.setArticleCount(4);

            for (int i = 0; i < newsMessage.getArticleCount(); i++) {
                /**
                 * 依次添加 文章的题目，描述，展示图片的URL,点击的链接
                 * */
                newsMessage.addArticle("题目一", "描述题目一", "https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=af9259bf03082838770ddb148898a964/6159252dd42a2834bc76c4ab5fb5c9ea14cebfba.jpg", "http://mp.weixin.qq.com/s?__biz=MzI5MTUwMjE5OQ==&mid=2247483658&idx=2&sn=ed9dd671a26e97bba5af91411bcd4da5&chksm=ec0ee012db7969048129fd96a7c9b1b8e451e5f4e5a40b0f9cd1e14d7b526f65781f91970469&scene=0#wechat_redirect");
            }

            responseMessage = StreamUtil.ObjToXml(newsMessage);
            System.out.println(responseMessage);
            return responseMessage;

        } else {
            return "success";
        }
    }
}
