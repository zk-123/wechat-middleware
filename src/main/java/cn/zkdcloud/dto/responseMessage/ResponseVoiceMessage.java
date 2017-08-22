package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.entity.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("xml")
public class ResponseVoiceMessage extends ResponseMessage{

    @XStreamAlias("Voice")
    private Voice voice;

    public ResponseVoiceMessage(){

    }

    public ResponseVoiceMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        this.msgType = MsgType.VOICE;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public static class Voice{
        @XStreamAlias("MediaId")
        private String mediaId;

        public static Voice getVoice(String mediaId){
            Voice ret = new Voice();
            ret.mediaId = mediaId;
            return ret;
        }
    }
}
