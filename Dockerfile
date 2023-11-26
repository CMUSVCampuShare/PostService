# Use a base image with JDK and a minimal OS
FROM openjdk

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY build/libs/PostService-0.0.1-SNAPSHOT.jar /app/PostService.jar

# Specify the command to run on container startup
CMD ["java", "-jar", "PostService.jar"]