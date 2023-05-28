package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.utils.SkyBlockUtils;
import net.minecraft.item.ItemStack;

public interface Filter {


    static boolean rodFilter(ItemStack itemStack) {

        String id = SkyBlockUtils.getItemId(itemStack);
        if (id.contains("ROD")) return true;
        return false;
    }

    boolean filter(ItemStack itemStack);
}
