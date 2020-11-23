package de.verdox.csi.model.custommelees;

import de.verdox.csi.Core;
import de.verdox.csi.interfaces.CustomArrowCallback;
import de.verdox.csi.model.Combo;
import de.verdox.csi.model.CustomArrow;
import de.verdox.csi.model.CustomMelee;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import de.verdox.vcore.utils.SecondsConverter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SwordOfRaijn extends CustomMelee {

    private Map<Player, Arrow> arrowCache = new HashMap<>();
    private Set<Player> cooldownCache = new HashSet<>();
    private long lastUse;

    private int cooldownInSeconds;

    public SwordOfRaijn(Configuration config) {
        super(config);
        if(!config.getConfig().isSet(configPath()+".cooldownInSeconds"))
            config.getConfig().set(configPath()+".cooldownInSeconds",60);
        this.cooldownInSeconds = config.getConfig().getInt(configPath()+".cooldownInSeconds");
    }

    @Override
    public void onComboTriggered(Player player) {
        if(arrowCache.containsKey(player)){
            arrowCache.get(player).remove();
            CustomArrow customArrow = CustomArrow.findByArrow(arrowCache.get(player));
            if(customArrow != null)
                customArrow.stopParticles();
            arrowCache.remove(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou have cancelled the teleportation&7!"));
        }
        else {
            if(cooldownCache.contains(player)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cItem is still on cooldown! &8["+ (cooldownInSeconds-(TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis()-lastUse)))) +"&8]"));
                return;
            }

            Arrow arrow = player.getLocation().getWorld().spawnArrow(player.getEyeLocation().add(0,1,0),player.getEyeLocation().getDirection(),1.5f,1);
            CustomArrow.toCustomArrow(player, null, 5, arrow, Effect.POTION_BREAK, 1, customArrow -> {
                arrowCache.remove(player);
                customArrow.getArrow().remove();
                player.teleport(customArrow.getArrow().getLocation().add(0,1,0));
            });

            arrowCache.put(player,arrow);
            cooldownCache.add(player);
            lastUse = System.currentTimeMillis();

            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.core,() -> {
                arrowCache.remove(player);
                cooldownCache.remove(player);
            },20L*cooldownInSeconds);
        }
    }

    private void cancelTeleportation(){}

    @Override
    public List<Combo.Click> combo() {
        return Arrays.asList(Combo.Click.Left, Combo.Click.Left, Combo.Click.Right);
    }

    @Override
    public void onEquip(Player player) {

    }

    @Override
    public void onUnEquip(Player player) {

    }

    @Override
    public boolean onPlayerDeath(Player player) {
        return false;
    }

    @Override
    protected ItemStack customItem() {
        return ItemUtil.createStack(Core.core,Material.DIAMOND_SWORD.name(), 1,(short) 0,"&eSword of Raijn","&cVery powerful sword&7!");
    }

    @Override
    public String identifier() {
        return "Sword_Of_Raijn";
    }

    @Override
    protected Map<Integer, Enchantment> enchantments() {
        Map<Integer,Enchantment> enchantments = new HashMap<>();
        enchantments.put(6,Enchantment.DAMAGE_ALL);
        enchantments.put(5,Enchantment.DURABILITY);
        return enchantments;
    }
}
