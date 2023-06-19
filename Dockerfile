FROM openjdk:17
COPY build/libs/ithunt-0.0.1-SNAPSHOT.jar bot.jar
ENTRYPOINT ["java","-jar","bot.jar"]