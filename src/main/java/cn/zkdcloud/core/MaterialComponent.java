package cn.zkdcloud.core;

import cn.zkdcloud.component.material.Material;
import cn.zkdcloud.component.material.MaterialArticle;
import cn.zkdcloud.component.material.MaterialType;
import cn.zkdcloud.util.AccessToken;
import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材管理
 *
 * @author zk
 * @version 2017/8/31
 */
public class MaterialComponent implements Component {

    private static Logger logger = Logger.getLogger(MaterialComponent.class);

    public static MaterialComponent materialComponent;
    /**
     * 上传临时素材(POST,FROM)
     */
    public static String UPLOAD_TEMP_MATERIAL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + AccessToken.getAccessToken() + "&type=";

    /**
     * 新增永久图文素材(POST)
     */
    public static String UPLOAD_PERSISTENT_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=" + AccessToken.getAccessToken();

    /**
     * 上传永久图片获取其URL(POST,FROM)
     */
    public static String UPLOAD_IMG_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + AccessToken.getAccessToken();

    /**
     * 新增其他类型永久素材
     * 通过POST表单来调用接口，表单id为media，包含需要上传的素材内容，有filename、filelength、content-type等信息
     * 请注意：图片素材将进入公众平台官网素材管理模块中的默认分组。
     * 意思是说也能从这里上传永久图片素材,至于占不占素材库，就不知道了，可能占
     */
    public static String UPLOAD_PERSISTENT_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + AccessToken.getAccessToken() + "&type=";

    /**
     * 下载临时的素材(GET 图片，语音，缩略图,视频等)
     */
    public static String DOWNLOAD_TEMP_MATERIAL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + AccessToken.getAccessToken() + "&media_id=";

    /**
     * 下载永久素材(POST 图片，语音，缩略图，视频，图文消息)
     */
    public static String DOWNLOAD_PERSISTENT_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取各种永久素材个数(GET)
     */
    public static String COUNT_PERSISTENT_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取永久素材列表(POST)
     */
    public static String LIST_PERSISTENT_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + AccessToken.getAccessToken();

    /**
     * 修改永久图文素材(POST)
     */
    public static String UPDATE_PERSISTENT_NEWS_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=" + AccessToken.getAccessToken();

    /**
     * 删除永久素材(POST)
     */
    public static String DELETE_PERSISTENT_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=" + AccessToken.getAccessToken();

    @Override
    public void init() {

    }

    private MaterialComponent() {

    }

    /**
     * 上传临时素材(image,video,thumb,voice保存3天)
     *
     * @param file file material
     * @param type 素材类型
     * @return media_id
     */
    public String uploadTempMaterial(File file, MaterialType type) {
        String ret = HttpUtil.doPost(UPLOAD_TEMP_MATERIAL + type.toString().toLowerCase(), file);
        if (null == ret) {
            logger.info("upload temp image fail");
            return null;
        }
        return JSONObject.parseObject(ret).getString("media_id");
    }

    /**
     * 上传永久图文素材
     *
     * @param articles articles
     * @return media_id
     */
    public String uploadPersistentMaterial(List<MaterialArticle> articles) {
        return uploadPersistentMaterial("{\"articles\":" + JSONObject.toJSONString(articles) + "}");
    }

    /**
     * 上传永久图文素材
     *
     * @param articles articles 图文json
     * @return media_id
     */
    public String uploadPersistentMaterial(String articles) {
        String ret = HttpUtil.doPost(UPLOAD_PERSISTENT_NEWS, articles);
        if (JsonUtil.isError(ret)) {
            logger.info("UPLOAD persistent material fail");
            return null;
        }
        return JSONObject.parseObject(ret).getString("media_id");
    }

