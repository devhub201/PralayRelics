package me.pralay.relics.listeners;

import me.pralay.relics.RelicsPlugin;
import me.pralay.relics.utils.RelicItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorRelicListener implements Listener {

    private final RelicsPlugin plugin;

    public ArmorRelicListener(RelicsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArmorRelicUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !RelicItems.isArmorRelic(item)) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        event.setCancelled(true);

        // Create relic armor pieces
        ItemStack helmet = RelicItems.createRelicArmor(Material.NETHERITE_HELMET, "Relic Helmet");
        ItemStack chestplate = RelicItems.createRelicArmor(Material.NETHERITE_CHESTPLATE, "Relic Chestplate");
        ItemStack leggings = RelicItems.createRelicArmor(Material.NETHERITE_LEGGINGS, "Relic Leggings");
        ItemStack boots = RelicItems.createRelicArmor(Material.NETHERITE_BOOTS, "Relic Boots");

        // Give armor to player
        player.getInventory().addItem(helmet, chestplate, leggings, boots);

        // Play effects
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(0, 1, 0), 50, 0.5, 0.5, 0.5, 0.1);

        // Send message
        player.sendMessage(Component.text("You have received the Relic Armor Set!").color(NamedTextColor.LIGHT_PURPLE));

        // Remove the shulker box
        item.setAmount(item.getAmount() - 1);
    }
}

