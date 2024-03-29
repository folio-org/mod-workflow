# build base image
FROM openjdk:17-slim as maven

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# copy pom.xml
COPY ./pom.xml ./pom.xml

# copy components
COPY ./components ./components

# copy service
COPY ./service ./service

# install reactor modules
RUN mvn install

WORKDIR /service

# build service
RUN mvn package

# final base image
FROM openjdk:17-slim

# Upgrade to latest patch versions of packages: https://pythonspeed.com/articles/security-updates-in-docker/
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y maven && \
    apt-get clean

# set deployment directory
WORKDIR /mod-workflow

# copy over the built artifact from the maven image
COPY --from=maven /service/target/workflow-service*.jar ./mod-workflow.jar

# environment
ENV SERVER_PORT='9000'

# expose ports
EXPOSE ${SERVER_PORT}
EXPOSE 61616

RUN mkdir -p activemq-data

# run java command
CMD java -jar ./mod-workflow.jar
