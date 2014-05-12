package org.springframework.boot.cli.command.create;

import org.springframework.boot.cli.command.Command;
import org.springframework.boot.cli.command.options.OptionHelp;

import java.util.Collection;

public class CreateCommand implements Command {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a brand new Spring Boot project";
    }

    @Override
    public String getUsageHelp() {
        return null;
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public Collection<OptionHelp> getOptionsHelp() {
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        new ProjectMaker(args[0]).create();
    }

}
