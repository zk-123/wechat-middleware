package cn.zkdcloud.dto.responseMessage;

import cn.zkdcloud.dto.ResponseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本消息格式回复
 */
@XStreamAlias("xml")
public class ResponseTextMessage extends ResponseMessage{
    /*
    消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     */
    @XStreamAlias("Content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
