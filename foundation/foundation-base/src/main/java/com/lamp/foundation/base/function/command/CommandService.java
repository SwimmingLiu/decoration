package com.lamp.foundation.base.function.command;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import com.lamp.foundation.api.function.command.Command;
import com.lamp.foundation.api.function.command.SubCommand;
import com.lamp.foundation.base.lang.util.collections.Tuple.Pair;

/**
 * @author hahaha
 */
public class CommandService {

    private final Stack<String> stack = new Stack<>();
    private Command command;
    private Class<? extends Command> clazz;
    private String[] args;
    private boolean error = false;


    public CommandService() {

    }

    public static void run(Command command, String[] args) {
        run(command, command.getClass(), args);
    }

    @SuppressWarnings({"AliDeprecation", "deprecation"})
    public static void run(Class<? extends Command> clazz, String[] args) {
        try {
            Command command = clazz.newInstance();
            run(command, command.getClass(), args);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(Command command, Class<? extends Command> clazz, String[] args) {
        CommandService commandService = new CommandService();
        commandService.command = command;
        commandService.clazz = clazz;
        commandService.args = args;
        commandService.run();
    }

    public void run() {
        for (String arg : args) {
            stack.push(arg);
        }
        Command command = this.command;
        if (this.command instanceof SubCommand) {
            command = this.macth((SubCommand) this.command);
        }
        if (!this.error || Objects.equals(stack.peek(), "--help")) {
            this.run(command);
        }

        if (this.command instanceof SubCommand) {
            this.macth((SubCommand) this.command);
        } else {

        }
    }

    private void run(Command command) {

    }

    private Map<String, Pair<Object, Field>> getAllArgs(Command command){
        return null;
    }


    private Command macth(SubCommand subCommand) {
        Command command = subCommand;
        for (; ; ) {
            String arg = stack.pop();
            if (arg.startsWith("--")) {
                if (command instanceof SubCommand) {
                    this.error = true;
                    return null;
                }
                return command;
            }
            Command tmp = subCommand.subCommandMap().get(arg);
            if (Objects.isNull(tmp)) {
                this.error = true;
                return null;
            }
            if (tmp instanceof SubCommand) {
                subCommand = (SubCommand) tmp;
            }
            // 这里主要为了解决，
            command = tmp;
        }

    }

    private void printUsage(SubCommand subCommand) {

    }


}
