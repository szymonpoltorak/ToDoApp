FROM gradle:8.2-jdk17-alpine

WORKDIR /home/todo-app

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

EXPOSE 8080

ENTRYPOINT ["gradle", "bootRun"]
