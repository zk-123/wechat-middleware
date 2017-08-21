package cn.zkdcloud.dto.acceptMessage.normalMessage;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.Message;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本消息
 * Created by zk on 2016/10/30.
 */
public class AcceptTextMessage extends AcceptMessage {

    /**
     * 文本消息内容
     */
    private String content;

    public AcceptTextMessage(){
        this.content = "success";
    }

    public String getContent() {
        return content;
    }
}
