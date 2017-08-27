package cn.zkdcloud.component.message.responseMessage;

import cn.zkdcloud.component.message.AbstractResponseMessage;
import cn.zkdcloud.component.message.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 返回图片格式Message
 */
@XStreamAlias("xml")
public class ResponseMusicMessage extends AbstractResponseMessage {

    @XStreamAlias("Music")
    private Music music;

    public ResponseMusicMessage() {
        super();
        this.msgType = MsgType.MUSIC;
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
