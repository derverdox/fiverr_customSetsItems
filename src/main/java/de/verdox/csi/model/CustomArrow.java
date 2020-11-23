package de.verdox.csi.model;

import de.verdox.csi.Core;
import de.verdox.csi.interfaces.CustomArrowCallback;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomArrow {

    public static Map<Arrow, CustomArrow> cache = new HashMap<>();

    private BukkitTask particleSpawner;
    private Player shooter;
    private CustomBow customBow;
    private double damage;
    private Arrow arrow;
    private Effect effect;
    private int type;
    private CustomArrowCallback customArrowCallback;
    private List<PotionEffect> potionEffects;

    CustomArrow(Player shooter, CustomBow customBow, double damage, Arrow arrow, Effect effect, int type, CustomArrowCallback customArrowCallback){
        this.shooter = shooter;
        this.customBow = customBow;
        this.damage = damage;
        this.arrow = arrow;
        this.effect = effect;
        this.type = type;
        this.customArrowCallback = customArrowCallback;
        cache.put(arrow,this);
        setParticleSpawner();
    }

    public void setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    private void setParticleSpawner(){
        if(this.effect == null || this.type == -1)
            return;
        this.particleSpawner = Bukkit.getScheduler().runTaskTimerAsynchronously(Core.core,() -> {
            this.arrow.getLocation().getWorld().playEffect(this.arrow.getLocation(),this.effect,type);
        },0L,1L);
    }

    public double getDamage() {
        return damage;
    }

    public void callback(){
        this.customArrowCallback.callback(this);
    }

    public void stopParticles(){
        if(this.particleSpawner != null)
        this.particleSpawner.cancel();
    }

    public Arrow getArrow() {
        return arrow;
    }

    public CustomBow getCustomBow() {
        return customBow;
    }

    public static CustomArrow toCustomArrow(Player shooter, CustomBow customBow, double damage, Arrow arrow, Effect effect, int type, CustomArrowCallback customArrowCallback){
        return new CustomArrow(shooter, customBow, damage, arrow,effect,type, customArrowCallback);
    }

    public static CustomArrow findByArrow(Arrow arrow){
        return cache.get(arrow);
    }
}
