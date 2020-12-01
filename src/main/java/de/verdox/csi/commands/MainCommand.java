package de.verdox.csi.commands;

import de.verdox.csi.model.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;

        if(!player.hasPermission("csi.giveItem")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cNo permission&7!"));
            return false;
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("give")){
                String customItemName = args[1];
                CustomItem customItem = CustomItem.findItem(customItemName);
                if(customItem == null){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cItem does not exist&7!"));
                    return false;
                }
                player.getInventory().setItem(player.getInventory().firstEmpty(),customItem.getItemStack());
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> suggest = new ArrayList<>();

        if(args.length >= 1){
            CustomItem.itemCache.forEach((s, customItem) -> suggest.add(customItem.identifier()));
            return suggest;
        }

        return suggest;
    }
}
