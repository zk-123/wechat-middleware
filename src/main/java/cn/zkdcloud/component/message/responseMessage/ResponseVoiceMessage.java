package cn.zkdcloud.component.message.responseMessage;

import cn.zkdcloud.component.message.AbstractResponseMessage;
import cn.zkdcloud.component.message.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class ResponseVoiceMessage extends AbstractResponseMessage {

    @XStreamAlias("Voice")
    private Voice voice;

    public ResponseVoiceMessage() {
        super();
        this.msgType = MsgType.VOICE;
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

    public static class Voice {
        @XStreamAlias("MediaId")
        private String mediaId;

        public static Voice getVoice(String mediaId) {
            Voice ret = new Voice();
            ret.mediaId = mediaId;
            return ret;
        }
    }
}
