package cn.zkdcloud.entity;

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
    UBSUBSCRIBE,

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
    VIEW
}
