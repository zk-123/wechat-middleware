package cn.zkdcloud.component.message.acceptMessage.eventMessage.menuEventMessage;

import cn.zkdcloud.component.message.acceptMessage.eventMessage.AbstractMenuEventMessage;

/**
 * 自定义菜单发起的扫描事件,目前只知道(扫码推事件的事件推送，扫码推事件且弹出“消息接收中”提示框的事件推送)
 */
public class MenuScanEventMessage extends AbstractMenuEventMessage {


    private ScanCodeInfo scanCodeInfo;

    /**
     * 扫描信息
     */
    public static class ScanCodeInfo {
        /**
         * 二维码类型
         */
        private String scanType;
        /**
         * 扫描结果
         */
        private String scanResult;

        public String getScanType() {
            return scanType;
        }

        public void setScanType(String scanType) {
            this.scanType = scanType;
        }

        public String getScanResult() {
            return scanResult;
        }

        public void setScanResult(String scanResult) {
            this.scanResult = scanResult;
        }
    }

    public ScanCodeInfo getScanCodeInfo() {
        return scanCodeInfo;
    }

    public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
        this.scanCodeInfo = scanCodeInfo;
    }
}
