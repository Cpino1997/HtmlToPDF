FROM pinolabs/java AS builder

RUN apk update && \
    apk upgrade
COPY src /app/src
COPY pom.xml /app/pom.xml

RUN cd /app && \
    mvn package -DskipTests

FROM pinolabs/java

# Copia el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar iamPDF.jar
# Expone el puerto 8080 para acceder a la aplicación
EXPOSE 8080

# Configura el comando de inicio del contenedor
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/iamPDF.jar","--spring.config.location=file:///default-app/application.properties"]