FROM gradle:jdk11

WORKDIR /springjpa
RUN mkdir /springjpa/build
COPY src /springjpa/src
COPY build.gradle /springjpa
COPY gradlew /springjpa
COPY gradlew.bat /springjpa

RUN gradlew bootJar

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/springjpa-0.0.1-SNAPSHOT.jar"]
