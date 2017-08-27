package cn.zkdcloud.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.*;


public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 默认httpClient
     */
    private static HttpClient httpClient = HttpClients.createDefault();


    /**
     * 执行Get方法
     *
     * @param url 请求url
     * @return jsonString
     */
    public static String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            return StreamUtil.inputStreamToStr(response.getEntity().getContent());
        } catch (IOException e) {
            logger.error(url + "is error");
        }
        return Const.HTTP_ERROR;
    }

    /**
     * 执行POST方法
     * @param url 请求url
     * @param data data
     * @return jsonString
     */
    public static String doPost(String url,String data){
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(data,"UTF-8"));
        HttpResponse response = null;

        try{
            response = httpClient.execute(httpPost);
            return StreamUtil.inputStreamToStr(response.getEntity().getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Const.HTTP_ERROR;
    }
}
