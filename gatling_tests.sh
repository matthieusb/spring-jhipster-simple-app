#!/usr/bin/env bash

# This script is here to launch load tests
# It starts the application before laucnhing them
# Author : Matthieu Sauboua-Beneluz.

# ------------------------------
# WARNING : this script checks if the application is correctly started
# using the application port, it won't give the expected/relevant
# results if something is already up on the used port.
# ------------------------------

# ########### FUNCTIONS
trim(){
    [[ "$1" =~ ^[[:space:]]*(.*[^[:space:]])[[:space:]]*$ ]]
    printf "%s" "${BASH_REMATCH[1]}"
}

# ########### MAIN PROGRAM

# ------ First, launch the spring-boot app in background and get its pid
mvn spring-boot:run > /tmp/apertureapi-loadtestscript.log 2>&1 &
API_PID=$!

# ------ Then, wait for it to be correctly initialized

portExtracted=`awk '/server/,/port/' ./src/main/resources/application.yml | cut -d ":" -f 2`
#portExtracted=`grep "port" ./src/main/resources/application.yml | cut -d ":" -f 2`
portToListenTo=`trim ${portExtracted}`

i=0
maxCounter=100
while [[ ! $(lsof -i :"$portToListenTo") ]] && [ ${i} -lt ${maxCounter} ]
do
    echo 'Waiting for spring-boot app to be up and running.'
    sleep 2
    i=$[$i+1]
done

# ------ Execute the gatling tests
mvn gatling:execute

# ------ Stop the spring boot api
kill $API_PID




