#!/bin/bash

cd target

nohup java -jar cardeneta-web-0.0.1-SNAPSHOT.jar > spring-boot.log 2>&1 &