#! /bin/bash

project=(earth berkshire PriceWaterHouse bridgewater-deduct SwissRe Barclays MunichRe bridgewater-remittance  Barclays-crawler all)

function packageProject(){

	dist_category=$3
	projectName=$1
	version=$2

	BASE_PATH=$(cd `dirname $0`;cd ..;pwd)

	# echo 'BASE_PATH:'$BASE_PATH

	if [[ $projectName == "SwissRe" ]] || [[ $projectName == "Barclays" ]] || [[ $projectName == "MunichRe" ]] || [[ $projectName == "bridgewater-remittance" ]] || [[ $projectName == "Barclays-crawler" ]]; then

		mvn clean install -DskipCompress=true -Dzufangbao.version=$version

		cd $BASE_PATH/$projectName;

		mvn clean install -Dzufangbao.version=$version

	else
		cd $BASE_PATH/zufangbao-springboot-center
		mvn -Dzufangbao.version=$version -DskipCompress=false clean install -pl ../$projectName -am -DdistTarget=$dist_category
	fi

	if [[ $? -ne 0 ]]; then
		echo -e '\033[31m打包项目['$projectName']失败，请找出原因重试！\033[0m'
		exit
	else
		echo -e '\033[33m成功打包项目['$projectName']\033[0m'
	fi
}

function printOneLineInfo(){
	info=''
	index=0
	for item in $@ ; do
		if [[ $index -eq 0 ]] ; then
			info=${item}'['
		else
			info+=${item}'|'
		fi
		let index++
	done
	info+=']'

	echo $info
}

PROJECT_NUM=${#project[*]}
resource_category=(yunxin shrbank)
declare -A resource_category_alias
resource_category_alias[yunxin]="信托(类似云信)"
resource_category_alias[shrbank]="信贷(类似华瑞)"

if [[ $# -eq 0 ]]; then

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

else 

	if [[ $# != 3 ]]; then
		echo '传入的参数个数不正确，正确如下：'

		dist_category_line_info=`printOneLineInfo ${resource_category[*]}`
		dist_category_alias_line_info=`printOneLineInfo ${resource_category_alias[*]}`

		for(( i=0;i<${PROJECT_NUM};i++ ));
		{
			projectName=${project[$i]}
			echo $i'./package.sh '${projectName}' 1.0.0-SNAPSHOT '$dist_category_line_info' ===> 将打包'${dist_category_alias_line_info}'资源的'$projectName'项目包,版本为1.0.0-SNAPSHOT'
		}

		exit 
	else

		projectName=$1
		version=$2
		dist_category=$3
	fi
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
