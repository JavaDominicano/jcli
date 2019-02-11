# jcli

### A command line tool for the Java ecosystem

The purpose of this tool is to provide a unified CLI for the Java ecosystem. This tool will initially provide the fallowing features:

* Create maven/gradle projects from the command line
  * ```jcli com.domain.myproject project-artifact```
* Install dependencies by the command line modifying the gradle or pom file.
  * ```jcli install artifactId```. When there are more artifacts with the same name the groupsId list will be shown for choice
* Install dependencies by the command line downloading and building github or gitlab dependencies
  * ```jcli github com.github.usuario proyecto```. Where com.github.user should match https://github.com/user and project with the name of the project on github.
* Accept plugins
  * ```jcli install-plugin  repo-url```
* Create a embed a JVM using JLink or Pakr
  * ```jcli embed  ~/project-artifact```
