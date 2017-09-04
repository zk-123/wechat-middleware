package cn.zkdcloud.component.qrcode;

/**
 * 二维码类型
 *
 * @author zk
 * @version 2017/9/4
 */
public enum QrType {
    /**
     * 临时的整型参数值
     */
    QR_SCENE,
    /**
     * 临时的字符串参数值
     */
    QR_STR_SCENE,
    /**
     * 永久的整型参数值
     */
    QR_LIMIT_SCENE,
    /**
     * 永久的字符串参数值
     */
    QR_LIMIT_STR_SCENE
}
