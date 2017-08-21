package cn.zkdcloud.handler;

import cn.zkdcloud.dto.AcceptMessage;
import cn.zkdcloud.entity.Event;
import cn.zkdcloud.entity.MsgType;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * message适配器
 */
public abstract class MessageAdapter{

    private static Logger logger = Logger.getLogger(MessageAdapter.class);

    /**
     * adapterMap
     */
    private static Map<Class,Map<Method,MsgEvent>> map;

    public MessageAdapter(Class<? extends MessageAdapter> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods){
            Parameter[] parameters = method.getParameters();
            for(Parameter parameter : parameters){
                if(parameter.getType() == MsgType.class){

                }
            }
        }
    }

    public void init(Class<? extends MessageAdapter> clazz){

    }

    public String doAdapter(AcceptMessage acceptMessage){
        return "";
    }

    /**
     * 单实例模式获取该map（可能也是多余了，因为它的继承类之间的加载本来就是异步的，所以不需要）
     */
    private static class AdaperMap{
        private Map getAdaperMap(){
            if(map == null){
                map = new HashMap<>();
            }
            return map;
        }
    }
}
