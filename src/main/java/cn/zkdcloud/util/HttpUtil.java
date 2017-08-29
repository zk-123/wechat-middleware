package cn.zkdcloud.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
     * @return ret
     */
    public static String doGet(String url) {
        return httpExecute(new HttpGet(url));
    }

    /**
     * 执行POST方法
     *
     * @param url  请求url
     * @param data data
     * @return jsonString
     */
    public static String doPost(String url, String data) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(data, "UTF-8"));

        return httpExecute(httpPost);
    }


    /**
     * 上传File
     *
     * @param url query url
     * @param file file
     * @return ret
     */
    public static String doPost(String url, File file) {
        HttpPost httpPost = new HttpPost(url);

        FileBody body = new FileBody(file);
        HttpEntity fileEntity = MultipartEntityBuilder.create().addPart("media",body).build();
        httpPost.setEntity(fileEntity);
        return httpExecute(httpPost);
    }

    /**
     * 上传InputStream
     *
     * @param url query url
     * @param inputStream inputStream
     * @return ret
     */
    public static String doPost(String url, InputStream inputStream,String fileName) {
        HttpPost httpPost = new HttpPost(url);

        InputStreamBody body = new InputStreamBody(inputStream,fileName);
        HttpEntity fileEntity = MultipartEntityBuilder.create().addPart("media",body).build();
        httpPost.setEntity(fileEntity);
        return httpExecute(httpPost);
    }

    /**
     * 执行请求
     * @param http request
     * @return ret
     */
    public static String httpExecute(HttpUriRequest http){
        try {
            HttpResponse response = httpClient.execute(http);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Const.HTTP_ERROR;
    }
}
