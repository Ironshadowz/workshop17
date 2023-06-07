#
# First stage
#

FROM maven:3.8.3-openjdk-17 AS build

COPY src /home/app/src
COPY pom.xml /home/app

ARG REDISHOST
ARG REDISPORT
ARG REDISUSER
ARG REDISPASSWORD

RUN mvn -f /home/app/pom.xml clean package

#
# second stage
#

FROM openjdk:17-oracle

ARG REDISHOST
ARG REDISPORT
ARG REDISUSER
ARG REDISPASSWORD

COPY --from=build \Users\Wei Liang\OneDrive\Desktop\VTTL Learning\SSF\ibfb3-ssf\workshop17\target\workshop17-0.0.1-SNAPSHOT.jar \Users\Wei Liang\OneDrive\Desktop\VTTL Learning\SSF\ibfb3-ssf\workshop17\target\workshop17.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/workshop17.jar"]