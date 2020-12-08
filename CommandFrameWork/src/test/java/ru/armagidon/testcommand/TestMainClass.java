package ru.armagidon.testcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TestMainClass extends JavaPlugin
{
    public void onEnable(){
        TestCommand testCommand = new TestCommand();
        Bukkit.getServer().getCommandMap().register(this.getName(), new Command("testcommand") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                return testCommand.onCommand(sender, this, commandLabel, args);
            }
        });
    }
}
