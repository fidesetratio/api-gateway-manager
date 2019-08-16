#!/bin/bash
if [ -f /opt/manager2/pid.file ]; then

 if ps -p $(cat /opt/manager2/pid.file) > /dev/null;then
        kill $(cat /opt/manager2/pid.file) > /dev/null
        echo 'kill them...'
 fi

fi
