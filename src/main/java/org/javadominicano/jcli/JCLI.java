package org.javadominicano.jcli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

import org.javadominicano.jcli.commands.*;

@Command(name = "jcli", mixinStandardHelpOptions = true, version = "jcli 1.0",
        description = "CLI to interact with Maven projects")
public class JCLI implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new JCLI())
                .addSubcommand("create", new CreateCommand())
                .addSubcommand("add-dependency", new AddDependencyCommand())
                .addSubcommand("build", new BuildCommand())
                .addSubcommand("test", new TestCommand())
                .execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        System.out.println("Use the subcommands: create, add-dependency, build, test");
        return 0;
    }

}
