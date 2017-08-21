package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("xml")
public class ResponseVoiceMessage extends ResponseMessage{

    @XStreamImplicit
    private Voice voice;

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    @XStreamAlias("Voice")
    static class Voice{
        @XStreamAlias("MediaId")
        private String mediaId;

        public static Voice getVoice(String mediaId){
            Voice ret = new Voice();
            ret.mediaId = mediaId;
            return ret;
        }
    }
}
