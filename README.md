# JCLI - A Command Line Tool for he Java Ecosystem


The purpose of this tool is to provide an unified CLI for the Java ecosystem. This tool will initially provide the fallowing features:

* Create maven/gradle projects from the command line
  * ```jcli create com.example my-app```
* Accept maven and gradle arguments  
* Install dependencies by the command line modifying the gradle or pom file.
  * ```jcli install user-artifactId```. When there are more artifacts with the same name the groupsId list will be shown for choice
* Install dependencies by the command line downloading and building github or gitlab dependencies (from master)
  * ```jcli install -g github-url``` Where com.github.**user** should match https://github.com/user/project and project with the name of the project on github
  * ```jcli install -g github-user proyect```. .
  * ```jcli install -g github-user project tag```
  * ```jcli install --github user project tag```
* Accept plugins
  * ```jcli install-plugin plugin-artifact```
  * ```jcli install-plugin -g https://github.com/user/project```
  * ```jcli install-plugin sdkman```
    * ```jcli skd --install java```
    * ```jcli sdk --list```
* Create a embed a JVM using JLink or Pakr
  * ```jcli embed ~/project-folder```


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

The usage of the add-dependency command is as bellow:
```shell
./jcli add-dependency org.apache.commons commons-lang3 3.12.0
```

The resulting pom file now contains the ```<project>``` tag has the ```<dependencies>``` tag with every new dependency added each time the command is executed
```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.12.0</version>
    </dependency>
</dependencies>
```


