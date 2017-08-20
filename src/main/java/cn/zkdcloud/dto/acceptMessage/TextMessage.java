package cn.zkdcloud.dto.acceptMessage;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.Message;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zk on 2016/10/30.
 */
@XStreamAlias("xml")
public class TextMessage extends AcceptMessage {

    /**
     * 文本消息内容
     */
    @XStreamAlias("Content")
    private String content;

    public TextMessage(Message baseMessage){
        this.setToUserName(baseMessage.getToUserName());
        this.setFromUserName(baseMessage.getFromUserName());
        this.setCreateTime(baseMessage.getCreateTime());
        this.setMsgType(baseMessage.getMsgType());
        this.setContent("success");
    }
    public TextMessage(){
        this.setContent("success");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
