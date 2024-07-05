package org.javadominicano.jcli.commands;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.javadominicano.jcli.TerminalConstants;
import org.javadominicano.jcli.TerminalUtils;

@Command(name = "filter", description = "Filters and lists all .java files in a directory and its subdirectories, grouped by folders.")
public class FilterCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "The file name to search")
    private String query;

    @Option(names = {"--ignore-case", "-ic"}, description = "Ignore case")
    private boolean ignoreCase;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new FilterCommand()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try (Stream<Path> paths = Files.walk(Paths.get("."))) {          
            Map<Path, List<Path>> groupedByFolder = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        var pathCase = ignoreCase ? path.toString().toLowerCase() : path.toString();
                        var queryCase = ignoreCase ? query.toLowerCase() : query;
                        
                        if(queryCase.startsWith("*")) {
                            queryCase = queryCase.substring(1);
                            return pathCase.toString().endsWith(queryCase);
                        }
                        if(queryCase.endsWith("*")) {
                            queryCase = queryCase.substring(0, query.length() - 1);
                            return pathCase.toString().startsWith(queryCase);
                        }
                        return pathCase.toString().contains(queryCase);
                    })
                    .collect(Collectors.groupingBy(Path::getParent));

            groupedByFolder.forEach((folder, files) -> {
                System.out.println(folder);
                for (int i = 0; i < files.size(); i++) {
                    Path file = files.get(i);
                    String prefix = (i == files.size() - 1) ? "└── " : "├── ";
                    String toBeBolded = query.replace("*", "");
                    System.out.println(prefix + TerminalUtils.bold(file.getFileName().toString(), toBeBolded));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

}
