package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.entity.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("xml")
public class ResponseVideoMessage extends ResponseMessage{

    @XStreamAlias("Video")
    private Video video;

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

    @XStreamAlias("video")
    public static class Video{
        @XStreamAlias("MediaId")
        private String mediaId;

        @XStreamAlias("Title")
        private String title;

        @XStreamAlias("Description")
        private String description;

        public static Video getVideo(String mediaId,String title,String description){
            Video ret = new Video();
            ret.title = title;
            ret.mediaId = mediaId;
            ret.description = description;
            return ret;
        }
    }
}
