package ru.armagidon.menuframework.menuelements;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import ru.armagidon.menuframework.utils.FieldModifier;

public class ButtonElement extends MenuElement
{

    private @Getter final Object source;

    public ButtonElement(Object source, ItemStack stack, FieldModifier modifier) {
        super(stack);
        this.source = source;
        this.setAction((e) -> modifier.action(this));
    }

    @Override
    public MenuElementType getType() {
        return MenuElementType.BUTTON;
    }
}
