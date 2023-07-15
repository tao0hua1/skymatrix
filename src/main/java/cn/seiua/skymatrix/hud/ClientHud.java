package cn.seiua.skymatrix.hud;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UIClientHudToggle;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class ClientHud implements Serializable, UIComponent {

    @JSONField
    public float x;
    @JSONField
    public float y;
    @JSONField
    public boolean enable;

    public transient Hud target;

    public ClientHud(int x, int y, boolean enable, Hud target) {
        this.x = x;
        this.y = y;
        this.enable = enable;
        this.target = target;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Hud getTarget() {
        return target;
    }

    public void setTarget(Hud target) {
        this.target = target;
    }

    @Override
    public UI build(String module, String category, String name, Signs sign) {

        OptionInfo<ClientHud> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UIClientHudToggle clientHudToggle = new UIClientHudToggle(optionInfo);
        return clientHudToggle;
    }
}
