package de.verdox.csi;

import de.verdox.csi.commands.MainCommand;
import de.verdox.csi.files.MainConfig;
import de.verdox.csi.listener.CustomListener;
import de.verdox.csi.model.customarmor.IceKingBoots;
import de.verdox.csi.model.customarmor.IceKingChestplate;
import de.verdox.csi.model.customarmor.IceKingHelmet;
import de.verdox.csi.model.customarmor.IceKingLeggings;
import de.verdox.csi.model.custombows.IceKingBow;
import de.verdox.csi.model.custommelees.SwordOfRaijn;
import de.verdox.csi.model.itemsets.IceKingSet;
import de.verdox.vcore.VCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Core extends JavaPlugin {

    public static VCore vcore;
    public static Core core;

    public MainConfig mainConfig;

    @Override
    public void onEnable() {
        core = this;
        vcore = VCore.getInstance(core);
        vcore.registerCommands();
        vcore.registerEvents();
        vcore.enable();
        Bukkit.getPluginManager().registerEvents(new CustomListener(),core);
        this.mainConfig = new MainConfig(core,"mainConfig.yml","");
        mainConfig.init();
        getCommand("csi").setExecutor(new MainCommand());
        getCommand("csi").setTabCompleter(new MainCommand());
        registerItems();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public FileConfiguration getConfig() {
        return super.getConfig();
    }

    private void registerItems(){
        // Melee Weapons
        SwordOfRaijn swordOfRaijn = new SwordOfRaijn(mainConfig);

        // Armor
        IceKingHelmet iceKingHelmet = new IceKingHelmet(mainConfig);
        IceKingChestplate iceKingChestplate = new IceKingChestplate(mainConfig);
        IceKingLeggings iceKingLeggings = new IceKingLeggings(mainConfig);
        IceKingBoots iceKingBoots = new IceKingBoots(mainConfig);

        // Bows
        IceKingBow iceKingBow = new IceKingBow(mainConfig);

        // Sets
        IceKingSet iceKingSet = new IceKingSet(mainConfig, Arrays.asList(iceKingHelmet, iceKingChestplate, iceKingLeggings, iceKingBoots));
    }
}
