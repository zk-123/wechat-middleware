package cn.zkdcloud.dto.acceptMessage.normalMessage;

import cn.zkdcloud.dto.acceptMessage.AcceptNormalMessage;

/**
 * 地理位置消息
 */
public class AcceptLocationMessage extends AcceptNormalMessage {
    /**
     * 维度
     */
    private String location_x;
    /**
     * 经度
     */
    private String location_y;
    /**
     * 地图缩放大小
     */
    private Integer scale;
    /**
     * 地理位置消息
     */
    private String label;

    public String getLocation_x() {
        return location_x;
    }

    public void setLocation_x(String location_x) {
        this.location_x = location_x;
    }

    public String getLocation_y() {
        return location_y;
    }

    public void setLocation_y(String location_y) {
        this.location_y = location_y;
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
