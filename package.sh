#! /bin/bash

project=(earth berkshire PriceWaterHouse bridgewater-deduct SwissRe Barclays MunichRe all)

function packageProject(){

	dist_category=$3

	if [[ $1 == "SwissRe" ]] || [[ $1 == "Barclays" ]] || [[ $1 == "MunichRe" ]]; then

		mvn clean install -DskipCompress=true -Dzufangbao.version=$2

		cd ../$1;

		mvn clean install -Dzufangbao.version=$2

	else
		mvn -Dzufangbao.version=$2 -DskipCompress=false clean install -pl ../$1 -am -DdistTarget=$dist_category
	fi

	if [[ $? -ne 0 ]]; then
		echo -e '\033[31m打包项目['$projectName']失败，请找出原因重试！\033[0m'
		exit
	else
		echo -e '\033[33m成功打包项目['$projectName']\033[0m'
	fi
}

PROJECT_NUM=${#project[*]}

echo '目前支持打包的项目个数有['$PROJECT_NUM']个，分别如下：'

for(( i=0;i<${PROJECT_NUM};i++ ));
{
	echo $i'.'${project[$i]}
}
echo 'Deloitte项目暂不支持此命令，需要手动执行命令，步骤是 1.在zufangbao-springboot-center中执行`mvn clean install` 2.在Deloitte下执行命令`mvn clean install`'

read -p  '请输入项目的编号数字:' num

while [[ "$num" -lt 0 ]] || [[ "$num" -ge ${PROJECT_NUM} ]]; do
	read -p '输入项目编号有误，不存在该编号，请重新输入:' num
done

projectName=${project[$num]}

read -p '请输入项目['$projectName']版本号[注：可选项，如果不填入将默认使用1.0.0-SNAPSHOT作为项目的版本号]:' version

if [[ -z $version ]]; then
	echo '将默认使用1.0.0-SNAPSHOT'
	version="1.0.0-SNAPSHOT"
fi

# dist_category=''

if [[ ${projectName} == "earth" ]] || [[ ${projectName} == "all" ]]; then

	resource_category=(yunxin shrbank)
	declare -A resource_category_alias
	resource_category_alias[yunxin]=云信
	resource_category_alias[shrbank]=华瑞

	echo "请选择打包的资源类型:"
	i=0;
	for resouce_name in ${resource_category[*]}; do
	 # echo 'resouce_name:'${resouce_name}
	 echo $i'.'${resource_category_alias[$resouce_name]}
	 let i++
	done

	read -p '请输入需要打包资源类型的编号:' resource_index

	if [[ "$resource_index" -lt 0 ]] || [[ "$resource_index" -ge ${#resource_category[*]} ]]; then
	 	read -p '输入资源编号有误，请重新输入:' resource_index
	fi
	dist_category='dist_'${resource_category[${resource_index}]}
fi

echo '开始打包项目['$projectName']:'

if [[ $projectName == "all" ]]; then

	for projectNameItem in ${project[*]}; do

		echo 'projectNameItem :'$projectNameItem

		if [[ $projectNameItem != "all" ]] ; then

				packageProject $projectNameItem $version $dist_category
		fi
	done

else
	packageProject $projectName $version $dist_category
fi
