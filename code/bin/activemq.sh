#!/usr/bin/env bash

docker run --name sb3r-activemq -p 61616:61616 -p 8161:8161 -p 61613:61613 -p 61614:61614 -d rmohr/activemq:5.15.4-alpine
