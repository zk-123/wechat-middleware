package cn.zkdcloud.dto.acceptMessage.eventMessage.menuEventMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptEventMessage;
import cn.zkdcloud.dto.acceptMessage.eventMessage.MenuEventMessage;

import java.util.List;

/**
 * 照片消息
 */
public class PhotoEventMessage extends MenuEventMessage {
    /**
     * 照片信息
     */
    private SendPicsInfo sendPicsInfo;

    /**
     * 照片信息类
     */
    public static class SendPicsInfo {

        /**
         * 照片数量
         */
        private Integer count;

        /**
         * 照片列表
         */
        private List<Item> picList;

        public static class Item {
            /**
             * 图片的MD5值，开发者若需要，可用于验证接收到图片
             */
            private String PicMd5Sum;

            public String getPicMd5Sum() {
                return PicMd5Sum;
            }

            public void setPicMd5Sum(String picMd5Sum) {
                PicMd5Sum = picMd5Sum;
            }
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<Item> getPicList() {
            return picList;
        }

        public void setPicList(List<Item> picList) {
            this.picList = picList;
        }
    }

    public SendPicsInfo getSendPicsInfo() {
        return sendPicsInfo;
    }

    public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
        this.sendPicsInfo = sendPicsInfo;
    }
}
