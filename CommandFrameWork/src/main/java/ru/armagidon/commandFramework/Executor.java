package ru.armagidon.commandFramework;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface Executor<S extends CommandSender>
{
    boolean execute(S sender, Command command, String label, String[] args);
}
