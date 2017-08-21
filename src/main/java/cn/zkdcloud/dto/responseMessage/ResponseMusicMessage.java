package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("xml")
public class ResponseMusicMessage extends ResponseMessage{

    @XStreamImplicit
    private Music music;

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    @XStreamAlias("Music")
    static class Music{
        /*
        title
         */
        @XStreamAlias("Title")
        private String title;
        /*
        description
         */
        @XStreamAlias("Description")
        private String description;
        /*
        music url
         */
        @XStreamAlias("MusicUrl")
        private String musicUrl;
        /*
        高清 music url(选填)
         */
        @XStreamAlias("HQMusicUrl")
        private String hqMusicUrl;
        /*
        缩略图的媒体id
         */
        @XStreamAlias("ThumbMediaId")
        private String thumbMediaId;

        public static Music getMusic(String title,String description,String musicUrl,
                                     String hqMusicUrl,String thumbMediaId){
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
