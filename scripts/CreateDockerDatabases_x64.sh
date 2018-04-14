#!/bin/bash

# Name : CreateDockerDatabases.sh
# Author : Matthieu Sauboua-Beneluz
# Description : Create the mongodb container to use with this app for testing


### To connect manually, install mongo on your host

###############################
#### MONGODB
###############################

# -- Stopping any mongo local service that has nothing to do with docker
sudo /etc/init.d/mongodb stop

# -- Creating and launching container
docker run -p 27017:27017 --name spring_mongo_instance_01 -d mongo:3.4.10

# -- Identification (Command line)
# mongo --port 27017
