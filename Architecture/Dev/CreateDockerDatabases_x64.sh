#!/bin/bash

# Name : CreateDockerDatabases.sh
# Author : Matthieu Sauboua-Beneluz
# Description : Create the mongodb container to use with this app for testing


### To connect manually, install mongo on your host

###############################
#### MONGODB
###############################

# -- Creating and launching container
docker run -p 28042:27017 --name spring_mongo_instance_01 -d mongo

# -- Identification (Command line)
# mongo --port 28042
