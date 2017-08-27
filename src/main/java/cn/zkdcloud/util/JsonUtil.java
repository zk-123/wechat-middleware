package cn.zkdcloud.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

/**
 * 处理json字符串
 *
 * @author zk
 * @version 2017/8/26
 */
public class JsonUtil {

    private static Logger logger = Logger.getLogger(JsonUtil.class);

    /**
     * 检查Http的返回结果
     *
     * @param jsonStr jsonStr
     * @return is Error?
     */
    public static boolean isError(String jsonStr) {
        return null == jsonStr ? true : isError(JSONObject.parseObject(jsonStr));
    }

    /**
     * 检查Http返回结果
     *
     * @param status jsonObject
     * @return is Error?
     */
    public static boolean isError(JSONObject status) {
        if (status == null) {
            return true;
        }

        if (null != status.getLong("errcode") && 0 != status.getLong("errcode")) {
            logger.error("this request is fail : " + status.getString("errmsg"));
            return true;
        }
        return false;
    }
}
