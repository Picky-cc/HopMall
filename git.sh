#! /bin/bash

###
###	git的相关操作
###

function readArgsFromConsole(){

	num=$1

	command_list_len=$2

	errMsg=$3

	while [[ "$num" -lt 0 ]] || [[ "$num" -ge $command_list_len ]]; do

		echo -e '\033[31m'$errMsg': \033[0m'

		read num

	done

}

function executeCommand(){

	command_name=$1

	projectName=$2

	branchName=$3

	message=$4

	if [[ $projectName == "all" ]];then

		for projectItem in ${project[@]:0:$((${#project[*]}-1))}; do

			# echo 'projectItem:'$projectItem

			executeCommand $command_name $projectItem $branchName $message
		done

	else

		if [[ ! -d $projectName ]]; then

			echo -e '\033[31m 该项目['$projectName']不存在,请检查！\033[0m'

			exit
		fi

		echo -e '\033[33m进入项目['$projectName']:\033[0m'

		cd $projectName

		if [[ $command_name == "pull" ]]; then

			git pull origin $branchName

		elif [[ $command_name == "push" ]]; then
			git add . && git commit -m $message
			git push origin $branchName:$branchName
		elif [[ $command_name == "checkout" ]]; then
			git checkout $branchName
		else
			git tag -a 'v_'$branchName -m $message
		fi

		cd ..
	fi


}

project=(gluon sun wellsfargo earth  Renaissance berkshire PriceWaterHouse bridgewater-deduct  zufangbao-springboot-center MunichRe greenLight demo2do-core canal-core all)

command_list=(pull checkout push tag)

# command_list_alias[pull]='批量项目'
# command_list_alias[push]='提交批量项目代码'
# command_list_alias[checkout]='切换批量项目分支'
# command_list_alias[merge]='合并批量项目代码'
# command_list_alias[tag]='tag标记批量项目'

echo '该脚本提供了一些常用的批量操作工具，比如：批量提交、批量更新、批量切换分支、批量打项目tag工具'

echo '当前脚本路径是'$PWD

echo '该脚本提供git的一些工具：'

for(( index=0;index<${#command_list[*]};index++ )); do

	command_name=${command_list[$index]}

	echo $index'.'$command_name'项目'
done

read -p  '请选择工具编号:' command_index


readArgsFromConsole $command_index ${#command_list[*]} '请重新选择工具编号'

# echo 'command_index1['$command_index']'

echo '将该命令作用于以下的项目：'

i=0
for((;i<${#project[*]};i++)); do
	echo $i'.'${project[$i]}
done;

read -p '请输入项目的编号[支持多项目，多项目数字以空格分割]:' project_indexs

for project_index in ${project_indexs[*]}; do

	 if [[ "$project_index" -lt 0 ]] || [[ "$project_index" -ge ${#project[*]} ]]; then
	 	read  -p '您输入的数字有误，请重新输入:' project_indexs
	 fi
done

project_len=$(expr ${#project[*]})

# readArgsFromConsole $project_index $project_len '请重新输入项目编号'

echo '请输入分支名：'

read branch_name

if [[ -z $branch_name ]];then
	echo '没有输入特定的分支名，将使用默认的分支名[yunxin_internal]'
	branch_name='yunxin_internal'
fi

if [[ $PWD =~ zufangbao-springboot-center$ ]]; then
	cd ..
fi

# echo 'command_index2['$command_index']'

case $command_index in
	0|1 )
		message=''
		;;
	2|3 )
		echo '请输入备注:'

		read message

		if [[ -z $message ]]; then
			message='update'
		fi


		;;
esac

for project_index in ${project_indexs[*]}; do

	projectName=${project[$project_index]}

	command_name=${command_list[$command_index]}

	echo -e '\033[33m将该命令['$command_name']作用于以下的项目:'

	if [[ $projectName == "all" ]]; then
		for projectItem in ${project[@]:0:$((${#project[*]}-1))};do
			echo $projectItem
		done
	else
		echo $projectName
	fi

	echo -e "\033[0m"

	executeCommand $command_name $projectName $branch_name $message

done
