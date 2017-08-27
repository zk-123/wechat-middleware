package cn.zkdcloud.component.message.acceptMessage;

/**
 * 事件推送格式
 */
public enum Event {
    /**
     * 关注
     */
    SUBSCRIBE,

    /**
     * 取消关注
     */
    UNSUBSCRIBE,

    /**
     * 扫描
     */
    SCAN,

    /**
     * 上报地理位置
     */
    LOCATION,

    /**
     * 自定义菜单点击事件
     */
    CLICK,

    /**
     * 点击菜单跳转链接时的事件推送
     */
    VIEW,
    /**
     * 扫码推事件的事件推送
     */
    SCANCODE_PUSH,

    /**
     * 扫码推事件且弹出“消息接收中”提示框的事件推送
     */
    SCANCODE_WAITMSG,

    /**
     * 弹出系统拍照发图的事件推送
     */
    PIC_SYSPHOTO,

    /**
     * .弹出拍照或者相册发图的事件推送(与上一个字段应都属于同一个类管理)
     */
    PIC_PHOTO_OR_ALBUM
}
