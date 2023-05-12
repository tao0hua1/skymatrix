package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.KeyBindManger;
import cn.seiua.skymatrix.client.Run;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;


public class KeyBind {

    private transient String name;

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    @JSONField(alternateNames = "keys")
    private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
    }

    private transient boolean run1;

    private transient Run run;

    public KeyBind(String name, List<Integer> keys) {
        this.name = name;
        this.keys = keys;
    }

    public KeyBind(String name, List<Integer> keys, Run run) {
        this.name = name;
        this.keys = keys;
        this.run = run;
        run1=true;
    }

    public static int getMouseKey(int k){
        return k;
    }
    public static int getKeyboardKey(int k){
        return k+ KeyBindManger.MOUSE;
    }

}
