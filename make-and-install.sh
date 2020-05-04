#!/usr/bin/env bash
#./generate-reflection-info.sh
mvn -DbuildArgs=--no-server clean verify
sudo cp target/google-search-cli /usr/bin/
