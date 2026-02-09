# Étape 1 : On construit le projet avec Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : On lance le site avec une version légère de Java
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# On récupère le fichier .jar créé à l'étape précédente
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]