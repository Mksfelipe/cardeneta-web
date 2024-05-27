# GNU nano 7.2                                                                                        Dockerfile
# Use a imagem base do OpenJDK para Java 17
FROM openjdk:17

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR da sua aplicação para o diretório de trabalho no contêiner
COPY target/*.jar app.jar

# Copie o arquivo keystore.p12 para o diretório /app/ssl dentro do contêiner
COPY /etc/letsencrypt/live/www.felipe-sousa-dev.com/keystore.p12 /app/ssl/

# Defina o comando padrão a ser executado quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]
