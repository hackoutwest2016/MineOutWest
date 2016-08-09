#!/usr/bin/env bash

DATE=`date +%Y-%m-%d:%H:%M:%S`
SERVER=ec2-user@ec2-52-43-90-109.us-west-2.compute.amazonaws.com
JAR_NAME=mineoutwest-1.0.jar
echo "=============="
echo "=============="
./gradlew build
echo "=============="
echo "backing up old jar"
echo "=============="
ssh $SERVER mv /home/ec2-user/mods/$JAR_NAME /home/ec2-user/backups/$JAR_NAME.$DATE
echo "=============="
echo "moving newjar"
echo "=============="
scp build/libs/$JAR_NAME $SERVER:~/mods/
echo "=============="
echo "done"
echo "=============="
