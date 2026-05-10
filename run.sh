#!/bin/sh
javac -cp lib/core.jar -d bin $(find src -name "*.java")
java -cp bin:lib/core.jar pRPG
