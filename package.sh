#! /bin/bash

if [[ $# == 0 ]] ; then

	echo -e '\e[1;41m use ./package.sh earth ==> aim to package earth project to war \e[0;m'

	exit
fi

echo -e '\e[1;43m begin to package project '$1' \e[0;m'

version="1.0.0-SNAPSHOT"

if [[ -n $2 ]]; then
	
	version=$2"-RELEASE";
	
fi

if [[ $1 == "all" ]] ; then
	mvn -Dzufangbao.version=$version -DskipCompress=false clean package
else

	mvn -Dzufangbao.version=$version -DskipCompress=false clean package -pl ../$1 -am

fi
echo -e '\e[1;43m end to package project '$1' \e[0;m'
