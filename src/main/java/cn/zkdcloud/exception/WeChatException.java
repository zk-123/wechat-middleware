package cn.zkdcloud.exception;

/**
 * 一般操作错误异常
 *
 * @author zk
 * @version 2017/9/4
 */
public class WeChatException extends Exception{
    public WeChatException(String msg){
        super(msg);
    }
}
