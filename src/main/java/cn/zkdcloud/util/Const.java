package cn.zkdcloud.util;

public class Const {
    /*
    token
     */
    public static String TOKEN = "zk123";
    /*
    appid
     */
    public static String APP_ID = "wxa581eff59e79cda2";
    /*
    app-secret
     */
    public static String APP_SECRET = "81388d9e20fd3a2369082b005dabe9eb";

    /**
     * 文本消息
     */
    public static final String MESSAGE_TEXT = "text";
    /**
     * 图片消息
     */
    public static final String MESSAtGE_IMAGE = "image";

    /**
     * 事件
     */
    public static final String MESSAGE_EVENT = "event";

    /**
     * click事件
     */

    public static final String EVENT_CLICK = "CLICK";

    /**
     *
     * */
    public static final String MSGType_NEWS = "news";

    /*
    access_token的url
     */
    public static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
            "&appid=" + Const.APP_ID + "&secret=" + Const.APP_SECRET;
}
