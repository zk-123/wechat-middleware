package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.entity.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 返回图片格式Message
 */
@XStreamAlias("xml")
public class ResponseMusicMessage extends ResponseMessage {

    @XStreamAlias("Music")
    private Music music;

    public ResponseMusicMessage() {

    }

    public ResponseMusicMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        this.msgType = MsgType.MUSIC;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public static class Music {
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
        /**
         * music url
         */
        @XStreamAlias("MusicUrl")
        private String musicUrl;
        /**
         * 高清 music url(选填)
         */
        @XStreamAlias("HQMusicUrl")
        private String hqMusicUrl;
        /**
         * 缩略图的媒体id
         */
        @XStreamAlias("ThumbMediaId")
        private String thumbMediaId;

        public static Music getMusic(String title, String description, String musicUrl,
                                     String hqMusicUrl, String thumbMediaId) {
            Music ret = new Music();
            ret.title = title;
            ret.description = description;
            ret.musicUrl = musicUrl;
            ret.hqMusicUrl = hqMusicUrl;
            ret.thumbMediaId = thumbMediaId;
            return ret;
        }
    }
}
