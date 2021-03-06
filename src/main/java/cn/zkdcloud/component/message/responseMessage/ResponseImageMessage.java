package cn.zkdcloud.component.message.responseMessage;

import cn.zkdcloud.component.message.AbstractResponseMessage;
import cn.zkdcloud.component.message.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 图片格式回复
 */
@XStreamAlias("xml")
public class ResponseImageMessage extends AbstractResponseMessage {
    /**
     * 图片
     */
    @XStreamAlias("Image")
    private Image image;

    public ResponseImageMessage() {
        super();
        this.msgType = MsgType.IMAGE;
    }

    public ResponseImageMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        this.msgType = MsgType.IMAGE;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * 回复图片类
     */
    public static class Image {

        @XStreamAlias("MediaId")
        private String mediaId;

        public static Image getImage(String mediaId) {
            Image ret = new Image();
            ret.mediaId = mediaId;
            return ret;
        }
    }
}
