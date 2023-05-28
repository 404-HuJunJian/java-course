#java 版本
FROM openjdk:8
#前者是要操作的jar包  后者自定义jar包名
ADD sentinel-dashboard-1.8.5.jar sentinel-dashboard.jar
#定义时区参数
ENV TZ=Asia/Shanghai
#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone
#暴露端口
EXPOSE 9900 8719
#配置启动命令,-D表示设置JVM参数
ENTRYPOINT ["java","-jar","-Dserver.port=9900","-Dcsp.sentinel.dashboard.server=localhost:9900","-Dproject.name=sentinel-dashboard","/sentinel-dashboard.jar"]