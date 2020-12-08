package ru.armagidon.commandFramework;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Builder
public class SubCommand<S extends CommandSender>
{
    private @Getter final List<String> aliases;
    private final Executor<S> executor;
    private @Getter final String name;

    public boolean execute(S sender, org.bukkit.command.Command cmd, String label, String[] args){
        return executor.execute(sender, cmd, label, args);
    }
}
