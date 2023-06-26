package cn.seiua.skymatrix.client.config;

import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.ToggleSwitch;
import cn.seiua.skymatrix.config.option.ValueInput;
import cn.seiua.skymatrix.gui.Icons;

@Event
@Sign(sign = Signs.PRO)
@SModule(name = "setting", category = "client")
public class Setting {
    @Value(name = "title")
    @Sign(sign = Signs.BETA)
    public ValueInput title = new ValueInput("Genshin Impact", Icons.LOCATION);
    @Value(name = "debug")
    @Sign(sign = Signs.BETA)
    public ToggleSwitch debug = new ToggleSwitch(false);

    private static Setting instance;


    @Init
    public void init() {
        instance = this;
    }

    public static Setting getInstance() {
        return instance;
    }

}
