package cn.seiua.skymatrix.utils;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SkyBlockUtils {

    public static Map<String, Boolean> getAllSeaCreatureMap() {
        HashMap<String, Boolean> map = new HashMap<>();

        map.putAll(getWaterSeaCreatureMap());
        map.putAll(getEventSeaCreatureMap());
        map.putAll(getCrimsonSeaCreatureMap());
        map.putAll(getCrystalWaterSeaCreatureMap());
        map.putAll(getCrystalLavaSeaCreatureMap());


        return map;
    }

    public static Map<String, Boolean> getWaterSeaCreatureMap() {
        HashMap<String, Boolean> map = new HashMap<>();
        // water
        map.put("Squid", true);
        map.put("Sea Walker", true);
        map.put("Night Squid", true);
        map.put("Sea Guardian", true);
        map.put("Sea Witch", true);
        map.put("Sea Archer", true);
        map.put("Rider Of The Deep", true);
        map.put("Catfish", true);
        map.put("Carrot King", true);
        map.put("Sea Leech", true);
        map.put("Guardian Defender", true);
        map.put("Deep Sea Protector", true);
        map.put("Water Hydra", true);
        map.put("The Sea Emperor", true);
        return map;
    }

    public static Map<String, Boolean> getEventSeaCreatureMap() {
        HashMap<String, Boolean> map = new HashMap<>();
        //event
        map.put("Scarecrow", true);
        map.put("Nightmare", true);
        map.put("Werewolf", true);
        map.put("Phantom Fisherman", true);
        map.put("Grim Reaper", true);
        map.put("Frozen Steve", true);
        map.put("Frosty The Snowman", true);
        map.put("Grinch", true);
        map.put("Yeti", true);
        map.put("Nutcracker", true);
        map.put("Reindrake", true);
        map.put("Nurse Shark", true);
        map.put("Blue Shark", true);
        map.put("Tiger Shark", true);
        map.put("Great White Shark", true);
        return map;
    }

    public static Map<String, Boolean> getCrimsonSeaCreatureMap() {
        HashMap<String, Boolean> map = new HashMap<>();
        //crimson
        map.put("Plhlegblast", true);
        map.put("Magma Slug", true);
        map.put("Moogma", true);
        map.put("Lava Leech", true);
        map.put("Pyroclastic Worm", true);
        map.put("Lava Flame", true);
        map.put("Fire Eel", true);
        map.put("Taurus", true);
        map.put("Thunder", true);
        map.put("Lord Jawbus", true);
        return map;
    }

    public static Map<String, Boolean> getCrystalWaterSeaCreatureMap() {
        HashMap<String, Boolean> map = new HashMap<>();

        //crystal water
        map.put("Water Worm", true);
        map.put("Poisoned Water Worm", true);
        map.put("Zombie Miner", true);
        return map;
    }

    public static Map<String, Boolean> getCrystalLavaSeaCreatureMap() {
        HashMap<String, Boolean> map = new HashMap<>();
        //crystal lava
        map.put("Flaming Worm", true);
        map.put("Lava Blaze", true);
        map.put("Lava Pigman", true);

        return map;
    }

    public static String getItemUuid(ItemStack itemStack) {
        return get(itemStack, "uuid");
    }

    public static String getItemRarity(ItemStack itemStack) {
        return get(itemStack, "rarity_upgrades");
    }

    public static String getItemId(ItemStack itemStack) {
        return get(itemStack, "id");
    }

    public static String getItemModifier(ItemStack itemStack) {

        return get(itemStack, "modifier");
    }

    private static String get(ItemStack itemStack, String key) {
        if (itemStack.getNbt() == null) return "none";
        if (itemStack.getNbt().getCompound("ExtraAttributes") == null) return "none";
        if (itemStack.getNbt().getCompound("ExtraAttributes").get(key) == null) return "none";
        return Objects.requireNonNull(itemStack.getNbt().getCompound("ExtraAttributes").get(key)).asString();
    }

    public static String getRodType(ItemStack itemStack) {
        if (itemStack.getNbt() == null) return null;
        if (itemStack.getNbt().toString().contains("Lava Rod")) {
            return "LAVA";

        } else {
            return "WATER";
        }
    }


}
