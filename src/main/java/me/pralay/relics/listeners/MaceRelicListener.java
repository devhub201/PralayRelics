package me.pralay.relics.listeners;

import me.pralay.relics.RelicsPlugin;
import me.pralay.relics.utils.RelicItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MaceRelicListener implements Listener {

    private final RelicsPlugin plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 10000; // 10 seconds in milliseconds
    private static final double LIGHTNING_CHANCE = 0.20; // 20%

    public MaceRelicListener(RelicsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMaceHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) {
            return;
        }

        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        ItemStack weapon = attacker.getInventory().getItemInMainHand();

        if (!RelicItems.isMaceRelic(weapon)) {
            return;
        }

        UUID attackerUUID = attacker.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Check cooldown
        if (cooldowns.containsKey(attackerUUID)) {
            long timeLeft = cooldowns.get(attackerUUID) - currentTime;
            if (timeLeft > 0) {
                long secondsLeft = timeLeft / 1000;
                attacker.sendActionBar(Component.text("Relic Mace on cooldown: " + secondsLeft + "s").color(NamedTextColor.RED));
                return;
            }
        }

        // 20% chance to trigger
        if (ThreadLocalRandom.current().nextDouble() < LIGHTNING_CHANCE) {
            // Strike lightning (visual only)
            victim.getWorld().strikeLightningEffect(victim.getLocation());

            // Apply slowness
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 1)); // 4 seconds, level II

            // Play sound
            attacker.playSound(attacker.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
            victim.playSound(victim.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);

            // Send messages
            attacker.sendActionBar(Component.text("⚡ Relic Mace activated! ⚡").color(NamedTextColor.YELLOW));
            victim.sendMessage(Component.text("You were struck by a Relic Mace!").color(NamedTextColor.RED));

            // Set cooldown
            cooldowns.put(attackerUUID, currentTime + COOLDOWN_TIME);

            // Schedule cooldown removal
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                cooldowns.remove(attackerUUID);
                attacker.sendActionBar(Component.text("Relic Mace ready!").color(NamedTextColor.GREEN));
            }, 200L); // 10 seconds = 200 ticks
        }
    }
}
