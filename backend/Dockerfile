# Use the official Maven 3.9.1 image as the build image
FROM maven:3.9.1-amazoncorretto-20 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Download the dependencies specified in the pom.xml file
RUN mvn dependency:resolve

# Copy the rest of the application code to the container
COPY src ./src

# Build the application using Maven
RUN mvn package

# Use the official OpenJDK 20 image as the runtime image
FROM openjdk:20

# Create a non-root user to run the application
RUN groupadd -r spring && useradd --no-log-init -r -g spring spring

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/EasyOrder-0.0.1-SNAPSHOT.jar ./myapp.jar

# Change the owner of the working directory to the non-root user
RUN chown -R spring:spring /app

# Expose the port used by the application
EXPOSE 8080

# Start the application when the container starts
USER spring
CMD ["java", "-jar", "myapp.jar"]
