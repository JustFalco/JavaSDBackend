FROM openjdk:17
EXPOSE 8080
ADD target/software-development-eindopdracht.jar software-development-eindopdracht.jar
ENTRYPOINT ["java", "-jar", "/software-development-eindopdracht.jar"]