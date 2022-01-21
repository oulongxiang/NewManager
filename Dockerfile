FROM openjdk:8u275-oracle

# 将本地文件夹挂在到当前容器
VOLUME /tmp

# 复制文件到容器
ADD BaseModel/target/BaseModel-0.0.1.jar audit_system.jar

# 声明需要暴露的端口
EXPOSE 8972

# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Duser.timezone=Asia/ShangHai","-jar","audit_system.jar"]

