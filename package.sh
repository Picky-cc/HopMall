#! /bin/bash

if [[ $# == 0 ]] ; then

	echo -e '\e[1;41m use ./package.sh earth ==> aim to package earth project to war \e[0;m'

	exit
fi

echo -e '\e[1;43m begin to package project '$1' \e[0;m'

mvn clean package -pl ../$1 -am

echo -e '\e[1;43m end to package project '$1' \e[0;m'
