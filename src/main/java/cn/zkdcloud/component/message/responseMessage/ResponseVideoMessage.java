package cn.zkdcloud.component.message.responseMessage;

import cn.zkdcloud.component.message.AbstractResponseMessage;
import cn.zkdcloud.component.message.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class ResponseVideoMessage extends AbstractResponseMessage {
    /**
     * video
     */
    @XStreamAlias("Video")
    private Video video;

    public ResponseVideoMessage() {
        super();
        this.msgType = MsgType.Video;
    }

    public ResponseVideoMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        this.msgType = MsgType.Video;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public static class Video {
        /**
         * mediaId
         */
        @XStreamAlias("MediaId")
        private String mediaId;
        /**
         * title
         */
        @XStreamAlias("Title")
        private String title;
        /**
         * description
         */
        @XStreamAlias("Description")
        private String description;

        public static Video getVideo(String mediaId, String title, String description) {
            Video ret = new Video();
            ret.title = title;
            ret.mediaId = mediaId;
            ret.description = description;
            return ret;
        }
    }
}
