package ru.armagidon.testcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("sub1")){
            //TODO 1
            int arg1 = Integer.parseInt(args[1]);
        } else if (args[0].equalsIgnoreCase("sub2")){
            //TODO 2
        }

        return true;
    }
}
