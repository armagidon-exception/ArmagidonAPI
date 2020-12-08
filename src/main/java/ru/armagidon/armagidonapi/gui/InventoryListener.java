package ru.armagidon.armagidonapi.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

public class InventoryListener implements Listener
{

    public InventoryListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() instanceof GUIHolder){
            GUIHolder holder = (GUIHolder) event.getInventory().getHolder();
            holder.handleClick(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(event.getInventory().getHolder() instanceof GUIHolder){
            GUIHolder holder = (GUIHolder) event.getInventory().getHolder();
            holder.handleInventoryClose(event);
        }
    }

}
