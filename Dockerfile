# base img
FROM openjdk:8-jdk-alpine
# configure at pom.xml
ARG JAR_FILE
#
COPY target/lrx-exam-0.0.1-SNAPSHOT.jar /opt/lrx_test.jar
#COPY ${JAR_FILE} /opt/lrx_test.jar
#
ENV DATAPATH /data
# mount
VOLUME $DATAPATH
# execute
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/lrx_test.jar"]
EXPOSE 80