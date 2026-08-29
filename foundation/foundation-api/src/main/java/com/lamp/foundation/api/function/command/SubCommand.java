package com.lamp.foundation.api.function.command;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hahaha
 */
public interface SubCommand extends Command {

    default Map<String, Command> subCommandMap() {
        return subCommands().stream().collect(Collectors.toMap(Command::commandName, v -> v));
    }

    List<Command> subCommands();

    String commandName();
}
