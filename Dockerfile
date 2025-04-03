FROM	eclipse-temurin:23.0.1_11-jre-ubi9-minimal
ADD     build/libs/homepage-1.21.0.jar /root/homepage.jar

EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/root/homepage.jar"]