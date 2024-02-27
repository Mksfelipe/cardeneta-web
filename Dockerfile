# Use uma imagem base com o JDK 17
FROM openjdk:17-jdk-alpine

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR da aplicação para o contêiner
COPY target/*.jar /app/cardeneta-web.jar

# Exponha a porta em que a aplicação vai estar escutando
EXPOSE 9090

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "sua-aplicacao.jar"]
