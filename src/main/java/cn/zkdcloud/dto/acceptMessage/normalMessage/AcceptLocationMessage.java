package cn.zkdcloud.dto.acceptMessage.normalMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;

/**
 * 地理位置消息
 */
public class AcceptLocationMessage extends AcceptNormalMessage {
    /**
     * 维度
     */
    private String location_X;
    /**
     * 经度
     */
    private String location_Y;
    /**
     * 地图缩放大小
     */
    private Integer scale;
    /**
     * 地理位置消息
     */
    private String label;

    public String getLocation_X() {
        return location_X;
    }

    public void setLocation_X(String location_X) {
        this.location_X = location_X;
    }

    public String getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(String location_Y) {
        this.location_Y = location_Y;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
