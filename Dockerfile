####
# Etapa 1: Build da aplicação
# Usa uma imagem que já vem com Maven e a JDK correta.
####
FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-17 AS build

# Copia os arquivos de configuração do Maven
COPY --chown=quarkus:quarkus mvnw .
COPY --chown=quarkus:quarkus .mvn .mvn
COPY --chown=quarkus:quarkus pom.xml .

# ADICIONE ESTA LINHA PARA DAR PERMISSÃO DE EXECUÇÃO
RUN chmod +x mvnw

# Baixa as dependências (isso otimiza o cache do Docker)
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.6.1:go-offline

# Copia o código-fonte e compila o projeto
COPY src src
RUN ./mvnw -B package -DskipTests

####
# Etapa 2: Imagem final
# Usa uma imagem mínima, apenas com o necessário para rodar.
####
FROM quay.io/quarkus/quarkus-micro-image:1.0
WORKDIR /work/

# Copia os artefatos compilados da etapa de build
COPY --from=build /home/quarkus/src/quarkus-app/ ./

RUN chown -R 1001 /work \
    && chmod -R "g+rwX" /work \
    && chown -R 1001:0 /work

EXPOSE 8080
USER 1001

# Comando para iniciar a aplicação
CMD ["java", "-jar", "quarkus-run.jar"]