#!/bin/bash

# Encontrar o PID do processo que está escutando na porta 9090
pid=$(lsof -t -i:9090)


kill $pid

cd /target

nohup java -jar cardeneta-web-0.0.1-SNAPSHOT.jar &