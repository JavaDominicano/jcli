package org.javadominicano.jcli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Command(name = "create", description = "Creates a new Maven project")
public class CreateCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "Group ID of the project")
    private String groupId;

    @Parameters(index = "1", description = "Artifact ID of the project")
    private String artifactId;

    @Parameters(index = "2", description = "Version of the project")
    private String version;

    @Option(names = {"--build-tool", "-b"}, description = "Build tool to use (maven, gradle)", defaultValue = "maven")
    private String buildTool;

    @Override
    public Integer call() throws Exception {
        if (buildTool.equalsIgnoreCase("maven")) {
            createMavenProject(groupId, artifactId, version);
        } else if (buildTool.equalsIgnoreCase("gradle")) {
            createGradleProject(groupId, artifactId, version);
        } else {
            System.err.println("Invalid build tool specified. Use 'maven' or 'gradle'.");
            return 1;
        }
        return 0;
    }

    private void createMavenProject(String groupId, String artifactId, String version) throws IOException {
        String pomContent = generateMavenPom(groupId, artifactId, version);
        Files.createDirectories(Paths.get(artifactId));
        Files.write(Paths.get(artifactId, "pom.xml"), pomContent.getBytes());
        System.out.println("Maven project created at " + artifactId);
    }

    private void createGradleProject(String groupId, String artifactId, String version) throws IOException {
        String buildGradleContent = generateGradleBuildFile(groupId, artifactId, version);
        Files.createDirectories(Paths.get(artifactId));
        Files.write(Paths.get(artifactId, "build.gradle"), buildGradleContent.getBytes());
        System.out.println("Gradle project created at " + artifactId);
    }

    private String generateMavenPom(String groupId, String artifactId, String version) {
        String pomTemplate = """
                <project xmlns="http://maven.apache.org/POM/4.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>
                    <groupId>%s</groupId>
                    <artifactId>%s</artifactId>
                    <version>%s</version>
                </project>
                """;
        return pomTemplate.formatted(groupId, artifactId, version);
    }

    private String generateGradleBuildFile(String groupId, String artifactId, String version) {
        String buildGradleTemplate = """
                plugins {
                    id 'java'
                }

                group = '%s'
                version = '%s'

                repositories {
                    mavenCentral()
                }

                dependencies {
                    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
                    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
                }

                test {
                    useJUnitPlatform()
                }
                """;
        return buildGradleTemplate.formatted(groupId, version);
    }
}
