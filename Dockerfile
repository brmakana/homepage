FROM	openjdk:10-jdk
ADD     target/homepage*.jar /root/homepage.jar

EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/root/homepage.jar"]