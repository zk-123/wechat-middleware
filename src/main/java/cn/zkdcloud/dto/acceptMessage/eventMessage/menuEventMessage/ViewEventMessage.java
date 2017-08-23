package cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.MenuEventMessage;

/**
 * 链接点击事件
 */
public class ViewEventMessage extends MenuEventMessage {
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
