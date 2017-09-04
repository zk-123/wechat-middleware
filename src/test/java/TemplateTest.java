import cn.zkdcloud.core.Oauth2AuthorizeComponent;
import cn.zkdcloud.core.TemplateComponent;
import cn.zkdcloud.core.TemplateComponent.TemplateMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 模板测试
 * @author zk
 * @version 2017/8/29
 */
public class TemplateTest {

    private TemplateComponent templateComponent;

    private Oauth2AuthorizeComponent oauth2AuthorizeComponent;
    @Before
    public void initTemplateComponent(){
        templateComponent = TemplateComponent.getInstance();
        oauth2AuthorizeComponent = Oauth2AuthorizeComponent.getInstance();
    }

    /**
     * 设置模板行业
     */
    @Test
    public void setIndustry(){
        templateComponent.setTemplateIndustry(1,2);
    }

    /**
     * 获取所有模板列表
     */
    @Test
    public void getAllTemplate(){
        System.out.println(templateComponent.getListTemplateMessage());
    }

    /**
     * 发送模板消息
     */
    @Test
    public void sendTemplateMessage(){
        TemplateMessage templateMessage =
                new TemplateMessage("oGCUGwLIsuHjdD2g1novVyit2S5M","5sBB5X2-IPTl5c9LQ_zLsz_qtHxqJkto88mtBJjvLpw","http://www.baidu.com");
        templateMessage.addData("first","本月话费提醒","#526dea");
        templateMessage.addData("money","1个亿");
        templateMessage.addData("paly","10个亿");
        templateMessage.addData("donate","100个亿");
        templateMessage.addData("remark","您的余额不足100亿，请续缴话费！","#cccccc");

        templateComponent.sendTemplateMessage(templateMessage);
    }

    @Test
    public void getTemplateList(){
        List<TemplateMessage> templates = templateComponent.getListTemplateMessage(true);
        System.out.println(templates.size());
    }

    @Test
    public void getAndSendTemplateMessage(){
        String confirmUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa581eff59e79cda2&redirect_uri=https%3a%2f%2fhacpai.com&response_type=" +
                "code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        TemplateMessage template = templateComponent.getTemplateByName("话费提醒模板");
        if (null != template) {
            template.setTouserAndUrl("oGCUGwLIsuHjdD2g1novVyit2S5M", confirmUrl);
            template.addData("first", "本月话费提醒", "#526dea");
            template.addData("money", "1个亿");
            template.addData("paly", "10个亿");
            template.addData("donate", "100个亿");
            template.addData("remark", "您的余额不足100亿，请续缴话费！", "#cccccc");

            templateComponent.sendTemplateMessage(template);
        }
    }

    @Test
    public void deleteTemplateMessage(){
        templateComponent.deleteTemplateMessageByName("话费提醒2");
    }

    @Test
    public void urlEncoding() throws UnsupportedEncodingException {
        String url="https://www.baidu.com";
        System.out.println(URLEncoder.encode(url,"UTF-8"));
    }

    @Test
    public void getAccessToken(){
        String code = "0012AOc101JeJC1sDHg10WGSc102AOcJ";
        String ret = oauth2AuthorizeComponent.getUserAccessToken(code);
        System.out.println(ret);
    }

    @Test
    public void getUserInfo(){
        String retStr = oauth2AuthorizeComponent.getUserInfo("K6M_UjdiCMdgoch_GCHXWERMEEKIei_ysD3aXpNw8es5FkGCDs8rGK41Ag9tNuxq26RTWT32ov6AyiUHnV_2xg","oGCUGwLIsuHjdD2g1novVyit2S5M");
        JSONObject userInfo = JSON.parseObject(retStr);
        System.out.println(userInfo.getString("headimgurl"));
    }
/*
    {"access_token":"K6M_UjdiCMdgoch_GCHXWERMEEKIei_ysD3aXpNw8es5FkGCDs8rGK41Ag9tNuxq26RTWT32ov6AyiUHnV_2xg","expires_in":7200,"refresh_token":"a5rf3cI9FNrTw8QY9folX980RSwT8qkyBz85WzbhlPdpfE5UYqD50rKCAtnw6sVN2Qf9NOghz_uQT33guzQqCg","openid":"oGCUGwLIsuHjdD2g1novVyit2S5M","scope":"snsapi_userinfo"}
*/

}
