package cn.zkdcloud.core;

import cn.zkdcloud.exception.WeChatException;
import cn.zkdcloud.util.AccessToken;
import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 用户标签管理组件(一个公账号最多添加100个标签)
 *
 * @author zk
 * @version 2017/9/2
 */
public class UserTagComponent implements Component {

    private static Logger logger = Logger.getLogger(UserTagComponent.class);

    public static UserTagComponent userTagComponent;

    /**
     * 创建标签(POST)
     */
    public static String CREATE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取公众号已创建的标签列表(GET)
     */
    public static String GET_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + AccessToken.getAccessToken();

    /**
     * 编辑标签(POST)
     */
    public static String UPDATE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=" + AccessToken.getAccessToken();

    /**
     * 删除标签(POST)
     */
    public static String DELETE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取某标签下粉丝列表
     */
    public static String GET_TAG_USERS = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=" + AccessToken.getAccessToken();

    /**
     * 批量为用户打标签(POST,标签功能目前支持公众号为用户打上最多20个标签。)
     */
    public static String BATCH_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=" + AccessToken.getAccessToken();

    /**
     * 批量为用户取消标签(POST)
     */
    public static String BATCH_UNTAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取用户身上的标签列表(POST)
     */
    public static String GET_TAGS_BY_USER = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=" + AccessToken.getAccessToken();

    @Override
    public void init() {

    }

    private UserTagComponent() {

    }

    /**
     * 添加标签
     *
     * @param tagName tagName 标签名
     * @return ret;
     * {
     * "tag":{
     * "id":134,//标签id
     * "name":"广东"
     * }
     * }
     */
    public String createTag(String tagName) throws WeChatException {
        String ret = HttpUtil.doPost(CREATE_TAG_URL, "{\"tag\" : {\"name\" : \"" + tagName + "\"}}");
        if (JsonUtil.isError(ret)) {
            throw new WeChatException("create tag fail:" + ret);
        }
        return ret;
    }

    /**
     * 获取标签列表
     *
     * @return jsonArray
     * [
     * {
     * "id":1,
     * "name":"每天一罐可乐星人",
     * "count":0 //此标签下粉丝数
     * },
     * {
     * "id":2,
     * "name":"星标组",
     * "count":0
     * },
     * {
     * "id":127,
     * "name":"广东",
     * "count":5
     * }
     * ]
     */
    public JSONArray getTags() {
        String ret = HttpUtil.doGet(GET_TAG_URL);
        if (JsonUtil.isError(ret)) {
            logger.info("get tags list fail :" + ret);
            return null;
        }
        return JSONObject.parseObject(ret).getJSONArray("tags");
    }

    /**
     * 修改标签
     *
     * @param id      tag's id
     * @param tagName 标签名
     * @return is or not success
     */
    public boolean updateTag(Integer id, String tagName) {
        String ret = HttpUtil.doPost(UPDATE_TAG_URL, " {\"tag\" : {\"id\" : " + id + ", \"name\" : \"" + tagName + "\"}}");
        if (JsonUtil.isError(ret)) {
            logger.info("update tag name " + tagName + " fail :" + ret);
            return false;
        }
        return true;
    }

    /**
     * 删除标签
     * <p>
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。
     * 此时，开发者可以对该标签下的openid列表，先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
     *
     * @param id 标签id
     * @return is or not delete
     */
    public boolean deleteTag(Integer id) {
        String ret = HttpUtil.doPost(DELETE_TAG_URL, "{\"tag\":{\"id\" : " + id + "}}");
        if (JsonUtil.isError(ret)) {
            logger.info("delete tag id " + id + " fail:" + ret);
            return false;
        }
        return true;
    }

    /**
     * 根据标签id获取用户列表
     *
     * @param tagid tagid
     * @return list<useopenid>
     */
    public List<String> getUserByTagId(Integer tagid) {
        return getUserByTagId(tagid, null);
    }

    /**
     * 根据标签id获取用户列表
     *
     * @param tagid       tagid
     * @param next_openid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return list<useropenid>
     */
    public List<String> getUserByTagId(Integer tagid, String next_openid) {
        JSONObject data = new JSONObject();
        data.put("tagid", tagid);
        data.put("next_openid", next_openid);

        String ret = HttpUtil.doPost(GET_TAG_USERS, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("get users by tagid fail:" + ret);
            return null;
        }
        JSONObject retJson = JSONObject.parseObject(ret);
        if (null != retJson.get("data")) {
            return retJson.getJSONObject("data").getJSONArray("openid").toJavaList(String.class);
        }
        return null;
    }

    /**
     * 批量为用户打标签
     *
     * @param openid_list 用户open_id列表
     * @param tagid       标签id
     * @return is or not success
     */
    public boolean batchTags(List<String> openid_list, Integer tagid) {
        JSONObject data = new JSONObject();
        data.put("tagid", tagid);
        data.put("openid_list", openid_list);

        String ret = HttpUtil.doPost(BATCH_TAG_URL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("batch users to tag error");
            return false;
        }
        return true;
    }

    /**
     * 批量为用户取消标签
     *
     * @param openid_list 用户open_id 列表
     * @param tagid       标签id
     * @return is or not success
     */
    public boolean batchUnTags(List<String> openid_list, Integer tagid) {
        JSONObject data = new JSONObject();
        data.put("tagid", tagid);
        data.put("openid_list", openid_list);

        String ret = HttpUtil.doPost(BATCH_UNTAG_URL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("batch users to untag error");
            return false;
        }
        return true;
    }

    /**
     * 获取某用户身上的标签
     *
     * @param open_id user's openid
     * @return List<tagid>
     */
    public List<Integer> getTagsByUser(String open_id) {
        String ret = HttpUtil.doPost(GET_TAGS_BY_USER, "{\"openid\" : \"" + open_id + "\"}");
        if (JsonUtil.isError(ret)) {
            logger.info("get tags by user fail" + ret);
            return null;
        }
        if (null != JSONObject.parseObject(ret).get("tagid_list")) {
            return JSONObject.parseObject(ret).getJSONArray("tagid_list").toJavaList(Integer.class);
        }
        return null;
    }

    /**
     * 获取用户标签管理组件实例
     *
     * @return userTagComponentBean
     */
    public static UserTagComponent getInstance() {
        if (null == userTagComponent) {
            userTagComponent = new UserTagComponent();
        }
        return userTagComponent;
    }

}
