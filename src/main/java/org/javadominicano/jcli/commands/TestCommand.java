package org.javadominicano.jcli.commands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "test", description = "Runs tests for the Maven project")
public class TestCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        ProcessBuilder builder = new ProcessBuilder("mvn", "test");
        builder.inheritIO();
        Process process = builder.start();
        process.waitFor();
        return process.exitValue();
    }
}