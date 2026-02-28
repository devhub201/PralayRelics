package me.pralay.relics.listeners;

import me.pralay.relics.RelicsPlugin;
import me.pralay.relics.utils.RelicItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TotemRelicListener implements Listener {

    private final RelicsPlugin plugin;

    public TotemRelicListener(RelicsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTotemUse(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        ItemStack totemItem = null;
        boolean isMainHand = false;

        if (RelicItems.isTotemRelic(mainHand)) {
            totemItem = mainHand;
            isMainHand = true;
        } else if (RelicItems.isTotemRelic(offHand)) {
            totemItem = offHand;
            isMainHand = false;
        }

        if (totemItem == null) {
            return;
        }

        // Allow the resurrection
        event.setCancelled(false);

        // Schedule enhanced effects after the vanilla totem effect
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            // Set health to 6 hearts (12.0 health points)
            player.setHealth(Math.min(12.0, player.getMaxHealth()));

            // Apply enhanced effects
            player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 1)); // 5 seconds, level II
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 1)); // 8 seconds, level II

            // Send message
            player.sendMessage(Component.text("The Relic Totem has saved you with enhanced power!").color(NamedTextColor.GOLD));

            // Remove the relic totem
            if (isMainHand) {
                player.getInventory().setItemInMainHand(new ItemStack(org.bukkit.Material.AIR));
            } else {
                player.getInventory().setItemInOffHand(new ItemStack(org.bukkit.Material.AIR));
            }
        }, 1L);
    }
}