    /**
     * 上传图片获取URL(注意返回的是url不是media_id,所以其不占素材库5000个限制)
     *
     * @param file img(jpg/png)
     * @return image's url
     */
    public String uploadPersistentImage(File file) {
        String ret = HttpUtil.doPost(UPLOAD_IMG_URL, file);
        if (JsonUtil.isError(ret)) {
            logger.info("upload image fail");
            return null;
        }
        return JSONObject.parseObject(ret).getString("url");
    }

    /**
     * 上传其他永久素材
     *
     * @param file file material
     * @return media_id and url
     * {
     * "media_id":MEDIA_ID,
     * "url":URL
     * }
     */
    public String uploadPersistentMaterial(File file, MaterialType type, String title, String introduction) {
        JSONObject attach = new JSONObject();
        attach.put("title", title);
        attach.put("introduction", introduction);

        String ret = HttpUtil.doPost(UPLOAD_PERSISTENT_MATERIAL + type.toString().toLowerCase(), file, attach.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("upload persistent material fail");
            return null;
        }
        return ret;
    }

    /**
     * 下载临时素材视频url
     *
     * @param media_id media_id
     * @return video url
     */
    public String downloadVideo(String media_id) {
        String ret = HttpUtil.doGet(DOWNLOAD_TEMP_MATERIAL.replace("https", "http") + media_id);
        if (JsonUtil.isError(ret)) {
            logger.info("get video resources fail");
            return null;
        }
        return JSONObject.parseObject(ret).getString("video_url");
    }


    /**
     * 下载临时素材(不包括视频)
     *
     * @param media_id media_id
     * @param filePath filePath(带文件名+文件格式)
     * @return file
     */
    public File downloadTempMaterial(String media_id, String filePath) {
        return HttpUtil.doGet(DOWNLOAD_TEMP_MATERIAL + media_id, filePath);
    }

    /**
     * 下载临时素材(不包括视频)
     *
     * @param media_id    media_id
     * @param fileDirPath 下载文件夹路i纪念馆
     * @param suffix      后缀名
     * @return
     */
    public File downloadTempMaterial(String media_id, String fileDirPath, String suffix) {
        fileDirPath = fileDirPath.replace("\\", "/");
        if (!fileDirPath.endsWith("/")) {
            fileDirPath += "/";
        }
        if (suffix.startsWith(".")) {
            suffix = suffix.substring(1);
        }

        String filePath = fileDirPath + System.currentTimeMillis() + "." + suffix;
        return HttpUtil.doGet(DOWNLOAD_TEMP_MATERIAL + media_id, filePath);
    }

    /**
     * 下载永久图文素材
     *
     * @param media_id media_id
     * @return List<news>
     */
    public List<MaterialArticle> downloadPersistentNews(String media_id) {
        JSONObject data = new JSONObject();
        data.put("media_id", media_id);
        String ret = HttpUtil.doPost(DOWNLOAD_PERSISTENT_MATERIAL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("get persistent news fail");
            return null;
        }
        List<MaterialArticle> articles = JSONObject.parseObject(ret).getJSONArray("news_item").toJavaList(MaterialArticle.class);
        return articles;
    }

    /**
     * 获取永久视频素材的url
     *
     * @param media_id media_id
     * @return video download url
     */
    public String downloadPersistentVideo(String media_id) {
        String ret = HttpUtil.doPost(DOWNLOAD_PERSISTENT_MATERIAL, "{\"media_id\":" + media_id + "}");
        if (JsonUtil.isError(ret)) {
            logger.info("get persistent video fail");
            return null;
        }
        return JSONObject.parseObject(ret).getString("down_url");
    }

    /**
     * 下载永久素材(不包括视频，图文)
     *
     * @param media_id media_id
     * @param filePath filePath(带文件名+文件格式)
     * @return file
     */
    public File downloadPersistentMaterial(String media_id, String filePath) {
        return HttpUtil.doGet(DOWNLOAD_PERSISTENT_MATERIAL + media_id, filePath);
    }

