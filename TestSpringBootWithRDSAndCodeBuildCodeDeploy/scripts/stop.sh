#!bin/bash

kill -9 $(ps -aux | grep -v grep | grep TestSpring | awk '{print $2}')
