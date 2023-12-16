FROM gradle:8.2-jdk17-alpine AS build

WORKDIR /home/todo-app

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/todo-app-backend-0.0.1-SNAPSHOT.jar todo-app.jar

FROM gradle:8.2-jdk17-alpine

WORKDIR /todo-app

COPY --from=build /home/todo-app/todo-app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "todo-app.jar"]
