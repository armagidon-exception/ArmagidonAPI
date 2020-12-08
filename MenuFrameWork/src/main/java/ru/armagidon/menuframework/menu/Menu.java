package ru.armagidon.menuframework.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import ru.armagidon.menuframework.menuelements.MenuElement;
import ru.armagidon.menuframework.menuelements.MenuElementType;

import java.util.HashMap;
import java.util.Map;

public class Menu
{
    private @Getter @Setter String title;
    private @Getter @Setter int size;
    private final Map<Integer, MenuElement> elements = new HashMap<>();
    private final Map<MenuElementType, Map<String, MenuElement>> groupedElements = new HashMap<MenuElementType, Map<String, MenuElement>>(){
        {
            for (MenuElementType type : MenuElementType.values()) {
                groupedElements.put(type, new HashMap<>());
            }
        }
    };

    private final Object source;

    Menu(Object obj){
        this.source = obj;
    }

    public void addElement(String tag, int slot, MenuElement element){
        elements.put(slot, element);
        groupedElements.get(element.getType()).put(tag, element);
    }

    public Map<String, MenuElement> getElementsByType(MenuElementType type){
        return groupedElements.get(type);
    }

    public void open(Player player){
        
    }
}
