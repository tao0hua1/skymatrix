package cn.seiua.skymatrix.gui;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.ui.UI;

public interface UIComponent {
    UI build(String module, String category, String name, Signs sign);
}
