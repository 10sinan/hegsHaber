FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Maven wrapper ve pom.xml'i kopyala
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Maven wrapper'a execute yetkisi ver
RUN chmod +x mvnw

# Bağımlılıkları önce indir (cache için)
RUN ./mvnw dependency:go-offline -B

# Kaynak kodları kopyala
COPY src ./src

# Uygulamayı build et
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Build stage'den jar dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

# Port'u expose et
EXPOSE 8080

# Uygulamayı başlat
ENTRYPOINT ["java","-jar","app.jar"]

