#!/usr/bin/env bash

DATE=`date +%Y-%m-%d:%H:%M:%S`
SERVER=ec2-user@ec2-52-43-90-109.us-west-2.compute.amazonaws.com
LEVEL_NAME=final2
echo "=============="
echo "Moving $LEVEL_NAME to $LEVEL_NAME.$DATE"
echo "=============="
ssh $SERVER mv /home/ec2-user/$LEVEL_NAME/ /home/ec2-user/backups/$LEVEL_NAME.$DATE
echo "=============="
echo "moving $LEVEL_NAME to $SERVER"
echo "=============="
scp -rp $LEVEL_NAME/ $SERVER:~/
echo "=============="
echo "done"
echo "=============="
