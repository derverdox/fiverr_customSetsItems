package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomWeapon extends CustomItem{

    private Map<Player, Long> cache = new HashMap<>();

    public CustomWeapon(Configuration config) {
        super(config);
    }

    public abstract void onComboTriggered();
    protected abstract List<ClickType> combo();

    public void onLeftClick(Player player){
        if(combo() == null || combo().isEmpty())
            return;

    }

    public void onRightClick(Player player){
        if(combo() == null || combo().isEmpty())
            return;

    }
}
