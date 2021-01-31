#!/bin/bash

if [ -n "$PORT" ]
then
  export SERVER_PORT=$PORT
fi
echo $SERVER_PORT
if [ -n "$ENABLE_DEBUG" -a "$ENABLE_DEBUG" == false ]
then
  java -jar crud.war
else
  java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar crud.war
fi