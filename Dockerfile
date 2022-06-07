FROM openjdk:11
COPY proyectofinal-main/target/practicas-1.0.0.jar practicas-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/practicas-1.0.0.jar"]