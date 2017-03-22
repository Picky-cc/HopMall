#! /bin/bash

project=(earth berkshire PriceWaterHouse bridgewater-deduct SwissRe Barclays MunichRe all)

function packageProject(){

	if [[ $1 == "SwissRe" ]] || [[ $1 == "Barclays" ]] || [[ $1 == "MunichRe" ]]; then

		mvn clean install -DskipCompress=true -Dzufangbao.version=$2

		cd ../$1;

		mvn clean install -Dzufangbao.version=$2

	else
		mvn -Dzufangbao.version=$2 -DskipCompress=false clean install -pl ../$1 -am
	fi

	if [[ $? -ne 0 ]]; then
		echo -e '[0;31m 打包项目['$projectName']失败，请找出原因重试！[0m'
		exit
	else
		echo '成功打包项目['$projectName']'
	fi
}

echo '目前支持打包的项目如下：'

for(( i=0;i<${#project[*]};i++ )); 
{
	echo $i'.'${project[$i]}
}
echo 'Deloitte项目占不支持此命令，需要手动执行命令，步骤是 1.在zufangbao-springboot-center中执行`mvn clean install` 2.在Deloitte下执行命令`mvn clean install`'

echo '请输入项目的编号数字：'

read num

if [[ $num < 0 ]] || [[ $num >7 ]]; then
	echo '输入项目编号有误，不存在该编号，请确认！'
	exit
fi

projectName=${project[$num]}

echo '请输入项目['$projectName']版本号[注：可选项，如果不填入将默认使用1.0.0-SNAPSHOT作为项目的版本号]：'

read version

if [[ -z $version ]]; then
	echo '将默认使用1.0.0-SNAPSHOT'
	version="1.0.0-SNAPSHOT"
fi

echo '开始打包项目['$projectName']，确定请输入yes，否则no：'

read confirm

case $confirm in
	yes )
		
		if [[ $projectName == "all" ]]; then

			for projectNameItem in ${project[*]}; do 

				echo 'projectNameItem :'$projectNameItem
				
				if [[ $projectNameItem != "all" ]] ; then

						packageProject $projectNameItem $version
				fi
			done
		else
			packageProject $projectName $version
		fi

		;;
	no )
		echo '退出打包项目['$projectName']'
		sleep 2
		exit
		;;
esac





