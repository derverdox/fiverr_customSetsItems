package de.verdox.csi;

import de.verdox.csi.commands.MainCommand;
import de.verdox.csi.files.MainConfig;
import de.verdox.csi.listener.CustomListener;
import de.verdox.csi.model.custommelees.ThrowingAxe;
import de.verdox.csi.model.itemsets.SpeedsterSet;
import de.verdox.csi.model.itemsets.TankSet;
import de.verdox.csi.model.itemsets.icekingset.IceKingBow;
import de.verdox.csi.model.custommelees.SwordOfRaijn;
import de.verdox.csi.model.itemsets.IceKingSet;
import de.verdox.csi.model.itemsets.StreetFighterSet;
import de.verdox.csi.model.itemsets.icekingset.IceKingBoots;
import de.verdox.csi.model.itemsets.icekingset.IceKingChestplate;
import de.verdox.csi.model.itemsets.icekingset.IceKingHelmet;
import de.verdox.csi.model.itemsets.icekingset.IceKingLeggings;
import de.verdox.csi.model.itemsets.speedsterset.SpeedsterBoots;
import de.verdox.csi.model.itemsets.speedsterset.SpeedsterChestplate;
import de.verdox.csi.model.itemsets.speedsterset.SpeedsterHelmet;
import de.verdox.csi.model.itemsets.speedsterset.SpeedsterLeggings;
import de.verdox.csi.model.itemsets.streetfighterset.StreetFighterBoots;
import de.verdox.csi.model.itemsets.streetfighterset.StreetFighterChestplate;
import de.verdox.csi.model.itemsets.streetfighterset.StreetFighterHelmet;
import de.verdox.csi.model.itemsets.streetfighterset.StreetFighterLeggings;
import de.verdox.csi.model.itemsets.tankset.TankBoots;
import de.verdox.csi.model.itemsets.tankset.TankChestplate;
import de.verdox.csi.model.itemsets.tankset.TankHelmet;
import de.verdox.csi.model.itemsets.tankset.TankLeggings;
import de.verdox.vcore.VCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Core extends JavaPlugin {

    public static VCore vcore;
    public static Core core;
    public static boolean debugMode = false;

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

        // Ice King Set
        IceKingHelmet iceKingHelmet = new IceKingHelmet(mainConfig);
        IceKingChestplate iceKingChestplate = new IceKingChestplate(mainConfig);
        IceKingLeggings iceKingLeggings = new IceKingLeggings(mainConfig);
        IceKingBoots iceKingBoots = new IceKingBoots(mainConfig);

        IceKingBow iceKingBow = new IceKingBow(mainConfig);

        IceKingSet iceKingSet = new IceKingSet(mainConfig, Arrays.asList(iceKingHelmet, iceKingChestplate, iceKingLeggings, iceKingBoots));
        //########################

        // Street Fighter Set
        StreetFighterHelmet streetFighterHelmet = new StreetFighterHelmet(mainConfig);
        StreetFighterChestplate streetFighterChestplate = new StreetFighterChestplate(mainConfig);
        StreetFighterLeggings streetFighterLeggings = new StreetFighterLeggings(mainConfig);
        StreetFighterBoots streetFighterBoots = new StreetFighterBoots(mainConfig);

        StreetFighterSet streetFighterSet = new StreetFighterSet(mainConfig, Arrays.asList(streetFighterHelmet,streetFighterChestplate,streetFighterLeggings,streetFighterBoots));
        //########################

        // SpeedsterSet
        SpeedsterHelmet speedsterHelmet = new SpeedsterHelmet(mainConfig);
        SpeedsterChestplate speedsterChestplate = new SpeedsterChestplate(mainConfig);
        SpeedsterLeggings speedsterLeggings = new SpeedsterLeggings(mainConfig);
        SpeedsterBoots speedsterBoots = new SpeedsterBoots(mainConfig);

        SpeedsterSet speedsterSet = new SpeedsterSet(mainConfig,Arrays.asList(speedsterHelmet,speedsterChestplate,speedsterLeggings,speedsterBoots));
        //########################

        // Tank Set

        TankHelmet tankHelmet = new TankHelmet(mainConfig);
        TankChestplate tankChestplate = new TankChestplate(mainConfig);
        TankLeggings tankLeggings = new TankLeggings(mainConfig);
        TankBoots tankBoots = new TankBoots(mainConfig);

        TankSet tankSet = new TankSet(mainConfig,Arrays.asList(tankHelmet,tankChestplate,tankLeggings,tankBoots));

        //
        SwordOfRaijn swordOfRaijn = new SwordOfRaijn(mainConfig);
        ThrowingAxe throwingAxe = new ThrowingAxe(mainConfig);
    }

    public static void debug(String debug){
        if(debugMode == true)
            System.out.println(debug);
    }
}
