package ru.armagidon.commandFramework;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class CommandManager<S extends CommandSender>
{
    private final Plugin plugin;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(Command<S> command){
        org.bukkit.command.Command bukkitCommand = new org.bukkit.command.Command(command.getName()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                if (!command.getSenderClass().isInstance(sender)) return true;
                return command.execute((S) sender, this, commandLabel, args);
            }
        };
        CommandMap map;
        if (PaperLib.isPaper()){
            map = Bukkit.getCommandMap();
        } else {
            try {
                map = (CommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                return;
            }
        }
        map.register(plugin.getName(), bukkitCommand);
    }
}
