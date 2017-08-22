package cn.zkdcloud.process;

import cn.zkdcloud.core.MessageAdapter;
import cn.zkdcloud.dto.ResponseMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.AcceptTextMessage;
import cn.zkdcloud.dto.responseMessage.ResponseTextMessage;
import cn.zkdcloud.util.StreamUtil;

public class TestMessage extends MessageAdapter{
    public TestMessage(Class<? extends MessageAdapter> clazz) {
        super(clazz);
    }

    public ResponseMessage textMessage(AcceptTextMessage message){
        System.out.println(StreamUtil.ObjToXml(message));
        ResponseTextMessage text = new ResponseTextMessage();
        text.setContent("你好");
        return text;
    }
}
