FROM openjdk:17
COPY out/production/algorithms/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "Main"]