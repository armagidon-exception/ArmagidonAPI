package ru.armagidon.menuframework.menuelements;

import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.armagidon.armagidonapi.itemutils.ItemBuilder;

import java.util.function.Consumer;

public abstract class MenuElement
{
    private Consumer<InventoryClickEvent> onClick;
    private @Setter ItemStack stack;
    private Consumer<MenuElement> onRender = (el) -> {};
    private Consumer<MenuElement> onDeRender = (el) -> {};

    public MenuElement(ItemStack stack, Consumer<InventoryClickEvent> onClick) {
        this.stack = stack;
        this.onClick = onClick;
    }

    public MenuElement(ItemStack stack) {
        this(stack, null);
    }

    protected void setAction(Consumer<InventoryClickEvent> action){
        this.onClick = action;
    }

    public ItemStack getHandle() {
        return stack;
    }

    public ItemBuilder modify(){
        return ItemBuilder.create(stack);
    }

    public MenuElement renderEvent(Consumer<MenuElement> onRender){
        this.onRender = onRender;
        return this;
    }

    public MenuElement derenderEvent(Consumer<MenuElement> onDeRender){
        this.onDeRender = onDeRender;
        return this;
    }

    public void action(InventoryClickEvent event){
        onClick.accept(event);
    }

    public void render(){
        onRender.accept(this);
    }

    public void derender(){
        onDeRender.accept(this);
    }

    public abstract MenuElementType getType();
}
