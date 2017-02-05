#!/bin/bash

# This file is just a reminder for most curl syntax

# ---- FindAll GET ROUTES
curl -i -X GET localhost:8080/rooms
curl -i -X GET localhost:8080/subjects
curl -i -X GET localhost:8080/supervisors


# ---- FindBy GET ROUTES (Url parameters)

# -- ROOMS FindById with url parameter
printf "\n##################################################\n"
curl -i -X GET localhost:8080/rooms/id/42 # Does not exist
curl -i -X GET localhost:8080/rooms/id/5063114bd386d8fadbd6b00a # Exists

# -- TEST SUBJECTS FindById with url parameter
printf "\n##################################################\n"
curl -i -X GET localhost:8080/subjects/id/42 # Does not exist
curl -i -X GET localhost:8080/subjects/id/5063114bd386d8fadbd6b00d # Exists

# -- TEST SUPERVISORS FindById with url parameter
printf "\n##################################################\n"
curl -i -X GET localhost:8080/supervisors/id/42 # Does not exist
curl -i -X GET localhost:8080/supervisors/id/5063114bd386d8fadbd6b004 # Does not exist


# ---- Other findBy routes

# -- ROOM FindByName
printf "\n##################################################\n"
curl -i -X POST localhost:8080/rooms/name --data "name=Test" # Does not exist
curl -i -X POST localhost:8080/rooms/name --data "name=Paths" # Exists
curl -i -X POST localhost:8080/rooms/name --data "name=Answer to life room" # Exists

# -- TestSubjects FindByName
printf "\n##################################################\n"
curl -i -X POST localhost:8080/subjects/name --data "name=42" # Does not exist
curl -i -X POST localhost:8080/subjects/name --data "name=Caroline" # Exists

# -- TestSupervisors FindByLogin
printf "\n##################################################\n"
curl -i -X POST localhost:8080/supervisors/login --data "login=42" # Does not exist
curl -i -X POST localhost:8080/supervisors/login --data "login=glados@aperture.fr" # Exists


