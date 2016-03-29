To test QName class you will need Maven 3 installed and configured.
Make sure you have a right directory layout appropriate to Maven's default directory layout:
  src/main/java - application sources,
  src/test/java - test sources,
and a pom.xml file in src directory.
All application sources and test sources could be downloaded from this repository.
In Command-Line, while being in src directory with pom.xml file, type command:
  mvn test
Maven will start working and will show you the test results.
To build a *.jar file type command:
  mvn package