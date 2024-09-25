FROM maven:3.9.8-eclipse-temurin-21 AS build
RUN mkdir /opt/app
COPY . /opt/app
WORKDIR /opt/app

RUN mvn clean install

FROM eclipse-temurin:21-jre-alpine
RUN mkdir /opt/app

COPY --from=build  /opt/app/target/ProjetoMedicaoAutomatica*.jar /opt/app/ProjetoMedicaoAutomatica*.jar
WORKDIR /opt/app

ENV PROFILE=prd

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.progfiles.active=${PROFILE}", "-jar", "app.jar"]