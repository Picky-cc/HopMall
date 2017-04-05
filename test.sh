#! /bin/bash

### 跑测试项目

echo '该脚本用来跑项目的Junit用例，支持的项目如下：'

declare -A project_test_map

project_test_map["sun"]="AllTestsOfSunYunxinBranch"
project_test_map["wellsfargo"]="AllTestsOfWellsfargoYunxinBranch"
project_test_map["earth"]="AllTestsOfEarth"

project=(sun wellsfargo earth)

for project_index in ${!project[@]}; do
    echo $project_index'.'${project[$project_index]}
done

read -p '请选择项目编号:' num

if [[ "$num" -lt 0 ]] || [[ "$num" -ge ${#project[*]} ]]; then
  read -p '输入项目编号有误，请重新输入：' num
fi

cd ../

projectName=${project[$num]}

echo -e '\033[33m进入项目['$projectName']\033[0m'

cd $projectName

echo 'mvn clean test -DskipTests=false -Dtest=*'${project_test_map[$projectName]}

mvn clean test -DskipTests=false "-Dtest=*"${project_test_map[$projectName]}
