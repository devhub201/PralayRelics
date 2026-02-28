package me.pralay.relics.utils;

import me.pralay.relics.RelicsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class RelicItems {

    private static final NamespacedKey ARMOR_RELIC_KEY = new NamespacedKey(RelicsPlugin.getInstance(), "armor_relic");
    private static final NamespacedKey TOTEM_RELIC_KEY = new NamespacedKey(RelicsPlugin.getInstance(), "totem_relic");
    private static final NamespacedKey MACE_RELIC_KEY = new NamespacedKey(RelicsPlugin.getInstance(), "mace_relic");
    private static final NamespacedKey RELIC_ARMOR_KEY = new NamespacedKey(RelicsPlugin.getInstance(), "relic_armor");

    public static ItemStack createArmorRelic() {
        ItemStack item = new ItemStack(Material.PURPLE_SHULKER_BOX);
        ItemMeta meta = item.getItemMeta();
        
        meta.displayName(Component.text("§5Ancient Armor Relic").decoration(TextDecoration.ITALIC, false));
        
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("§7Right-click to receive the Relic Armor Set").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("§8One-time use item").decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        
        meta.getPersistentDataContainer().set(ARMOR_RELIC_KEY, PersistentDataType.BYTE, (byte) 1);
        
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createTotemRelic() {
        ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = item.getItemMeta();
        
        meta.displayName(Component.text("§6Relic Totem").decoration(TextDecoration.ITALIC, false));
        
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("§7Grants extra power when activated").decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        
        meta.getPersistentDataContainer().set(TOTEM_RELIC_KEY, PersistentDataType.BYTE, (byte) 1);
        
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createMaceRelic() {
        ItemStack item = new ItemStack(Material.MACE);
        ItemMeta meta = item.getItemMeta();
        
        meta.displayName(Component.text("§cRelic Mace").decoration(TextDecoration.ITALIC, false));
        
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("§7Smite your enemies with power").decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        
        meta.addEnchant(Enchantment.SHARPNESS, 5, true);
        meta.addEnchant(Enchantment.UNBREAKING, 3, true);
        
        meta.getPersistentDataContainer().set(MACE_RELIC_KEY, PersistentDataType.BYTE, (byte) 1);
        
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createRelicArmor(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        
        meta.displayName(Component.text("§d" + name).decoration(TextDecoration.ITALIC, false));
        
        meta.addEnchant(Enchantment.PROTECTION, 4, true);
        meta.addEnchant(Enchantment.UNBREAKING, 3, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        
        meta.getPersistentDataContainer().set(RELIC_ARMOR_KEY, PersistentDataType.BYTE, (byte) 1);
        
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isArmorRelic(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(ARMOR_RELIC_KEY, PersistentDataType.BYTE);
    }

    public static boolean isTotemRelic(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(TOTEM_RELIC_KEY, PersistentDataType.BYTE);
    }

    public static boolean isMaceRelic(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(MACE_RELIC_KEY, PersistentDataType.BYTE);
    }
}
