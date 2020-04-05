#!/bin/bash
mvn install:install-file -Dfile=/tmp/ojdbc8.jar -DgroupId=com.oracle.ojdbc \
    -DartifactId=ojdbc8 -Dversion=18c -Dpackaging=jar
