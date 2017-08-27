package cn.zkdcloud.core;

import cn.zkdcloud.util.AccessToken;
import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

/**
 * 客服管理
 *
 * @author zk
 * @version 2017/8/26
 */
public class CustomComponent implements Component {

    private static Logger logger = Logger.getLogger(CustomComponent.class);

    /**
     * 客服管理实体
     */
    private static CustomComponent customComponent;

    /**
     * 添加客服URL(POST)
     */
    public static String ADD = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=" + AccessToken.getAccessToken();

    /**
     * 修改客服URL()
     */
    public static String UPDATE = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=" + AccessToken.getAccessToken();

    private CustomComponent() {

    }

    @Override
    public void init() {

    }

    /**
     * 添加客服
     *
     * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
     * @param nickname   客服昵称
     * @param password
     */
    public void add(String kf_account, String nickname, String password) {
        JSONObject custom = new JSONObject();
        custom.put("kf_account", kf_account);
        custom.put("nickname", nickname);
        custom.put("password", password);

        String ret = HttpUtil.doPost(ADD, custom.toJSONString());
        if (JsonUtil.isError(ret)) {
            return;
        }
        logger.info("add custom success");
    }

    /**
     * 返回客服实体
     *
     * @return customBean
     */
    public static CustomComponent getInstance() {
        if (null == customComponent) {
            customComponent = new CustomComponent();
        }
        return customComponent;
    }
}
