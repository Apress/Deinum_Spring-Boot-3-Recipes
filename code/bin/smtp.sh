#!/usr/bin/env bash

docker run --name sb3r-greenmail -t -i -p 8080:8080 -p 3025:3025 -p 3110:3110 -p 3143:3143 -p 3465:3465 -p 3993:3993 -p 3995:3995 -d greenmail/standalone:2.0.1
