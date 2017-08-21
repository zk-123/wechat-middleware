package cn.zkdcloud.dto.acceptMessage.eventMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;

public class ViewEventMessage extends AcceptEventMessage{
    /*
    跳转的url
     */
    private String viewKey;

    public String getViewKey() {
        return viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }
}
