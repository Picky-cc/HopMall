#! /bin/bash

### 跑测试项目

echo '该脚本用来跑项目的Junit用例，支持的项目如下：'

project=(gluon sun wellsfargo earth  Renaissance berkshire PriceWaterHouse bridgewater-deduct  zufangbao-springboot-center MunichRe greenLight demo2do-core canal-core all)
declare -a project_test_map;

project_test_map["sun"]="AllTestsOfSunYunxinBranch"
project_test_map["wellsfargo"]="AllTestsOfWellsfargoYunxinBranch"
project_test_map["earth"]="AllTestsOfEarth"


i=0

for project_index in ${!project[@]}; do
    echo $i'.'${project[$project_index]}
    let i++
done

read -p '请选择项目编号:' num

if [[ "$num" -lt 0 ]] || [[ "$num" -ge ${#project[*]} ]]; then
  read -p '输入项目编号有误，请重新输入：' num
fi

cd ../

projectName=${project[$num]}

echo -e '\033[33m进入项目['$projectName']\033[0m'

cd $projectName

mvn clean test -DskipTests=false '"-Dtest=*'${project[$projectName]}'"' -DfailIfNoTests=false
