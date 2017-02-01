#!/bin/bash

# Name : CreateDockerDatabases.sh
# Author : Matthieu Sauboua-Beneluz
# Description : Script to just launch all docker containers and show some help to access them

red=`tput setaf 1`
green=`tput setaf 2`
reset=`tput sgr0`
white_bold=`tput bold`

# -- Variables
docker_containers=( "spring_mongo_instance_01" )

# -- Starting Instances
_launch_all_instances() {

# In this specific project, shutting down a possible mongo daemon on the local machine
sudo /etc/init.d/mongodb stop

# Starting all instances except if they are running)
	for i in "${docker_containers[@]}"
		do
			container_is_running=`docker inspect -f {{.State.Running}} $i`
			case $container_is_running in
			  true)
				echo "${green}$i container is already running${reset}"
				;;
			  false)
				echo "${red}$i container is not running, launching it !${reset}"
				docker start "$i" >/dev/null
				;;
			esac
		done
}

_kill_all_instances() {
# Killing all instances except if they are not running
	for i in "${docker_containers[@]}"
		do
			container_is_running=`docker inspect -f {{.State.Running}} $i`
			case $container_is_running in
			  true)
				echo "${red}$i container is running, killing it !${reset}"
				docker kill "$i" >/dev/null
				;;
			  false)
				echo "${green}$i container is not running${reset}"
				;;
			esac
		done
}

# -- Connection instructions
_display_help_connection() {
	echo 'This scripts allows you to start and kill this projects docker instances. Using it is not mandatory if you know your way around the containers.'

	echo '###### SCRIPT ARGUMENTS ######'
	echo '-h -> Display help'
	echo '-i -> Launch all containers'
	echo '-k -> Kill all containers'
	echo ''

	echo '###### CUSTOMIZE SCRIPT ######'
	echo "Change the ${green} docker_containers ${reset} variable in this script to start/stop only the containers you want"

	echo '###### CONNECTION INSTRUCTIONS ######'

	echo '------- Mongodb'
	echo 'mongo --port 28042'
}

# Handles the entry arguments, takes one parameter (The entry argument $1 variable)
_arg_handling() {
    case $1 in
		-i|--install)
			_launch_all_instances
			;;
		-k|--kill)
			_kill_all_instances
			;;
		-h|--help)
			_display_help_connection
			;;
		*)
			echo "Argument is not valid, use -h for more information"
	esac
}

_arg_handling $1
