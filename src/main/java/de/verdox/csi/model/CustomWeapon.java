package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CustomWeapon extends CustomItem{

    public CustomWeapon(Configuration config) {
        super(config);
    }

    public abstract void onComboTriggered(Player player);
    public abstract List<Combo.Click> combo();
}
