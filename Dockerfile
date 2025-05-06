
FROM sbtscala/scala-sbt:eclipse-temurin-alpine-23.0.2_7_1.10.11_3.3.5
WORKDIR /API
COPY build.sbt .
RUN mkdir project && echo "" > project/build.properties
RUN sbt update
COPY . .
EXPOSE 9090 9094
CMD ["sbt", "run"]