package ru.armagidon.armagidonapi.gui;

import org.bukkit.entity.Player;
import ru.armagidon.armagidonapi.gui.buttons.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PagedGUI
{
    private int pagePointer = 0;
    private final int pages;
    private final int rows;
    private final List<GUIHolder> holders;

    public PagedGUI(int pages, String title_format,int rows) {
        this.pages = pages;
        this.rows = rows;
        this.holders = new ArrayList<>(pages);
        for (int i = 0; i < this.pages; i++) {
            title_format = title_format.replace("(%PN%)",""+(i+1));
            GUIHolder holder = new GUIHolder(title_format,6);
            holders.add(holder);
        }
    }

    public void fill(List<GUIButton<?>> buttons){
        Queue<GUIButton<?>> queue = new LinkedList<>(buttons);
        int max_for_page = 9*this.rows;
        int pages = (int) Math.ceil((double) buttons.size()/max_for_page);
        for (int p = 0; p < pages&&!queue.isEmpty(); p++) {
            for (int slot = 0; slot < max_for_page&&!queue.isEmpty(); slot++) {
                holders.get(p).addButton(slot,queue.poll());
                System.out.println();
            }
        }
    }

    public void addButton(int free_slot, GUIButton<?> button){
        holders.forEach(p-> p.addButton(free_slot,button));
    }

    public GUIHolder nextPage(){
        pagePointer++;
        if(pagePointer>pages) pagePointer=0;
        GUIHolder holder = holders.get(pagePointer);
        if(holder == null){
            holder = new GUIHolder("NULL OBJECT",6);
        }
        return holder;
    }

    public GUIHolder previousPage(){
        pagePointer--;
        if(pagePointer<0) pagePointer=pages-1;
        GUIHolder holder = holders.get(pagePointer);
        if(holder == null){
            holder = new GUIHolder("NULL OBJECT",6);
        }
        return holder;
    }

    public int getCurrentPageNumber() {
        return pagePointer;
    }

    public void open(Player player){
        holders.get(0).open(player);
    }
}
