package ru.armagidon.menuframework.menuelements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class MenuInput extends MenuElement
{

    private final Set<Material> whitelistedMaterials = new HashSet<>();

    public MenuInput() {
        super(new ItemStack(Material.AIR));
        setAction( (e) -> {

            if( whitelistedMaterials.size() == 0 ) return;
            if( e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            ItemStack current = e.getCurrentItem();
            if( !whitelistedMaterials.contains(current.getType()) ) e.setCancelled(true);

        });
    }

    @Override
    public MenuElementType getType() {
        return MenuElementType.INPUT;
    }

    public void addWhitelistedItem(Material material){
        whitelistedMaterials.add(material);
    }
}
