package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import com.alibaba.fastjson.annotation.JSONField;

public class ValueInput implements UIComponent {

    @JSONField(alternateNames = "value")
    private String value;

    public ValueInput(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public UI build(String module, String category, String name, Signs sign) {
        return null;
    }
}
