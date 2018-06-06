FROM anapsix/alpine-java
EXPOSE 443
COPY target/whobot-1.0-SNAPSHOT-jar-with-dependencies.jar /home/whobot.jar
CMD ["java","-jar","/home/whobot.jar"]
