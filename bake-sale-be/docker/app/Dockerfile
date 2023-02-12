FROM openjdk:17-oracle

ARG JAR_FILE=./build/libs/testassignment.jar

COPY  ${JAR_FILE} testassignment.jar

EXPOSE 14441

ENV PORT 14441

CMD ["java", "-jar", "/testassignment.jar"]