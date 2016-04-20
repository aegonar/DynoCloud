#!/bin/bash

# Central Node Telemetry Daemon script

echo
echo ========================================================================================================
echo
echo "              888888ba                              a88888b. dP                         dP            "
echo "              88    '8b                            d8'   '88 88                         88            "
echo "              88     88 dP    dP 88d888b. .d8888b. 88        88 .d8888b. dP    dP .d888b88            "
echo "              88     88 88    88 88'  '88 88'  '88 88        88 88'  '88 88    88 88'  '88            "
echo "              88    .8P 88.  .88 88    88 88.  .88 Y8.   .88 88 88.  .88 88.  .88 88.  .88            "
echo "              8888888P  '8888P88 dP    dP '88888P'  Y88888P' dP '88888P' '88888P' '88888P8            "
echo "                             .88                                                                      "
echo "                         d8888P                                                                       "
echo
echo ========================================================================================================


#Run as ./

#Stop program Execution
stop_program=true

############################################################
#Program location path setup

host=$(hostname)

if [[ "$host" == "raspberrypi" ]]
then			
	   export install_dir="/home/pi/DynoCloud/"
	   export MQTThost="localhost"
else
	if [[ "$host" == "AEGONAR-G750JX" ]]
	then 	   		
	   		export install_dir="/home/agonar/DynoCloud/Git/DynoCloud/Java/"
	   		export MQTThost="192.168.0.199"
	else
			echo "Unknow Server host, program location path is unavailable."
		exit 1
	fi
fi

echo "Server Host: ${host}, program location path: ${install_dir}"
echo --------------------------------------------------------------------------------------------------------

############################################################
#External JARs

#Mysql Connector
export CLASSPATH="${CLASSPATH}:${install_dir}External Jars/mysql-connector-java-5.1.18-bin.jar"

#MQTT Lib
export CLASSPATH="${CLASSPATH}:${install_dir}External Jars/mqtt-client-java1.4-uber-1.7.jar"

############################################################
#Application Paths

#Telemetry Daemon
export CLASSPATH="${CLASSPATH}:${install_dir}CentralNodeTelemetryDaemon/bin"

########################################################################################################################
# echo ""
# echo ""
# echo ""
# echo ""
# echo ""
# echo ""
########################################################################################################################

#Telemetry Daemon
daemon="com.dynocloud.node.telemetry.Daemon"

echo "Start Program" $daemon 

java -cp "$CLASSPATH" "$daemon" $MQTThost 2>&1 | tee  "${install_dir}CentralNodeTelemetryDaemon/Telemetry.log"

program_status=${PIPESTATUS[0]} 
if $stop_program; then
	if [[ $program_status != 0 ]]
		then echo "There was an exception, script stopping"
		exit 1
	fi
fi

echo "End Program" $daemon
echo
echo --------------------------------------------------------------------------------------------------------

########################################################################################################################