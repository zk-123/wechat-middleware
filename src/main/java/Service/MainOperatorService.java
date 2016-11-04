package Service;

import Entity.BaseMessage;
import Entity.TextMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zk on 2016/10/30.
 */
@Service("mainOperatorService")
public class MainOperatorService {

    public TextMessage baseToText(BaseMessage baseMessage)
    {
        TextMessage textMessage = new TextMessage(baseMessage);
        return textMessage;
    }

    public BaseMessage mapToBase(Map<String,String> map)
    {
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setFromUserName(map.get("ToUserName"));
        baseMessage.setToUserName(map.get("FromUserName"));
        baseMessage.setMsgType(map.get("MsgType"));
        baseMessage.setCreateTime(System.currentTimeMillis());
        return baseMessage;
    }
}
