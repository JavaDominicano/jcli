package org.javadominicano.jcli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.shell.jline3.PicocliJLineCompleter;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import org.javadominicano.jcli.commands.*;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

@Command(name = "jcli", mixinStandardHelpOptions = true, version = "jcli 1.0", description = "CLI to interact with Maven projects")
public class JCLI implements Callable<Integer> {

    @Option(names = {"--version", "-v"}, description = "version")
    private boolean version;

    @Option(names = {"--about", "-a"}, description = "version")
    private boolean about;


    public static void main(String[] args) throws IOException {

        var commandLine = new CommandLine(new JCLI())
                .addSubcommand("create", new CreateCommand())
                .addSubcommand("add-dependency", new AddDependencyCommand())
                .addSubcommand("generate", new GenerateCommand())
                .addSubcommand("filter", new FilterCommand())
                .addSubcommand("build", new BuildCommand())
                .addSubcommand("test", new TestCommand());

        if(args.length > 0) {
            int exitCode = commandLine.execute(args);
            System.exit(exitCode);
        }

        Terminal terminal = TerminalBuilder.builder().build();
        PicocliJLineCompleter completer = new PicocliJLineCompleter(commandLine.getCommandSpec());

        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .build();

        String prompt = "jcli> ";
        String line;
        try {
            while ((line = reader.readLine(prompt)) != null) {
                if(line.equals("exit")) System.exit(0);
                    commandLine.execute(line.split(" "));
            }
        } catch (org.jline.reader.UserInterruptException ex) {
            
        }
    }

    @Override
    public Integer call() {
        if(about){
            System.out.println("""
                 d8b          888 d8b 
                 Y8P          888 Y8P 
                              888     
                8888  .d8888b 888 888 
                "888 d88P"    888 888 
                 888 888      888 888 
                 888 Y88b.    888 888 
                 888  "Y8888P 888 888 
                 888                  
                d88P                  
              888P"               
              """);
        }
        System.out.println("Use the subcommands: create, add-dependency, generate, filter, build, test");
        return 0;
    }

}
