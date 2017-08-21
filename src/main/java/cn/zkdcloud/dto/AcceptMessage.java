package cn.zkdcloud.dto;

import cn.zkdcloud.entity.MsgType;
import cn.zkdcloud.util.StreamUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zk
 * @version 2017/8/20
 */
public abstract class AcceptMessage extends Message{

    public static AcceptMessage prepareMessage(HttpServletRequest request){
        AcceptMessage ret;
        Map<String,String> data = StreamUtil.xmlToMap(request);
        if(data.get("MsgType").equalsIgnoreCase(MsgType.EVENT.toString())){

        } else {

        }
    }
}
