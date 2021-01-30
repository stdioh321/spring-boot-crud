FROM gradle:jre15-hotspot as builder
COPY . /tmp
WORKDIR /tmp
RUN gradle build -x test

FROM openjdk:11-jre-slim as runtime
COPY --from=builder /tmp/build/libs/crud.war /
EXPOSE 8080 5005
CMD java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar crud.war