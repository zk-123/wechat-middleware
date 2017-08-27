package cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage;

import cn.zkdcloud.component.message.acceptMessage.eventMessage.AbstractMenuEventMessage;

/**
 * 链接点击事件
 */
public class ViewEventMessage extends AbstractMenuEventMessage {
    /**
     * 跳转的url
     */
    private String viewKey;

    public String getViewKey() {
        return viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }
}
