package cn.zkdcloud.core;

import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import cn.zkdcloud.util.WeChatUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 网页授权管理
 *
 * @author zk
 * @version 2017/8/31
 */
public class Oauth2AuthorizeComponent implements Component {

    private static Logger logger = Logger.getLogger(Oauth2AuthorizeComponent.class);

    /**
     * 授权实例
     */
    public static Oauth2AuthorizeComponent authorizeComponent;

    /**
     * scope为snsapi_base 授权
     */
    public static String SNSAPI_BASE_URL;

    /**
     * scope为snsapi_userinfo 授权
     */
    public static String SNSAPI_USERINFO_URL;

    /**
     * 获取用户access token url
     */
    public static String GET_USER_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
            + WeChatUtil.getAppId() + "&secret=" + WeChatUtil.getAppSecret() + "&grant_type=authorization_code&code=";

    /**
     * 刷新yoghurt access token url
     */
    public static String REFRESH_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
            + WeChatUtil.getAppId() + "&grant_type=refresh_token&refresh_token=";

    /**
     * 获取用户信息(GET)
     */
    public static String GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?";

    /**
     * 检验该access_token是否有效
     */
    private static String CHECK_ACCESS_TOKEN = " https://api.weixin.qq.com/sns/auth?";

    /**
     * 可以初始化snsapiUrl
     */
    @Override
    public void init() {
    }

    private Oauth2AuthorizeComponent() {

    }

    /**
     * 设置scope为snsapi_base 和 snsapi_userinfo  授权
     *
     * @param redirect_uri 重定向url,应当使用https链接来确保授权code的安全性。
     * @param state        重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    public void setSnsapiUrl(String redirect_uri, String state) {
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
            SNSAPI_BASE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatUtil.getAppId()
                    + "&redirect_uri=" + redirect_uri + "&response_type=code&scope=snsapi_base&state=" + state + "#wechat_redirect";
            SNSAPI_USERINFO_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatUtil.getAppId()
                    + "&redirect_uri=" + redirect_uri + "&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据code 获取用户信息
     *
     * @param code code 5分钟未被使用自动过期
     * @return userInfo
     */
    public String getUserInfo(String code) {
        String accessTokenJson = getUserAccessToken(code);
        if (null != accessTokenJson) {
            JSONObject accessToken = JSONObject.parseObject(accessTokenJson);
            return getUserInfo(accessToken.getString("access_token"), accessToken.getString("openid"));
        }
        return null;
    }

    /**
     * 根据code获取用户授权的access_token
     *
     * @param code code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
     * @return jsonStr
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     */
    public String getUserAccessToken(String code) {
        String ret = HttpUtil.doGet(GET_USER_ACCESS_TOKEN + code);
        if (JsonUtil.isError(ret)) {
            logger.info("get user access tocken error!");
            return null;
        }
        return ret;
    }

    /**
     * 刷新拉取用户access token(GET/POST)
     *
     * @param refresh_token refresh token
     * @return jsonStr
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     */
    public String refreshUserAccessToken(String refresh_token) {
        String ret = HttpUtil.doGet(REFRESH_ACCESS_TOKEN + refresh_token);
        if (JsonUtil.isError(ret)) {
            logger.info("refresh user access token fail");
            return null;
        }
        return ret;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param access_token 网页授权接口调用凭证
     * @param openid       用户的唯一标识
     * @return 用户信息jsonStr
     * {
     * "openid":" OPENID",
     * " nickname": NICKNAME,
     * "sex":"1",
     * "province":"PROVINCE"
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl":    "http://wx.qlogo.cn/Q1dZuTOgvLLrhJbERQQ,
     * "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     */
    public String getUserInfo(String access_token, String openid) {
        return getUserInfo(access_token, openid, "zh_CN");
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param access_token 网页授权接口调用凭证
     * @param openid       用户的唯一标识
     * @return 用户信息jsonStr
     * {
     * "openid":" OPENID",
     * " nickname": NICKNAME,
     * "sex":"1",
     * "province":"PROVINCE"
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl":    "http://wx.qlogo.cn/Q1dZuTOgvLLrhJbERQQ,
     * "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     */
    public String getUserInfo(String access_token, String openid, String lang) {
        String ret = HttpUtil.doGet(GET_USER_INFO + "access_token=" + access_token + "&openid=" + openid + "&lang=" + lang);
        if (JsonUtil.isError(ret)) {
            logger.info("get userinfo fail");
            return null;
        }
        return ret;
    }

    /**
     * 检查该access是否有效
     *
     * @param access_token access_token
     * @param openid       openid
     * @return is or not effective
     */
    public boolean isEffective(String access_token, String openid) {
        String ret = HttpUtil.doGet(CHECK_ACCESS_TOKEN + "access_token=" + access_token + "&openid=" + openid);
        if(JsonUtil.isError(ret)){
            logger.info("this access is expire");
            return false;
        }
        return true;
    }

    /**
     * 获取授权组件实例
     *
     * @return authorizeComponent
     */
    public static Oauth2AuthorizeComponent getInstance() {
        if (null == authorizeComponent) {
            authorizeComponent = new Oauth2AuthorizeComponent();
        }
        return authorizeComponent;
    }
}
