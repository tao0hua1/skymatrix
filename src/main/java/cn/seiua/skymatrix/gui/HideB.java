package cn.seiua.skymatrix.gui;

import cn.seiua.skymatrix.config.IHide;

public class HideB {
    private String value;
    private IHide hide;

    public HideB(String value, IHide hide) {
        this.value = value;
        this.hide = hide;
    }

    public boolean canRender() {
        boolean flag = false;
        for (String a : value.split("&")) {
            if (this.hide != null) {
                flag = flag || hide.canRender(a.trim());
            }

        }
        return flag;
    }
}
