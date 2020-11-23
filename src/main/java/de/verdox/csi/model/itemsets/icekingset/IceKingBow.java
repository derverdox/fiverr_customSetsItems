package de.verdox.csi.model.itemsets.icekingset;

import de.verdox.csi.Core;
import de.verdox.csi.interfaces.CustomArrowCallback;
import de.verdox.csi.model.Combo;
import de.verdox.csi.model.CustomArrow;
import de.verdox.csi.model.CustomBow;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IceKingBow extends CustomBow {
    private HashMap<Projectile, BukkitTask> shot = new HashMap<>();
    public IceKingBow(Configuration config) {
        super(config);
    }

    @Override
    public void onCustomArrowHit(EntityDamageByEntityEvent e) {

    }

    @Override
    public void onComboTriggered(Player player) {

    }

    @Override
    public List<Combo.Click> combo() {
        return null;
    }

    @Override
    public void onHit(ProjectileHitEvent e) {
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        if(!(projectile instanceof Arrow))
            return;
        Arrow arrow = (Arrow) projectile;
        CustomArrow.toCustomArrow((Player) e.getEntity().getShooter(), this,0, arrow, Effect.POTION_BREAK, 2, customArrow -> { });
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
        return ItemUtil.createStack(Core.core, Material.BOW.name(), 1,(short) 0,"&bIce King Bow","&cVery powerful bow&7!");
    }

    @Override
    public String identifier() {
        return "IceKing_Bow";
    }

    @Override
    protected Map<Integer, Enchantment> enchantments() {
        return null;
    }
}
