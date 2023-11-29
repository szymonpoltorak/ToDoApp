FROM gradle:8.2-jdk17-alpine

WORKDIR /home/todo-app

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN ls -la

RUN ls -la build

RUN ls -la build/libs

RUN mv build/libs/todo-app-backend-0.0.1-SNAPSHOT.jar todo-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "todo-app.jar"]
