package ru.armagidon.menuframework.menu;

import java.util.HashMap;
import java.util.Map;

public class MenuParser
{

    private final static Map<String, Menu> MENU_REGISTRY = new HashMap<>();

    public static Menu parse(Object obj){
        //TODO ADD MENUS
        return null;
    }

    public static Menu getMenuOrNull(String tag){
        return MENU_REGISTRY.get(tag);
    }
}
