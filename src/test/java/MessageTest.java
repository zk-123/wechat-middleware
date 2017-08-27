import cn.zkdcloud.component.message.AbstractResponseMessage;
import cn.zkdcloud.component.message.Message;
import cn.zkdcloud.component.message.acceptMessage.normalMessage.AcceptTextMessage;
import cn.zkdcloud.component.message.responseMessage.ResponseImageMessage;
import cn.zkdcloud.component.message.acceptMessage.Event;
import cn.zkdcloud.component.message.MsgType;
import cn.zkdcloud.component.message.responseMessage.ResponseTextMessage;
import cn.zkdcloud.core.MessageComponent;
import cn.zkdcloud.util.StreamUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MessageTest {
    public static void main(String[] args) {
        ResponseImageMessage message = new ResponseImageMessage("abc", "123");
        message.setImage(ResponseImageMessage.Image.getImage("http://www.baidu.com"));
        System.out.println(StreamUtil.ObjToXml(message));
    }

    @Test
    public void eventEnum() {
        Event event = Event.valueOf("SCaN".toUpperCase());
        System.out.println(event);
        System.out.println(event.toString());
    }

    @Test
    public void methodTest() {
        Class clazz = MessageComponent.class;
        for (Method method : clazz.getDeclaredMethods()) {
            for (Parameter p : method.getParameters()) {
                if (Message.class.isAssignableFrom(p.getType())) {
                    System.out.println(Message.class);
                }
            }
        }
    }

    @Test
    public void xmlToJson() {
        AcceptTextMessage message = new AcceptTextMessage();
        message.setContent("你好");
        message.setFromUserName("abc");
        message.setToUserName("bbb");
        message.setMsgType(MsgType.TEXT);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(message));
        System.out.println(jsonObject.toJSONString());

        AcceptTextMessage message1 = jsonObject.toJavaObject(AcceptTextMessage.class);
        System.out.println(message1.getContent() + message1.getToUserName());

    }

    @Test
    public void primitive() {
        Class clazz = MsgType.class;
        System.out.println(MsgType.IMAGE.getClass());
        System.out.println(Integer.class == int.class);
    }

    @Test
    public void test(){
        ResponseTextMessage message = new ResponseTextMessage("abc");
        AbstractResponseMessage message1 = message;
        System.out.println(message1.getClass());
    }


}
