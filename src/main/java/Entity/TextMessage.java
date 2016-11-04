package Entity;

/**
 * Created by zk on 2016/10/30.
 */
public class TextMessage extends BaseMessage{

    /**
     * 文本消息内容
     */

    private String Content;

    public TextMessage(BaseMessage baseMessage){
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
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }



}
