package cn.zkdcloud.dto.acceptMessage.normalMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;

/**
 * 小视频消息
 */
public class AcceptShotVideoMessage extends AcceptNormalMessage {
    /*
    mediaId
     */
    private String mediaId;
    /*
    视频消息缩略图的媒体id
     */
    private String thumMediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumMediaId() {
        return thumMediaId;
    }

    public void setThumMediaId(String thumMediaId) {
        this.thumMediaId = thumMediaId;
    }
}
