package ru.armagidon.armagidonapi.gui.buttons;

import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public abstract class GUIButton<T>
{
    private @Setter ItemStack stack;

    public GUIButton(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getHandle() {
        return stack;
    }

    public abstract void action(T t);
}
