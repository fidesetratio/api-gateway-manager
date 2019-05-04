#!/bin/bash
if [ -f /home/patartimotius/applicationtesting/apimanager/pid.file ]; then
	
 if ps -p $(cat /home/patartimotius/applicationtesting/apimanager/pid.file) > /dev/null;then
 	kill $(cat /home/patartimotius/applicationtesting/apimanager/pid.file) > /dev/null
	echo 'kill them...'
 fi
 
fi
