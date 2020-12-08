package ru.armagidon.menuframework.menuelements;

import org.bukkit.inventory.ItemStack;

public class IconElement extends MenuElement
{

    public IconElement(ItemStack stack) {
        super(stack, (e) -> e.setCancelled(true));
    }

    @Override
    public MenuElementType getType() {
        return MenuElementType.ICON;
    }
}
