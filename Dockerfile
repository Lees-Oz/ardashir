#FROM openjdk:8
#
#RUN apt-get update
#RUN apt-get install -y maven
#
#WORKDIR /code
#
#ADD pom.xml /code/pom.xml
#ADD src /code/src
#
#RUN ["mvn", "verify"]
#
#EXPOSE 4567
#CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/ardashir-jar-with-dependencies.jar"]