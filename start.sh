#!/bin/bash

nohup java -jar /opt/manager2/manager-0.0.1-SNAPSHOT.jar > /opt/manager2/log.txt 2>&1 &
echo $! > /opt/manager2/pid.file
