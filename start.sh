#!/bin/bash
nohup java -jar /home/patartimotius/applicationtesting/apimanager/manager-0.0.1-SNAPSHOT.jar > /home/patartimotius/applicationtesting/apimanager/log.txt 2>&1 &
echo $! > /home/patartimotius/applicationtesting/apimanager/pid.file