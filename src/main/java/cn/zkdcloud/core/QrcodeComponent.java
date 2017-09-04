package cn.zkdcloud.core;

import cn.zkdcloud.component.qrcode.QrType;
import cn.zkdcloud.util.AccessToken;
import cn.zkdcloud.util.HttpUtil;
import cn.zkdcloud.util.JsonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author zk
 * @version 2017/9/4
 */
public class QrcodeComponent implements Component {

    private static Logger logger = Logger.getLogger(QrcodeComponent.class);

    public static QrcodeComponent qrcodeComponent;

    /**
     * 创建二维码(POST)
     */
    public static String CREATE_QR = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + AccessToken.getAccessToken();

    /**
     * 获取二维码(GET,根据ticket)
     */
    public static String GET_GR = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    @Override
    public void init() {

    }

    /**
     * 创建临时/永久整型参数值
     *
     * @param scene_id scene_id
     * @param type     type
     * @return 二维码url地址(用户扫描后会出发扫描事件)
     */
    public String createIntegerQr(Integer scene_id, QrType type) {
        String ret;
        JSONObject data = new JSONObject();
        JSONObject action_info = new JSONObject();
        JSONObject scene = new JSONObject();

        scene.put("scene_id", scene_id);
        action_info.put("scene", scene);
        data.put("action_info", action_info);

        if (QrType.QR_SCENE == type) {//临时整形参数
            data.put("expire_seconds", 2592000);
            data.put("action_name", QrType.QR_SCENE.toString());
            ret = HttpUtil.doPost(CREATE_QR, data.toJSONString());
        } else if (QrType.QR_LIMIT_SCENE == QrType.QR_LIMIT_SCENE) {//永久整形参数
            data.put("action_name", QrType.QR_LIMIT_SCENE.toString());
            ret = HttpUtil.doPost(CREATE_QR, data.toJSONString());
        } else {
            logger.info("qr type is not support");
            return null;
        }

        if (JsonUtil.isError(ret)) {
            logger.info("get Integer qr fail");
            return null;
        }

        try {
            return GET_GR + URLEncoder.encode(JSONObject.parseObject(ret).getString("ticket"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info("get qrcode fail" + e.getMessage());
            return null;
        }
    }

    /**
     * 创建临时/永久字符串参数值
     *
     * @param scene_str scene_str 场景字符串
     * @param type     type
     * @return 二维码url地址(用户扫描后会出发扫描事件)
     */
    public String createStrQr(String scene_str, QrType type) {
        String ret;
        JSONObject data = new JSONObject();
        JSONObject action_info = new JSONObject();
        JSONObject scene = new JSONObject();

        scene.put("scene_str", scene_str);
        action_info.put("scene", scene);
        data.put("action_info", action_info);

        if (QrType.QR_STR_SCENE == type) {//临时字符串参数
            data.put("expire_seconds", 2592000);
            data.put("action_name", QrType.QR_SCENE.toString());
            ret = HttpUtil.doPost(CREATE_QR, data.toJSONString());
        } else if (QrType.QR_LIMIT_STR_SCENE == QrType.QR_LIMIT_SCENE) {//永久字符串参数
            data.put("action_name", QrType.QR_LIMIT_SCENE.toString());
            ret = HttpUtil.doPost(CREATE_QR, data.toJSONString());
        } else {
            logger.info("qr type is not support");
            return null;
        }

        if (JsonUtil.isError(ret)) {
            logger.info("get str qr fail");
            return null;
        }

        try {
            return GET_GR + URLEncoder.encode(JSONObject.parseObject(ret).getString("ticket"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info("get qrcode fail" + e.getMessage());
            return null;
        }
    }



    public static QrcodeComponent getInstance() {
        if (null == qrcodeComponent) {
            qrcodeComponent = new QrcodeComponent();
        }
        return qrcodeComponent;
    }
}
