FROM khipu/openjdk17-alpine
# VOLUME /root/images
ARG spring_datasource_url
ARG spring_datasource_username
ARG spring_datasource_password
ENV SPRING_DATASOURCE_URL=${spring_datasource_url}
ENV SPRING_DATASOURCE_USERNAME=${spring_datasource_username}
ENV SPRING_DATASOURCE_PASSWORD=${spring_datasource_password}
CMD ["./gradlew","clean","build"]
ARG JAR_FILE=./build/libs/board-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} spring.jar
# VOLUME과의 차이점 인지
RUN ["mkdir","/root/images"]
ENTRYPOINT ["java", "-jar","-Duser.timezone=Asia/Seoul","spring.jar"]
