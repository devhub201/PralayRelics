package me.pralay.relics.commands;

import me.pralay.relics.RelicsPlugin;
import me.pralay.relics.utils.RelicItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelicCommand implements CommandExecutor, TabCompleter {

    private final RelicsPlugin plugin;

    public RelicCommand(RelicsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        if (!sender.hasPermission("relics.admin")) {
            sender.sendMessage(Component.text("You don't have permission to use this command!").color(NamedTextColor.RED));
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(Component.text("Usage: /relic give <armor|totem|mace> <player>").color(NamedTextColor.RED));
            return true;
        }

        if (!args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(Component.text("Unknown subcommand! Use: give").color(NamedTextColor.RED));
            return true;
        }

        String relicType = args[1].toLowerCase();
        Player target = Bukkit.getPlayer(args[2]);

        if (target == null) {
            sender.sendMessage(Component.text("Player not found!").color(NamedTextColor.RED));
            return true;
        }

        ItemStack relic;
        String relicName;

        switch (relicType) {
            case "armor":
                relic = RelicItems.createArmorRelic();
                relicName = "Ancient Armor Relic";
                break;
            case "totem":
                relic = RelicItems.createTotemRelic();
                relicName = "Relic Totem";
                break;
            case "mace":
                relic = RelicItems.createMaceRelic();
                relicName = "Relic Mace";
                break;
            default:
                sender.sendMessage(Component.text("Invalid relic type! Use: armor, totem, or mace").color(NamedTextColor.RED));
                return true;
        }

        target.getInventory().addItem(relic);
        
        sender.sendMessage(Component.text("Successfully gave " + relicName + " to " + target.getName()).color(NamedTextColor.GREEN));
        target.sendMessage(Component.text("You have received a " + relicName + "!").color(NamedTextColor.GOLD));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        if (!sender.hasPermission("relics.admin")) {
            return new ArrayList<>();
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("give");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            completions.addAll(Arrays.asList("armor", "totem", "mace"));
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            return null; // Return null to show online players
        }

        return completions;
    }
}
