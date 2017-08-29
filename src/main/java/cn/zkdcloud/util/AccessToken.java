package cn.zkdcloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import java.io.IOException;

/**
 * 管理accessToken
 *
 * @author zk
 * @version 2017/8/26
 */
public class AccessToken {

    private static Logger logger = Logger.getLogger(AccessToken.class);

    /**
     * 默认httpClient
     */
    private static HttpClient httpClient = HttpClients.createDefault();

    /**
     * access_token的url
     */
    public static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token" +
            "?grant_type=client_credential&appid=" + WeChatUtil.getAppId() + "&secret=" + WeChatUtil.getAppSecret();
    /**
     * accessToken
     */
    private static String accessToken;

    /**
     * 有效期,单位s
     */
    private static long expiresIn;

    /**
     * 最后一次获取时间,单位s
     */
    private static long lastGetTime;

    /**
     * 获取access_token
     *
     * @return access_token
     */
    public static String getAccessToken() {
        if (null == accessToken || (lastGetTime + expiresIn < System.currentTimeMillis()/1000)){
            return refresh();
        }
        return accessToken;
    }

    /**
     * 刷新accessToken(也可以直接调用该方法强制刷新token)
     *
     * @return accessToken
     */
    public static String refresh() {
        HttpGet httpGet = new HttpGet(GET_ACCESS_TOKEN_URL);
        HttpResponse response = null;
        JSONObject ret = null;

        try {
            response = httpClient.execute(httpGet);
            ret = JSON.parseObject(StreamUtil.inputStreamToStr(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("get accessToken net error");
        }

        if (JsonUtil.isError(ret)) {
            return Const.HTTP_ERROR;
        }

        lastGetTime = System.currentTimeMillis()/1000;
        expiresIn = ret.getLong("expires_in");
        accessToken = ret.getString("access_token");

        logger.info("get access_token success");
        return accessToken;
    }
}
