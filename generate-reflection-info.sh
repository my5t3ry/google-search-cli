#!/usr/bin/env bash
rm -rf src/main/resources/*
mvn clean compile assembly:single
$JAVA_HOME/bin/java -agentlib:native-image-agent=config-output-dir=src/main/resources/ -jar target/google-search-cli.jar

