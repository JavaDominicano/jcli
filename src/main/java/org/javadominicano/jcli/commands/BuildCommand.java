package org.javadominicano.jcli.commands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "build", description = "Builds the Maven project")
public class BuildCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        ProcessBuilder builder = new ProcessBuilder("mvn", "clean", "install");
        builder.inheritIO();
        Process process = builder.start();
        process.waitFor();
        return process.exitValue();
    }
}