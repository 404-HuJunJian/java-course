FROM openjdk:8

ADD GateWay-1.0.0.jar /opt/javapackage/
ADD MessageCenter-1.0.0.jar /opt/javapackage/
ADD RegisterCenter-1.0.0.jar /opt/javapackage/
ADD UploadCenter-1.0.0.jar /opt/javapackage/
ADD WeatherServer-1.0.0.jar /opt/javapackage/
ADD GPTServer-1.0.0.jar /opt/javapackage/

COPY entrypoint.sh /opt/javapackage/

#RUN apt-get update && apt-get install -y curl
#RUN curl -fsSL https://get.docker.com -o get-docker.sh && sh get-docker.sh

EXPOSE 8800 6600 7700 5500 9000 9001

#CMD ["/opt/javapackage/entrypoint.sh"]
