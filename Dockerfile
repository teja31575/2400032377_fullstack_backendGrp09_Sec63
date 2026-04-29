FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port (Spring Boot default)
EXPOSE 8080

# Run the jar file
CMD ["sh", "-c", "java -jar target/*.jar"]