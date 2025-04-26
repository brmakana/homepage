FROM	eclipse-temurin:24.0.1_9-jre-ubi9-minimal
ADD     build/libs/homepage-*.jar /root/homepage.jar

EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/root/homepage.jar"]