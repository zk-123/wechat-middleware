package cn.zkdcloud.dto.acceptMessage.normalMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;

/**
 * 语音消息
 */
public class AcceptVoiceMessage extends AcceptNormalMessage {
    /**
     * mediaId
     */
    private String mediaId;
    /**
     * 语音格式
     */
    private String format;
    /**
     * 语音识别结果
     */
    private String recognition;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }
}
