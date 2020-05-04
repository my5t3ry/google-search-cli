#!/usr/bin/env bash
./install-graal.sh
export JAVA_HOME=$HOME/.m2/caches/info.picocli.graal/graalvm-ce-java11-19.3.0.2
mvn -DbuildArgs=--no-server clean verify
sudo cp target/google-search-cli /usr/bin/
