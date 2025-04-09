
FROM sbtscala/scala-sbt:eclipse-temurin-23.0.2_7_1.10.11_3.3.5
WORKDIR API/
COPY . .
RUN sbt update
EXPOSE 9090 9094
CMD ["sbt", "run"]