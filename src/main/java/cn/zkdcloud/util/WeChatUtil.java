package cn.zkdcloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import java.io.*;


public class WeChatUtil {
    private static Logger logger = Logger.getLogger(WeChatUtil.class);

    /**
     * 默认httpClient
     */
    private static HttpClient httpClient = HttpClients.createDefault();

    /**
     * 获取Access_token
     *
     * @return String
     */
    public static String getGetAccessToken() {
        HttpGet httpGet = new HttpGet(Const.GET_ACCESS_TOKEN_URL);
        HttpResponse response = null;
        JSONObject ret = null;

        try {
            response = httpClient.execute(httpGet);
            ret = JSON.parseObject(StreamUtil.inputStreamToStr(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ret != null && ret.get("access_token") != null) {
            return ret.getString("access_token");
        }
        return null;
    }


}
