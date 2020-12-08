package ru.armagidon.armagidonapi.gui.buttons;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ActionButton extends GUIButton<InventoryClickEvent>
{
    private final Consumer<InventoryClickEvent> action;

    public ActionButton(ItemStack stack, Consumer<InventoryClickEvent> action) {
        super(stack);
        this.action = action;
    }

    @Override
    public void action(InventoryClickEvent t) {
        action.accept(t);
    }
}
