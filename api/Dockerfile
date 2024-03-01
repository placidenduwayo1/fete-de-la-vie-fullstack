FROM maven:3.8-jdk-11-slim AS build

# Par défaut:
ARG POSTFIX="dev"

ENV BUILD_ENV=$POSTFIX

WORKDIR /usr/src/app

COPY pom.xml .

RUN mvn dependency:go-offline --fail-never --update-snapshots

COPY src ./src

RUN test -z "$BUILD_ENV" && echo "BUILD_ENV is empty: $BUILD_ENV" || echo "BUILD_ENV is not empty: $BUILD_ENV"

RUN [ -z "$BUILD_ENV" ] && mvn -DskipTests=true package || mvn -DskipTests=true package -P $BUILD_ENV

FROM openjdk:11-jre-slim AS run

# Copy built jar from build stage
COPY --from=build /usr/src/app/target/*.jar /target/app.jar

WORKDIR /usr/src/app

# Set the user and group to non-root
RUN groupadd -r appuser && useradd -r -g appuser appuser
USER appuser

EXPOSE 8080

ENTRYPOINT ["java","-jar","/target/app.jar"]