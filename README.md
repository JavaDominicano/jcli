# JCLI

### Examples


#### Create a New Project

```shell
./jcli create com.example my-app 1.0.0-SNAPSHOT
```

This command will create a new Maven project in a directory named my-app with the groupId com.example, the artifactId my-app, and the version 1.0.0-SNAPSHOT. The content of the generated pom.xml will be something like this:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</project>
```

You could also specify the build tool system with the ```--build-tool```. By default use maven.

##### Specify The Build Tool With ```--build-tool``` flag
```shell
./jcli create com.example my-app 1.0.0-SNAPSHOT --build-tool gradle
```
The process is similar, this generate a folder named my-app with the fallowing gradle file:

```gradle
plugins {
    id 'java'
}

group = 'com.example'
version = '1.0.0-SNAPSHOT'

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
```


#### Add a Dependency

```shell
./jcli add-dependency org.apache.commons commons-lang3 3.12.0

```

#### Execute a Java JAR or class

Execute a Java JAR or a specific class within a JAR file.

