FROM	java:8
ADD     target/homepage*.jar /root/homepage.jar

EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xms128m", "-Xmx256m","/root/homepage.jar"]