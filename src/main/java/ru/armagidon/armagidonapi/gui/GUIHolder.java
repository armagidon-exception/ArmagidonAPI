package ru.armagidon.armagidonapi.gui;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.armagidon.armagidonapi.gui.buttons.ActionButton;
import ru.armagidon.armagidonapi.gui.buttons.GUIButton;
import ru.armagidon.armagidonapi.gui.buttons.LinkButton;
import ru.armagidon.armagidonapi.utils.ItemBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class GUIHolder implements InventoryHolder {


    private final Inventory container;
    private final Map<Integer, GUIButton<?>> buttons = new HashMap<>();
    private final Set<Integer> freeSpace = new HashSet<>();
    private final Set<ItemStack> blockedItems = new HashSet<>();

    public GUIHolder(String title, int rows) {
        if (rows > 6) rows = 6;
        container = Bukkit.createInventory(this, rows * 9, title);
    }

    public final void addButton(int slot, GUIButton<?> button){
        buttons.put(slot,button);
        container.setItem(slot, button.getHandle());
    }

    public final void makeFreeSpace(int slot){
        if(buttons.containsKey(slot)) throw new IllegalStateException("This slot is already occupied by a button");
        freeSpace.add(slot);
    }

    public final void open(Player player){
        player.openInventory(container);
    }

    public final void setButtonActivation(int slot, boolean activate){
        GUIButton<?> button = buttons.get(slot);
        if(!button.getClass().getSimpleName().equalsIgnoreCase("ActionButton")){
            throw new IllegalStateException("You can activate ActionButton only");
        }
        ItemStack stack = ItemBuilder.create(button.getHandle()).setEnchanted(activate).asItemStack();
        button.setStack(stack);
        container.setItem(slot, button.getHandle());
        update();
    }

    public final void modifyButton(int slot, ItemStack stack){
        GUIButton<?> button = buttons.get(slot);
        button.setStack(stack);
        container.setItem(slot, button.getHandle());
    }

    public final void addBlockedItem(ItemStack stack){
        Validate.notNull(stack);
        blockedItems.add(stack);
    }



    public final void handleInventoryClose(InventoryCloseEvent event){
        if(freeSpace.size()==0) return;
        freeSpace.stream().
                filter(slot-> {
                    ItemStack item = event.getInventory().getItem(slot);
                    return item!=null&&!item.getType().isAir();
                }).forEach(slot->{
                    ItemStack item = event.getInventory().getItem(slot).clone();
                    event.getInventory().clear(slot);
                    event.getPlayer().getInventory().addItem(item);
                });
    }

    public final void handleClick(InventoryClickEvent event){
        if(freeSpace.contains(event.getRawSlot())) return;
        if(event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) return;
        cancel:
        {
            if (event.getRawSlot() > container.getSize()) {
                if (event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) return;
                for (ItemStack blockedItem : blockedItems) {
                    if (event.getCurrentItem().isSimilar(blockedItem)) break cancel;
                }
                return;
            }
        }
        event.setCancelled(true);
        GUIButton<?> button = buttons.get(event.getRawSlot());
        if(button==null) return;
        String buttonTypeClassName = button.getClass().getSimpleName();
        switch (buttonTypeClassName){
            case "ActionButton": {
                ActionButton b = (ActionButton) button;
                b.action(event);
                break;
            }
            case "LinkButton": {
                LinkButton b = (LinkButton) button;
                b.action((Player) event.getWhoClicked());
                break;
            }
            default:
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return container;
    }

    public final void reload(){
        getInventory().clear();
        buttons.forEach((slot,item)-> getInventory().setItem(slot,item.getHandle()));
        List<Player> pl = getInventory().getViewers().stream().map(v -> (Player) v).collect(Collectors.toList());
        pl.forEach(HumanEntity::closeInventory);
    }

    public final void update(){
        container.getViewers().stream().map(p->(Player)p).forEach(Player::updateInventory);
    }

}
