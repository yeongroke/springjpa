FROM gradle:7.0.1-jdk-11

WORKDIR /springjpa
RUN mkdir /springjpa/build
COPY src /springjpa/src
COPY build.gradle /springjpa
COPY gradlew /springjpa
COPY gradlew.bat /springjpa

RUN mvn package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/springjpa-0.0.1-SNAPSHOT.jar"]
