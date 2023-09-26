FROM khipu/openjdk17-alpine
CMD [".gradlew", "clean", "build"]
ARG JAR_FILE=./build/libs/board-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} "spring.jar"
RUN mkdir /root/images
ENTRYPOINT java -jar -Duser.timezone=Asia/Seoul spring.jar
#README.md
# 민감한 정보는 dockerfile에 기입X
# build-time말고 run-time에 주입받도록 해야함
# run --mount=type=secret,id=myscret 은 영속성이없는 일회성 작업(git clone, ssh연결 등)에만 쓰임.
