package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.entity.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 图片格式回复
 */
@XStreamAlias("xml")
public class ResponseImageMessage extends ResponseMessage {
    /*
    图片
     */
    @XStreamAlias("Image")
    private Image image;

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

    /*
    回复图片类
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
