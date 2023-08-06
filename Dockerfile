FROM openjdk:17
EXPOSE 8199
LABEL maintainer="olamireDev on Github.com"
COPY target/prophius-apiV1.jar prophius-apiV1.jar
ENTRYPOINT ["java","-jar", "/prophius-apiV1.jar"]