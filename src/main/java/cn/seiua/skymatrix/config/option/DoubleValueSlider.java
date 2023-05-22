package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UIDoubleSlider;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class DoubleValueSlider<V extends Number> implements UIComponent {

    @JSONField(alternateNames = "value", serialzeFeatures = SerializerFeature.DisableCircularReferenceDetect)
    private V value;
    @JSONField(alternateNames = "valua", serialzeFeatures = SerializerFeature.DisableCircularReferenceDetect)
    private V valua;
    private transient V min;
    private transient V max;
    private transient V interval;

    public DoubleValueSlider(V value, V valua, V min, V max, V interval) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.interval = interval;
        this.valua = valua;
    }

    public V getValua() {
        return valua;
    }

    public void setValua(V valua) {
        this.valua = valua;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public V getMin() {
        return min;
    }

    public void setMin(V min) {
        this.min = min;
    }

    public V getMax() {
        return max;
    }

    public void setMax(V max) {
        this.max = max;
    }

    public V getInterval() {
        return interval;
    }

    public void setInterval(V interval) {
        this.interval = interval;
    }

    @Override
    public String getID() {
        return "DoubleValueSlider";
    }

    @Override
    public UI build(String module, String category, String name, Signs sign) {

        OptionInfo<DoubleValueSlider> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UIDoubleSlider slider = new UIDoubleSlider(optionInfo);
        return slider;
    }

}
