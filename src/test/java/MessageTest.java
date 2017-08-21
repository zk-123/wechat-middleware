import cn.zkdcloud.dto.responseMessage.ResponseImageMessage;
import cn.zkdcloud.util.StreamUtil;
import cn.zkdcloud.util.WeChatUtil;

public class MessageTest {
    public static void main(String[] args){
        ResponseImageMessage message = new ResponseImageMessage("abc","123");
        message.setImage(ResponseImageMessage.Image.getImage("http://www.baidu.com"));
        System.out.println(StreamUtil.ObjToXml(message));
    }
}
