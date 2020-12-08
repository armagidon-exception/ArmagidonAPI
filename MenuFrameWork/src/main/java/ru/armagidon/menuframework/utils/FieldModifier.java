package ru.armagidon.menuframework.utils;


import ru.armagidon.menuframework.menuelements.MenuElement;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;


public class FieldModifier
{
    private final Field field;
    private final BiConsumer<Field, MenuElement> action;
    private final Object source;

    public FieldModifier(Object source, Field field, BiConsumer<Field, MenuElement> action) {
        this.field = field;
        this.action = action;
        this.source = source;
    }

    public void action(MenuElement element){
        action.accept(field, element);
    }
}
