package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UITargetSelect;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class TargetSelect implements Serializable, UIComponent {


    @JSONField(alternateNames = "player")
    private boolean player;
    @JSONField(alternateNames = "mob")
    private boolean mob;
    @JSONField(alternateNames = "invisible")
    private boolean invisible;
    @JSONField(alternateNames = "naked")
    private boolean naked;
    @JSONField(alternateNames = "friendly")
    private boolean friendly;

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public boolean isMob() {
        return mob;
    }

    public void setMob(boolean mob) {
        this.mob = mob;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isNaked() {
        return naked;
    }

    public void setNaked(boolean naked) {
        this.naked = naked;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public boolean canBeTarget(Entity entity) {
        if (this.mob) {
            if (entity instanceof MobEntity) {
                return true;
            }
        }
        if (this.player) {
            if (entity instanceof PlayerEntity) {
                return true;
            }
        }
        if (this.friendly) {
            if (entity instanceof MobEntity) {
                return true;
            }
        }


        return false;
    }


    @Override
    public UI build(String module, String category, String name, Signs sign) {
        OptionInfo<TargetSelect> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UITargetSelect uiTargetSelect = new UITargetSelect(optionInfo);
        return uiTargetSelect;
    }


}
