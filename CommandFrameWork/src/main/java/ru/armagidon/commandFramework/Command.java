package ru.armagidon.commandFramework;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Command<S extends CommandSender>
{

    Command(Class<S> senderClass, String name, Executor<S> executor, ImmutableMap<String, SubCommand<S>> subCommands) {
        this.senderClass = senderClass;
        this.name = name;
        this.executor = executor;
        this.subCommands = subCommands;
    }

    private @Getter final Class<S> senderClass;
    private @Getter final String name;
    private final Executor<S> executor;
    private final ImmutableMap<String, SubCommand<S>> subCommands;

    public boolean execute(S sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            if (subCommands.values().size() > 0) return false;
            return executor.execute(sender, command, label, args);
        } else {
            String subCommand = args[0].toLowerCase();
            SubCommand<S> sub = null;
            if (!subCommands.containsKey(subCommand.toLowerCase())) {
                for (SubCommand<S> entry : subCommands.values()) {
                    if (entry.getAliases().contains(subCommand)){
                        sub = entry;
                        break;
                    }
                }
                if (sub == null) return false;
            }

            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

            return subCommands.get(subCommand).execute(sender, command, subCommand, subArgs);
        }
    }

    public static <S extends CommandSender> Builder<S> builder(){
        return new Builder<>();
    }

    public static class Builder<T extends CommandSender> {
        Class<T> senderClass = (Class<T>) Player.class;
        Executor<T> executor = (sender, command, label, args) -> false;
        String name;
        ImmutableMap.Builder<String, SubCommand<T>> subCommandBuilder = new ImmutableMap.Builder<>();

        public Builder<T> name(String name){
            this.name = name;
            return this;
        }

        public Builder<T> executor(Executor<T> executor){
            this.executor = executor;
            return this;
        }

        public <O extends CommandSender> Builder<O> senderClass(Class<O> senderClass){
            Builder<O> newBuilder = new Builder<>();
            newBuilder.senderClass = senderClass;
            return newBuilder;
        }

        public Builder<T> addSubCommand(SubCommand<T> subCommand){
            subCommandBuilder.put(subCommand.getName(), subCommand);
            return this;
        }

        public Command<T> build(){
            return new Command<>(senderClass,name,executor,subCommandBuilder.build());
        }
    }
}
