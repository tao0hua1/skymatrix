package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.utils.SkyBlockUtils;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public interface Filter {


    static boolean rodFilter(ItemStack itemStack) {

        String id = SkyBlockUtils.getItemId(itemStack);
        if (id.contains("ROD")) return true;
        return false;
    }

    boolean filter(ItemStack itemStack);

    static boolean weaponFilter(ItemStack itemStack) {
        String id = SkyBlockUtils.getItemId(itemStack);

        if (!Objects.equals(id, "none")) {
            String type = SkyBlockUtils.getItemType(itemStack);
            if (type != null) {
                if (type.equals("BOW") || type.equals("SWORD") || type.equals("AXE")) {
                    return true;
                }
            }
        }

        return false;
    }
}
