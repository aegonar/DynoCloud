#!/bin/bash

host=$(hostname)

if [[ "$host" == "dynocloud" ]]
then                    
           export install_dir="/home/dyno/DynoCloud/Daemons/"
else
        if [[ "$host" == "raspberrypi" ]]
        then                    
                export install_dir="/home/pi/DynoCloud/Daemons/"

        else
                if [[ "$host" == "AEGONAR-G750JX" ]]
                then                    
                        export install_dir="/home/agonar/DynoCloud/Git/DynoCloud/Daemons/"

                else
                        echo "Unknow Server host, program location path is unavailable."
                        exit 1
                fi
        fi
fi
	
case "$1" in
	start)
        echo "Starting mosquitto"
        touch "${install_dir}/Mosquitto.log"
        	#nohup mosquitto > /dev/null 2>&1 &
        mosquitto >> "${install_dir}/Mosquitto.log" 2>&1 &
        echo "Done."
        ;;
	stop) 
        echo "Stopping mosquitto"
        killall mosquitto
        echo "Done."
        ;;
	restart)
	    echo "Restarting mosquitto"
	    killall mosquitto
	    sleep 1
        touch "${install_dir}/Mosquitto.log"
        	#nohup mosquitto > /dev/null 2>&1 &
        mosquitto >> "${install_dir}/Mosquitto.log" 2>&1 &
        echo "Done."
        ;;
	*)
        echo "Usage: /etc/init.d/mosquitto start|stop|restart"
        exit 1
        ;;
esac