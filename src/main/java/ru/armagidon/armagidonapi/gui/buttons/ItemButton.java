package ru.armagidon.armagidonapi.gui.buttons;

import org.bukkit.inventory.ItemStack;

public class ItemButton extends GUIButton<ItemStack>
{

    public ItemButton(ItemStack stack) {
        super(stack);
    }

    @Override
    public void action(ItemStack stack) {}
}
