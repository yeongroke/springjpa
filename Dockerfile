FROM gradle:7-jdk11

WORKDIR /springjpa
RUN mkdir /springjpa/build
COPY src /springjpa/src
COPY build.gradle /springjpa
COPY gradlew /springjpa
COPY gradlew.bat /springjpa
RUN chmod -x ./gradlew
RUN ./gradlew clean build

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/springjpa.jar"]
