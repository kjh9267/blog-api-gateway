FROM eclipse-temurin:17-jdk

ARG JAR_FILE=target/blog-api-gateway-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} blog-api-gateway.jar

ENTRYPOINT ["java", "-jar", "/blog-api-gateway.jar"]