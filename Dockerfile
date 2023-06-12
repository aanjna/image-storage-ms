FROM openjdk:17
EXPOSE 8080
ADD target/image-storage-ms-*.jar image-storage-ms-*.jar
ENTRYPOINT ["java","-jar","/image-storage-ms-*.jar"]