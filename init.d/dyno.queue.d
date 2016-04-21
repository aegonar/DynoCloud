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
        echo "Starting Queue Daemon"
        nohup "${install_dir}QueueDaemon_CentralNode.sh" > /dev/null 2>&1 &
        echo "Done."
        ;;
	stop) 
        echo "Stopping Queue Daemon"
        pkill -f com.dynocloud.node.queue.Daemon
        echo "Done."
        ;;
	restart)
	    echo "Restarting Queue Daemon"
	    pkill -f com.dynocloud.node.queue.Daemon
	    sleep 1
        nohup "${install_dir}QueueDaemon_CentralNode.sh" > /dev/null 2>&1 &
        echo "Done."
        ;;
	*)
        echo "Usage: /etc/init.d/dyno.queue.d start|stop|restart"
        exit 1
        ;;
esac