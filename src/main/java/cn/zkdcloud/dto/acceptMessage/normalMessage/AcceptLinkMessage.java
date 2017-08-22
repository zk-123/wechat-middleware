package cn.zkdcloud.dto.acceptMessage.normalMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;

/**
 * 链接消息
 */
public class AcceptLinkMessage extends AcceptNormalMessage {
    /**
     * 链接标题
     */
    private String title;
    /**
     * 链接描述
     */
    private String description;
    /**
     * 链接url
     */
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
