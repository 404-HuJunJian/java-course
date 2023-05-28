#! /bin/sh
echo "#################Start GateWay####################"
java -jar /opt/javapackage/GateWay-1.0.0.jar
echo "#################Start RegisterCenter####################"
java -jar /opt/javapackage/RegisterCenter-1.0.0.jar
echo "#################Start MessageCenter####################"
java -jar /opt/javapackage/MessageCenter-1.0.0.jar
echo "#################Start GPTServer####################"
java -jar /opt/javapackage/GPTServer-1.0.0.jar
echo "#################Start WeatherServer####################"
java -jar /opt/javapackage/WeatherServer-1.0.0.jar
echo "#################Start UploadCenter####################"
java -jar /opt/javapackage/UploadCenter-1.0.0.jar

