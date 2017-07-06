## 项目介绍
> zufangbao-springboot-center提供所有maven模块项目的依赖统一管理

## 环境准备
以linux 64位系统为例，安装的软件都托管在内网的[Nas](http://192.168.0.184:5000/)上，账号密码wukai/123456
1. 需要安装[git] (1.9)
2. 需要安装[jdk](1.8)
3. 需要安装[mysql](5.7)
4. 需要安装[eclipse](neon)
5. 需要安装[maven],为了使用maven私服，拷贝[setting.xml](http://192.168.0.184:5000/sharing/y5HhlI0FW)到本地的电脑~/.m2/下


## 项目起步
#### 注册码云账号和申请加入项目
1.  在[gitlab](http://gitlab.5veda.net/users/sign_in)上注册账号


2.  向代码管理员李博申请加入随地付项目组

#### 克隆代码
1. 加入项目组后，克隆代码到本地，先克隆zufangbao-springboot-center项目,克隆的时候在本地仓库新建一个分支来追踪远程代码库中的yunxin_internal分支
2. 克隆完成后,进入zufangbao-springboot-center,执行init_project.sh*脚本,选择20,按回车键
3. 导入这些项目到eclipse中

#### 运行项目
1. 进入项目中，一般会以项目的名称作为启动入口，如berkshire项目，启动入口的java类为Berkshire.java
2. 点击该类，右键运行`run as java application`即可

```bash
mvn clean package -pl ../greenLight -amd
mvn clean test -pl ../greenLight -DskipTests=false -Dtest=*
```
