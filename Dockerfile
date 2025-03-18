# Sử dụng OpenJDK 21 làm base image
FROM eclipse-temurin:21-jdk AS build

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Biên dịch và đóng gói ứng dụng
RUN ./mvnw clean package -DskipTests

# Sử dụng OpenJDK 21 runtime image nhẹ hơn để chạy ứng dụng
FROM eclipse-temurin:21-jre

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy file JAR từ bước build trước
COPY --from=build /app/target/*.jar app.jar

# Mở port ứng dụng
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
