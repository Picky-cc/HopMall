#! /bin/bash

### 跑测试项目


echo '该脚本用来跑项目的Junit用例，支持的项目如下：'

declare -A regex_test_map

regex_test_map["sun"]="AllTestsOfSunYunxinBranch"
regex_test_map["wellsfargo"]="AllTestsOfWellsfargoYunxinBranch"
regex_test_map["earth"]="AllTestsOfEarth"


function runTest4Project(){

	local project_test_array=($1)

	for projectName in ${project_test_array[*]}; do

		echo -e '\033[33m进入项目['$projectName']\033[0m'

		cd $projectName

		_command='mvn clean test -DskipTests=false -Dtest=*'${regex_test_map[$projectName]}

		echo '将执行下面的命令：'$_command

		$_command

	done
}

project_array=()

index=0

for project in ${!regex_test_map[@]}; do
    echo $index'.'${project}
    project_array[$index]=${project}
    let index++
done
echo ${index}'.所有测试'

# echo "project_array."${project_array[*]}

read -p '请选择项目编号:' num

if [[ "$num" -lt 0 ]] || [[ "$num" -gt ${#project_array[*]} ]]; then
  read -p '输入项目编号有误，请重新输入：' num
fi

BASE_PATH=$(cd `dirname $0`;cd ..;pwd)

cd $BASE_PATH

if [[ $num -eq ${#project_array[*]} ]]; then

	index=0

	for project in ${project_array[*]}; do
		project_test_array[$index]=${project}
		let index++
	done

else
	project_test_array[0]=${project_array[$num]}
fi

runTest4Project $project_test_array
