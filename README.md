#视频云基础平台接口

## 1、启动命令
###直接启动
      java -jar api-management-1.0.0-jar-with-dependencies.jar
###后台启动
      nohup  java -jar api-management-1.0.0-jar-with-dependencies.jar  > server-log 2>&1 & 

##2、将resource/lib 下两个jar 包 安装到本地maven 仓库   

     mvn install:install-file -Dfile=druid-6.2.2.jar -DgroupId=com.alibaba  -DartifactId=druid  -Dversion=6.2.2 -Dpackaging=jar  
     mvn install:install-file -Dfile=elasticsearch-sql-6.2.2.jar -DgroupId=org.nlpcn  -DartifactId=elasticsearch-sql  -Dversion=6.2.2 -Dpackaging=jar 

##3、将system.properties 文件  copy  到 本机 当前目录下  例如我的是 C:\Users\31425