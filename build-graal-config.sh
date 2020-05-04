#!/usr/bin/env bash
./install-graal.sh
export JAVA_HOME=$HOME/.m2/caches/info.picocli.graal/graalvm-ce-java11-19.3.0.2
rm -rf src/main/resources/graal-config/*
mvn clean compile assembly:single
$JAVA_HOME/bin/java -agentlib:native-image-agent=config-output-dir=src/main/resources/graal-config -jar target/google-search-cli.jar
