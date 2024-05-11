# Use a imagem oficial do OpenJDK 17
FROM openjdk:17-jdk-alpine

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o jar da sua aplicação para o contêiner
COPY target/cardeneta-web-0.0.1-SNAPSHOT.jar /app/cardeneta-web-0.0.1-SNAPSHOT.jar

# Porta exposta pela aplicação
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "cardeneta-web-0.0.1-SNAPSHOT.jar"]
