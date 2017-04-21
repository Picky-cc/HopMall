#! /bin/bash
###
###	重新设置git repository
### 


function resetting_git_repository(){

	cd $BASE_PATH

	echo '当前目录是:'${BASE_PATH}

	url=$1;

	projectItem=${url##*\/}

	if [[ ! -d $projectItem ]]; then
		echo '该项目'$projectItem'不存在，将跳过'
		return;
	fi
	
	cd $projectItem;

	echo `git remote -v` | grep -q "origin https://git.oschina.net"

	if [[ $? -eq 0 ]]; then

		return;
	fi

	git remote rename origin gitlab &&  git remote rename upstream origin

	# && git remote add origin 'http://gitlab.5veda.net/zjgsuwk/'$projectItem'.git'

	if [[ $? -eq 0 ]]; then

		echo '重置项目'$projectItem'的git仓库地址成功！'
		# git remote -v 
	else
		echo '重置项目'$projectItem'的git仓库地址失败！'
	fi

	cd $BASE_PATH

}

function checkInputValid(){

	local inputArray=($1)

	len=$2

	echo 'inputArray:'${inputArray[@]}

	for input in ${inputArray[*]}; do
		
		if [[ $input -lt 0 ]] || [[ $input -ge $len ]]; then

			echo false;
		fi
	done

	echo true;
}

PROJECT_URL[0]="https://git.oschina.net/trustno1/greenLight"
PROJECT_URL[1]="https://git.oschina.net/trustno1/MunichRe"
PROJECT_URL[2]="https://git.oschina.net/trustno1/demo2do-core"
PROJECT_URL[3]="https://git.oschina.net/trustno1/zufangbao-springboot-center"
PROJECT_URL[4]="https://git.oschina.net/trustno1/gluon"
PROJECT_URL[5]="https://git.oschina.net/trustno1/wellsfargo"
PROJECT_URL[6]="https://git.oschina.net/trustno1/canal-core"
PROJECT_URL[7]="https://git.oschina.net/myounique/berkshire"
PROJECT_URL[8]="https://git.oschina.net/myounique/coffer"
PROJECT_URL[9]="https://git.oschina.net/myounique/bridgewater-deduct"
PROJECT_URL[10]="https://git.oschina.net//myounique/sun"
PROJECT_URL[11]="https://git.oschina.net/myounique/earth"
PROJECT_URL[12]="https://git.oschina.net/zufangbaowk/PriceWaterHouse"
PROJECT_URL[13]="https://git.oschina.net/zufangbaowk/Renaissance"
PROJECT_URL[14]="https://git.oschina.net/trustno1/SwissRe"
PROJECT_URL[15]="https://git.oschina.net/trustno1/jpmorgan"
PROJECT_URL[16]="https://git.oschina.net/trustno1/swift"
PROJECT_URL[17]="https://git.oschina.net/myounique/bridgewater-remittance"
PROJECT_URL[18]="https://git.oschina.net/trustno1/Deloitte"
PROJECT_URL[19]="https://git.oschina.net//myounique/Barclays"
PROJECT_URL[20]="all"

BASE_PATH=$(cd `dirname $0`;cd ..;pwd)

echo '将重置以下项目git repository：'

for index in ${!PROJECT_URL[*]}; do

	url=${PROJECT_URL[$index]}

	project=${url##*\/}

	echo $index'.'${project}

done

read -p '请选择需要重置项目的编号,支持多个项目编号，以空格隔开:' indexes

len=${#PROJECT_URL[*]}

while [[ ! $(checkInputValid ${indexes} $len)  ]]; do
	read -p '选择项目编号有误，请重新填入：' indexes
done

urls=()

allIndex=$[ len - 1 ]

for index in ${indexes[*]}; do
	#statements
	if [[ $index -eq $allIndex ]]; then
		urls=()
		urls=(`echo ${PROJECT_URL[*]:0:${allIndex}}`)
		break;
	else
		urls+=${PROJECT_URL[$index]}
		urls+="  "
	fi
done

for url in ${urls[*]}; do
	#statements
	project=${url##*\/}
	echo '开始在['$PWD']目录下重置git项目['${project}']的git repository'
	resetting_git_repository $url
done



