#!/usr/bin/env bash

./gradlew fatjar && \
    java -Djava.util.logging.config.file=src/main/resources/logging.properties -jar build/libs/whobot.jar token.txt