package org.javadominicano.jcli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Command(name = "add-dependency", description = "Adds a dependency to the Maven project")
public class AddDependencyCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "Group ID of the dependency")
    private String groupId;

    @Parameters(index = "1", description = "Artifact ID of the dependency")
    private String artifactId;

    @Parameters(index = "2", description = "Version of the dependency")
    private String version;

    @Override
    public Integer call() throws Exception {
        String dependencyTemplate = """
                <dependency>
                    <groupId>%s</groupId>
                    <artifactId>%s</artifactId>
                    <version>%s</version>
                </dependency>
                """;
        String dependency = dependencyTemplate.formatted(groupId, artifactId, version);

        String pomPath = "pom.xml"; // Assuming we're in the project directory
        String pomContent = new String(Files.readAllBytes(Paths.get(pomPath)));
        if (pomContent.contains("</dependencies>")) {
            pomContent = pomContent.replace("</dependencies>", dependency + "    </dependencies>");
        } else if (pomContent.contains("</project>")) {
            pomContent = pomContent.replace("</project>",
                    "<dependencies>" + dependency + "</dependencies>\n</project>");
        } else {
            throw new IOException("Invalid pom.xml format.");
        }

        Files.write(Paths.get(pomPath), pomContent.getBytes());
        System.out.println("Dependency added to pom.xml");
        return 0;
    }
}