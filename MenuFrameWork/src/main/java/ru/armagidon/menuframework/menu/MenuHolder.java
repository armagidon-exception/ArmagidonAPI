package ru.armagidon.menuframework.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.armagidon.menuframework.menuelements.MenuElement;

import java.util.Map;

public class MenuHolder implements InventoryHolder {


    private final Inventory container;
    private final Map<Integer, MenuElement> elements;

    public MenuHolder(String title, int rows, Map<Integer, MenuElement> elements) {
        if (rows > 6) rows = 6;
        container = Bukkit.createInventory(this, rows * 9, title);
        this.elements = elements;
    }

    public final void open(Player player){
        player.openInventory(container);
    }

    public final void handleClick(InventoryClickEvent event){
        if(event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) return;
        {
            if (event.getRawSlot() > container.getSize()) {
                if (event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) return;
                return;
            }
        }
        event.setCancelled(true);
        MenuElement button = elements.get(event.getRawSlot());
        if(button==null) return;
        button.action(event);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return container;
    }
}
