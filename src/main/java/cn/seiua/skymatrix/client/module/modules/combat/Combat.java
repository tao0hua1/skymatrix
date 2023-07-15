package cn.seiua.skymatrix.client.module.modules.combat;

import cn.seiua.skymatrix.client.component.Category;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.SingleChoice;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientPacketEvent;

import java.util.ArrayList;


@Category(name = "combat")
@Event(register = true)
public class Combat {
    @Value(name = "test")
    public SingleChoice<String> test = new SingleChoice<>(new ArrayList<>());

    //    playpong
//    keepalive
    @EventTarget
    public void test(ClientPacketEvent e) {

    }

    @Init
    public void init() {

    }


}
