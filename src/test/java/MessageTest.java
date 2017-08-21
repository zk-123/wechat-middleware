import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.dto.acceptMessage.normalMessage.AcceptTextMessage;
import cn.zkdcloud.dto.responseMessage.ResponseImageMessage;
import cn.zkdcloud.entity.Event;
import cn.zkdcloud.handler.MessageAdapter;
import cn.zkdcloud.util.StreamUtil;
import cn.zkdcloud.util.WeChatUtil;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class MessageTest {
    public static void main(String[] args){
        ResponseImageMessage message = new ResponseImageMessage("abc","123");
        message.setImage(ResponseImageMessage.Image.getImage("http://www.baidu.com"));
        System.out.println(StreamUtil.ObjToXml(message));
    }

    @Test
    public void eventEnum(){
        Event event = Event.valueOf("SCaN".toUpperCase());
        System.out.println(event);
        System.out.println(event.toString());
    }

    @Test
    public void methodTest(){
        Class clazz = MessageAdapter.class;
        for(Method method : clazz.getDeclaredMethods()){
            for(Parameter p : method.getParameters()){
                if(p.getType() == AcceptMessage.class){
                    System.out.println(AcceptMessage.class);
                }
                if(p.getType() == )

            }
        }
    }
}
