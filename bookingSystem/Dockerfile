# Etapa 1: Construcción del proyecto usando Maven y JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y resolver dependencias
COPY pom.xml .

# Descargar las dependencias del proyecto
RUN mvn dependency:go-offline

# Copiar el código fuente del proyecto
COPY src ./src

# Compilar y empaquetar la aplicación (genera un archivo JAR)
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen para ejecutar el JAR con JDK 21
FROM eclipse-temurin:21-jdk

# Establecer el directorio de trabajo para el runtime
WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que correrá la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
