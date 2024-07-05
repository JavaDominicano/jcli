package org.javadominicano.jcli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;


@Command(name = "generate", description = "Generates project scaffolding")
public class GenerateCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "Type of scaffolding to generate (class, interface, record)")
    private String type;

    @Parameters(index = "1", description = "Fully qualified name of the class/interface/record")
    private String qualifiedName;

    @Override
    public Integer call() throws Exception {
        String content = switch (type.toLowerCase()) {
            case "class" -> generateClassTemplate(qualifiedName);
            case "interface" -> generateInterfaceTemplate(qualifiedName);
            case "record" -> generateRecordTemplate(qualifiedName);
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };

        String directoryPath = "src/main/java/" + qualifiedName.substring(0, qualifiedName.lastIndexOf('.')).replace('.', '/');
        String fileName = qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1) + ".java";

        Files.createDirectories(Paths.get(directoryPath));
        Files.write(Paths.get(directoryPath, fileName), content.getBytes());

        System.out.printf("%s generated at %s%n", type, Paths.get(directoryPath, fileName));
        return 0;
    }

    private String generateClassTemplate(String qualifiedName) {
        String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf('.'));
        String className = qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);

        return """
                package %s;

                /**
                 * Class %s
                 */
                public class %s {
                    
                    public %s() {
                        // Constructor
                    }
                    
                    // Methods
                }
                """.formatted(packageName, className, className, className);
    }

    private String generateInterfaceTemplate(String qualifiedName) {
        String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf('.'));
        String interfaceName = qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);

        return """
                package %s;

                /**
                 * Interface %s
                 */
                public interface %s {
                    
                    // Methods
                }
                """.formatted(packageName, interfaceName, interfaceName);
    }

    private String generateRecordTemplate(String qualifiedName) {
        String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf('.'));
        String recordName = qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);

        return """
                package %s;

                public record %s() {}
                """.formatted(packageName, recordName);
    }

}
