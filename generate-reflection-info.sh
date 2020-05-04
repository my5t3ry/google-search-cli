#!/usr/bin/env bash
rm -rf src/main/resources/graal-config/*
mvn clean compile assembly:single
$JAVA_HOME/bin/java -agentlib:native-image-agent=config-output-dir=src/main/resources/graal-config -jar target/google-search-cli.jar