    /**
     * 下载永久素材(不包括视频，图文)
     *
     * @param media_id    media_id
     * @param fileDirPath 下载文件夹路径
     * @param suffix      后缀名
     * @return
     */
    public File downloadPersistentMaterial(String media_id, String fileDirPath, String suffix) {
        fileDirPath = fileDirPath.replace("\\", "/");
        if (!fileDirPath.endsWith("/")) {
            fileDirPath += "/";
        }
        if (suffix.startsWith(".")) {
            suffix = suffix.substring(1);
        }

        String filePath = fileDirPath + System.currentTimeMillis() + "." + suffix;
        return HttpUtil.doGet(DOWNLOAD_PERSISTENT_MATERIAL + media_id, filePath);
    }

    /**
     * 统计永久素材总数
     *
     * @return countJson
     * {
     * "voice_count":COUNT, 语音
     * "video_count":COUNT, 视频
     * "image_count":COUNT, 照片
     * "news_count":COUNT   图文
     * }
     */
    public String countPersistentMaterial() {
        String ret = HttpUtil.doGet(COUNT_PERSISTENT_MATERIAL);
        if (JsonUtil.isError(ret)) {
            logger.info("count persistent material fail : " + ret);
            return null;
        }
        return ret;
    }

    /**
     * 获取图文素材列表
     *
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return <media_id,materialArticle>
     */
    public Map<String, List<MaterialArticle>> getListNewsPersistentMaterial(Integer offset, Integer count) {
        JSONObject data = new JSONObject();
        data.put("type", "news");
        data.put("offset", offset);
        data.put("count", count);
        String ret = HttpUtil.doPost(LIST_PERSISTENT_MATERIAL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("get list persistent material fail" + ret);
            return null;
        }
        Map<String, List<MaterialArticle>> retMap = new HashMap<>();
        JSONArray articlesJsonArray = JSONObject.parseObject(ret).getJSONArray("item");
        for (int i = 0; i < articlesJsonArray.size(); i++) {
            JSONObject item = articlesJsonArray.getJSONObject(i);
            retMap.put(item.getString("media_id"), item.getJSONObject("content").getJSONArray("news_item").toJavaList(MaterialArticle.class));
        }
        return retMap;
    }

    /**
     * 获取其它素材列表
     *
     * @param type   type
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return list<material>
     */
    public List<Material> getListPersistentMaterial(MaterialType type, Integer offset, Integer count) {
        JSONObject data = new JSONObject();
        data.put("type", type.toString().toLowerCase());
        data.put("offset", offset);
        data.put("count", count);
        String ret = HttpUtil.doPost(LIST_PERSISTENT_MATERIAL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("get list persistent material fail" + ret);
            return null;
        }

        return JSONObject.parseObject(ret).getJSONArray("item").toJavaList(Material.class);
    }

    /**
     * 更新某个图文永久素材
     *
     * @param media_id meida_id
     * @param index    要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @param article  新图文素材内容
     * @return is or not success
     */
    public boolean updateNewsPersistentMaterial(String media_id, Integer index, MaterialArticle article) {
        JSONObject data = new JSONObject();
        data.put("media_id", media_id);
        data.put("index", index);
        data.put("articles", JSONObject.toJSONString(article));

        String ret = HttpUtil.doPost(UPDATE_PERSISTENT_NEWS_MATERIAL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("update news persistent material fail :" + ret);
            return false;
        }
        return true;
    }

    /**
     * 删除指定永久素材
     *
     * @param media_id media_id
     * @return is or not success
     */
    public boolean deletePersistentMaterial(String media_id) {
        JSONObject data = new JSONObject();
        data.put("media_id", data);
        String ret = HttpUtil.doPost(DELETE_PERSISTENT_MATERIAL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("delete persistent material fail:" + ret);
            return false;
        }
        return true;
    }

    /**
     * 获取material实例
     *
     * @return materialComponentBean
     */
    public static MaterialComponent getInstance() {
        if (null == materialComponent) {
            materialComponent = new MaterialComponent();
        }
        return materialComponent;
    }
}
