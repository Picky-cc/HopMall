#! /bin/bash
###
###	初始化所有的项目
###

function printProjectStaticsInfoAndRemoveFile(){

	if [[ -f $2 ]]; then
		echo $1'的项目数为'`cat $2 | wc -l`'个,分别如下：'
		cat "$2"
	else
		echo $1'的项目数为0个'
	fi

	rm -f $2

}

function checkToolExist(){

	if which $1 > /dev/null; then
		echo $1'安装成功！'
	else
		echo -e "\033[31m '$1'没有安装，请安装！\033[0m"
		exit
	fi
}

function cloneProject(){

	url=$1;

	projectItem=${url##*\/}

	branch=$2

	if [[ $projectItem == "all" ]]; then
		return;
	fi

	if [[ -d $projectItem ]]; then
		echo '该项目'$projectItem'已存在，将跳过'
		echo $projectItem >> skipProjects
		return;
	fi

	tryTimes=3;

	sucCloneFlag=false

	while [[ $tryTimes > 0 ]] && [[ ! $sucCloneFlg ]]; do
		#statements
		git clone $url -b $branch

		if [[ $? -eq 0 ]]; then
			echo $projectItem >> sucProjects;
			sucCloneFlg=true
		else
			echo $projectItem >> failProjects;
			sucCloneFlg=false
			echo '克隆项目['$projectItem']失败，将重试，重试次数还有['$tryTimes']次'
			rm -rf $projectItem
		fi
		let tryTimes--

	done
}


PROJECT_URL[0]="http://git.oschina.net/trustno1/greenLight"
PROJECT_URL[1]="http://git.oschina.net/trustno1/MunichRe"
PROJECT_URL[2]="http://git.oschina.net/trustno1/demo2do-core"
PROJECT_URL[3]="http://git.oschina.net/trustno1/zufangbao-springboot-center" 
PROJECT_URL[4]="http://git.oschina.net/trustno1/gluon" 
PROJECT_URL[5]="http://git.oschina.net/trustno1/wellsfargo"
PROJECT_URL[6]="http://git.oschina.net/trustno1/canal-core"  
PROJECT_URL[7]="http://git.oschina.net/myounique/berkshire"
PROJECT_URL[8]="http://git.oschina.net/myounique/coffer"
PROJECT_URL[9]="http://git.oschina.net/myounique/bridgewater-deduct"
PROJECT_URL[10]="http://git.oschina.net//myounique/sun"
PROJECT_URL[11]="http://git.oschina.net/myounique/earth"
PROJECT_URL[12]="http://git.oschina.net/zufangbaowk/PriceWaterHouse"
PROJECT_URL[13]="http://git.oschina.net/zufangbaowk/Renaissance"
PROJECT_URL[14]="all"

TOOL_LIST=("git" "mvn" "java")

echo '检查工具是否安装：' 

for tool in ${TOOL_LIST[*]}; do
	checkToolExist $tool
done

echo '将克隆以下项目：'

echo ${!PROJECT_URL[0]}

for index in ${!PROJECT_URL[*]}; do

	url=${PROJECT_URL[$index]}

	project=${url##*\/}

	echo $index'.'${project}

done 

echo '请选择需要克隆项目的编号'
# 
read number

while [[ "$number" -lt 0 ]] || [[ "$number" -ge ${#PROJECT_URL[*]} ]]; do
	echo '选择项目编号有误，请重新选择'
	read number
done

echo '请输入需要克隆项目的分支名［如果不填，默认为yunxin_internal］'

read branch

if [[ -z $branch ]]; then
	branch="yunxin_internal"
fi

url=${PROJECT_URL[$number]}

project=${url##*\/}

current_path=$PWD

if [[ $current_path =~ zufangbao-springboot-center$ ]]; then

	cd ..
fi

# 克隆项目

echo '开始在['$PWD']目录下克隆git项目['${project}']'

if [[ $project == "all" ]] ; then

	for url in ${PROJECT_URL[*]} ; do

	{
		cloneProject $url $branch
	} &

	done 

else
	cloneProject $url $branch
fi

wait

printProjectStaticsInfoAndRemoveFile "克隆成功" "sucProjects"
printProjectStaticsInfoAndRemoveFile "克隆失败" "failProjects"
printProjectStaticsInfoAndRemoveFile "克隆跳过" "skipProjects"

echo '配置maven私库'
rm -f ~/.m2/settings.xml
wget -P ~/.m2/ http://zufangbao1.oss-cn-shanghai.aliyuncs.com/maven/settings.xml

if [[ $? -ne 0 ]]; then
	echo '配置maven私库失败，请查明原因重试！';
	exit
fi

echo '开始编译项目'

if [[ $project == "all" ]]; then
	cd zufangbao-springboot-center
else
	cd $project;
fi

mvn clean install

if [[ $? -ne 0 ]]; then
	echo '编译项目失败，请查明原因重试！';
	exit
else
	echo '编译项目成功，请导入到开发工具中'
fi




	       