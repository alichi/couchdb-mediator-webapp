set MAVEN_OPTS=-Xms512m -Xmx2048m -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
mvn clean install -T2 tomcat7:run