#!/bin/bash

OS=""
case "$OSTYPE" in
  solaris*) OS="SOLARIS" ;;
  darwin*)  OS="OSX" ;;
  linux*)   OS="LINUX" ;;
  bsd*)     OS="BSD" ;;
  *)        OS="unknown: $OSTYPE" ;;
esac
echo $OS

if screen -list | grep -q "redis"; then
    echo 'redis process running'
else
    redis_server_script="./redis-3.2.0/src/redis-server"
    if [ -e $redis_server_script ]; then
        echo "redis by var exists"
    else
        if [ ! -e "./redis-3.2.0" ]; then
            if [ "$OS" == "OSX" ]; then
                curl -o "redis-3.2.0.tar.gz" "http://download.redis.io/releases/redis-3.2.0.tar.gz"
            else
                wget "http://download.redis.io/releases/redis-3.2.0.tar.gz"
            fi
            tar -xvzf redis-3.2.0.tar.gz
        fi
        cd redis-3.2.0
        make
        cd ../
    fi
    echo 'run redis'
    screen -S redis -d -m redis-3.2.0/src/redis-server redis-3.2.0/redis.conf
    rm redis-3.2.0.tar.gz
fi
