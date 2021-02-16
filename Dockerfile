FROM maven:3.6.3-jdk-8-slim AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/


WORKDIR /build/

RUN mvn -B -U -e clean verify

FROM openjdk:8-jre-alpine

WORKDIR /

COPY --from=MAVEN_BUILD /build/target/votos-0.0.1-SNAPSHOT.jar /

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "votos-0.0.1-SNAPSHOT.jar"]
