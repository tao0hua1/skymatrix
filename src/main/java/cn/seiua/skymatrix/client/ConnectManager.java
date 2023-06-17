package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Config;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.ToggleSwitch;

import java.io.Serializable;

@Component
@Config(name = "NMSL")
public class ConnectManager implements Serializable {

    @Value(name = "toggle")
    public ToggleSwitch toggle = new ToggleSwitch(false);


    @Init
    public void handle() {

    }
}
