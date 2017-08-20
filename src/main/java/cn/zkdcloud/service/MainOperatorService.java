package cn.zkdcloud.service;

import cn.zkdcloud.dto.Message;
import cn.zkdcloud.dto.NewsMessage;
import cn.zkdcloud.dto.acceptMessage.TextMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zk on 2016/10/30.
 */
@Service("mainOperatorService")
public class MainOperatorService {

    public TextMessage baseToText(Message baseMessage)
    {
        return new TextMessage(baseMessage);
    }
    public NewsMessage baseToNews(Message baseMessage)
    {
        return new NewsMessage(baseMessage);
    }
    public Message mapToBase(Map<String,String> map)
    {
        Message baseMessage = new Message();
        baseMessage.setFromUserName(map.get("ToUserName"));
        baseMessage.setToUserName(map.get("FromUserName"));
        baseMessage.setMsgType(map.get("MsgType"));
        baseMessage.setCreateTime(System.currentTimeMillis());

        if(map.containsKey("Event")) baseMessage.setEvent(map.get("Event"));
        else baseMessage.setEvent("");

        if(map.containsKey("EventKey")) baseMessage.setEventKey(map.get("EventKey"));
        else baseMessage.setEventKey("");

        return baseMessage;
    }
}
