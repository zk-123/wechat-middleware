package cn.zkdcloud.component.menu;


import java.util.ArrayList;
import java.util.List;

/**
 * 所有按钮父类
 *
 * @author zk
 * @version 2017/8/25
 */
public abstract class AbstractButton {
    /**
     * 按钮类型
     */
    protected MenuType type;

    /**
     * 名字
     */
    protected String name;

    /**
     * 可能会有子按钮
     */
    protected List<AbstractButton> sub_button;

    /**
     * 添加子按钮
     *
     * @param abstractButton this(父按钮)
     */
    public AbstractButton addSubButton(AbstractButton abstractButton) {
        if (null == this.sub_button) {
            this.sub_button = new ArrayList<>();
        }

        getSub_button().add(abstractButton);
        return this;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbstractButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<AbstractButton> sub_button) {
        this.sub_button = sub_button;
    }
}
