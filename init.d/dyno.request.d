#!/bin/bash

host=$(hostname)

if [[ "$host" == "dynocloud" ]]
then                    
           install_dir="/home/dyno/DynoCloud/Daemons/"
           user="dyno"
else
        if [[ "$host" == "raspberrypi" ]]
        then                    
                install_dir="/home/pi/DynoCloud/Daemons/"
                user="pi"

        else
                if [[ "$host" == "AEGONAR-G750JX" ]]
                then                    
                        install_dir="/home/agonar/DynoCloud/Git/DynoCloud/Daemons/"
                        user="agonar"

                else
                        echo "Unknow Server host, program location path is unavailable."
                        exit 1
                fi
        fi
fi

       currentUser=$(whoami)

case "$1" in
        start)
            running=$(ps -elf | grep com.dynocloud.node.request.Daemon | grep -v grep | wc -l)

            if [[ $running -gt 0 ]]
            then   
                echo "Request Daemon already running"
            else

                echo "Starting Request Daemon"

                if [[ $currentUser == $user ]]
                then                    
                        touch "${install_dir}/request.log"
                        "${install_dir}RequestDaemon_CentralNode.sh" >> "${install_dir}/request.log" 2>&1 &
                        echo "Done."
                else
                        su $user -c "touch "${install_dir}/request.log""
                        su $user -c ""${install_dir}RequestDaemon_CentralNode.sh" >> "${install_dir}/request.log" 2>&1 &"
                        echo "Done."
                fi
            fi
        ;;
        stop) 
        echo "Stopping Request Daemon"
        
                pkill -f com.dynocloud.node.request.Daemon

        echo "Done."
        ;;
        restart)
            echo "Restarting Request Daemon"

                if [[ $currentUser == $user ]]
                then                    
                        pkill -f com.dynocloud.node.request.Daemon
                        sleep 1
                        touch "${install_dir}/request.log"
                        "${install_dir}RequestDaemon_CentralNode.sh" >> "${install_dir}/request.log" 2>&1 &
                else
                        pkill -f com.dynocloud.node.request.Daemon
                        sleep 1
                        su $user -c "touch "${install_dir}/request.log""
                        su $user -c ""${install_dir}RequestDaemon_CentralNode.sh" >> "${install_dir}/request.log" 2>&1 &"
                fi

        echo "Done."
        ;;
        *)
        echo "Usage: /etc/init.d/mosquitto start|stop|restart"
        exit 1
        ;;
esac