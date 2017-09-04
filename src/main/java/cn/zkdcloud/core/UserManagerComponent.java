package cn.zkdcloud.core;

import cn.zkdcloud.component.user.UserInfo;
import cn.zkdcloud.util.AccessToken;
import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 用户管理组件
 *
 * @author zk
 * @version 2017/9/3
 */
public class UserManagerComponent implements Component {

    private static Logger logger = Logger.getLogger(UserManagerComponent.class);

    public static UserManagerComponent userManagerComponent;

    /**
     * 设置用户备注名(POST)
     */
    public static String USER_REMARK_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=" + AccessToken.getAccessToken();

    /**
     * 查询用户信息(GET)
     */
    public static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + AccessToken.getAccessToken();

    /**
     * 批量获取用户基本信息(POST) *errmsg 无效openid
     */
    public static String BATCH_GET_USERINFOS = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + AccessToken.getAccessToken();

    /**
     * 批量获取用户openId，一次拉取调用最多拉取10000个关注者的OpenID(GET)
     */
    public static String GET_USER_OPENIDS = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取公账号黑名单列表(POST)
     * 该接口每次调用最多可拉取 10000 个OpenID，当列表数较多时，可以通过多次拉取的方式来满足需求。
     */
    public static String GET_BLACK_LIST = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=" + AccessToken.getAccessToken();

    /**
     * 批量拉黑用户(POST,一次最多20)
     */
    public static String BATCH_BLACK_USES = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=" + AccessToken.getAccessToken();

    /**
     * 批量取消拉黑用户(POST，一次最多20)
     */
    public static String BARCH_UNBLACK_USERS = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=" + AccessToken.getAccessToken();

    @Override
    public void init() {

    }

    private UserManagerComponent() {

    }

    /**
     * 设置用户备注名
     *
     * @param openid     open_id
     * @param remarkName remarkName
     * @return is or not success
     */
    public boolean remarkUser(String openid, String remarkName) {
        JSONObject data = new JSONObject();
        data.put("openid", openid);
        data.put("remark", remarkName);

        String ret = HttpUtil.doPost(USER_REMARK_URL, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("remark user:" + openid + remarkName + " fail");
            return false;
        }
        return true;
    }

    /**
     * 根据用户openid获取用户信息(language default:zh-cn)
     *
     * @param openid openid
     * @return userInfoBean
     */
    public UserInfo getUserInfoByOpenId(String openid) {
        return getUserInfoByOpenId(openid, "zh_CN");
    }

    /**
     * 根据用户openid 获取用户信息
     *
     * @param openid openid
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return userInfoBean
     */
    public UserInfo getUserInfoByOpenId(String openid, String lang) {
        String ret = HttpUtil.doPost(GET_USER_INFO + "&openid=" + openid + "&lang=" + lang);
        if (JsonUtil.isError(ret)) {
            logger.info("get userInfo :" + openid + "fail");
            return null;
        }
        return JSONObject.parseObject(ret).toJavaObject(UserInfo.class);
    }

    /**
     * 批量获取用户信息 ×
     *
     * @param openids openids
     * @return userInfos
     */
    public List<UserInfo> getBatchUserInfo(List<String> openids) {
        return getBatchUserInfo(openids, "zh_CN");
    }

    /**
     * 批量获取用户信息
     *
     * @param openids openids
     * @param lang    lang default 'zh_cn'
     * @return userInfos
     */
    public List<UserInfo> getBatchUserInfo(List<String> openids, String lang) {
        JSONObject data = new JSONObject();
        JSONArray userList = new JSONArray();
        for (String openid : openids) {
            userList.add("{\"openid\": \"" + openid + "\",\"lang\": \"" + lang + "\"}");
        }
        data.put("user_list", userList);
        String ret = HttpUtil.doPost(BATCH_GET_USERINFOS, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("get batch uerInfo fail:" + ret);
            return null;
        }

        if (null != JSONObject.parseObject(ret).getJSONArray("user_info_list")) {
            return JSONObject.parseObject(ret).getJSONArray("user_info_list").toJavaList(UserInfo.class);
        }
        return null;
    }

    /**
     * 批量获取用户openid
     *
     * @return List<openId>
     */
    public List<String> getUserOpenids() {
        return getUserOpenids(null);
    }

    /**
     * 批量获取用户openid
     *
     * @param next_openid 开始的第一个openId
     * @return List<openId>
     */
    public List<String> getUserOpenids(String next_openid) {
        String ret;
        if (null != next_openid) {
            ret = HttpUtil.doGet(GET_USER_OPENIDS + "&next_openid=" + next_openid);
        } else {
            ret = HttpUtil.doGet(GET_USER_OPENIDS);
        }
        if (JsonUtil.isError(ret)) {
            logger.info("get user openids fail" + ret);
            return null;
        }

        if (null != JSONObject.parseObject(ret).getJSONObject("data")) {
            return JSONObject.parseObject(ret).getJSONObject("data").getJSONArray("openid").toJavaList(String.class);
        }
        return null;
    }

    /**
     * 获取公账号黑名单列表（一次最多10000）
     *
     * @return List<openId>
     */
    public List<String> getBlackList() {
        return getBlackList(null);
    }

    /**
     * 获取公众号黑名单列表
     *
     * @param begin_openid 开始的openid
     * @return List<openid>
     */
    public List<String> getBlackList(String begin_openid) {
        String ret;
        if (null == begin_openid) {
            ret = HttpUtil.doPost(GET_BLACK_LIST);
        } else {
            ret = HttpUtil.doPost(GET_BLACK_LIST, "{\"begin_openid\":\"" + begin_openid + "\"}");
        }

        if (JsonUtil.isError(ret) || null == JSONObject.parseObject(ret).get("data")) {
            logger.info("get blackList fail or list is not :" + ret);
            return null;
        }
        return JSONObject.parseObject(ret).getJSONObject("data").getJSONArray("openid").toJavaList(String.class);
    }

    /**
     * 批量拉黑用户
     *
     * @param openIds List<openId>
     * @return is or not success
     */
    public boolean batchBlackUsers(List<String> openIds) {
        JSONObject data = new JSONObject();
        data.put("opened_list", openIds);
        String ret = HttpUtil.doPost(BATCH_BLACK_USES, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("batch black users fail :" + ret);
            return false;
        }
        return true;
    }

    /**
     * 批量取消拉黑用户
     *
     * @param openIds List<OpenId></>
     * @return is or not success
     */
    public boolean batchUnblackUsers(List<String> openIds) {
        JSONObject data = new JSONObject();
        data.put("opened_list", openIds);
        String ret = HttpUtil.doPost(BARCH_UNBLACK_USERS, data.toJSONString());
        if (JsonUtil.isError(ret)) {
            logger.info("batch unblack users fail :" + ret);
            return false;
        }
        return true;
    }

    public static UserManagerComponent getInstance() {
        if (null == userManagerComponent) {
            userManagerComponent = new UserManagerComponent();
        }
        return userManagerComponent;
    }
}
