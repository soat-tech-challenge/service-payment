# Java 17 optimized image
FROM bellsoft/liberica-runtime-container:jre-17-slim-musl

# Creates and switches to non-root user
ENV APP_USER=app
# Busybox syntax, which is available in the image
RUN addgroup -S $APP_USER && adduser -D -g "" -G $APP_USER $APP_USER
USER app

# Copy .jar to container
ARG JAR_FILE=target/*.jar
ENV APP_FOLDER=/opt/app
WORKDIR $APP_FOLDER
COPY $JAR_FILE app.jar

# Execute application
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
