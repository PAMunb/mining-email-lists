#!/bin/bash

#docker run --rm -it --name alimentacao-bd -p 27020:27017 unbesv/alimentacaodb:latest

docker run --rm -it -p 3306:3306 --name scrap-bd -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=scrap -e MYSQL_USER=spring_user -e MYSQL_PASSWORD=root mysql:8.0.33-debian
