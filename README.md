## 项目介绍
> zufangbao-springboot-center提供所有maven模块项目的依赖统一管理

## 环境准备
以linux 64位系统为例，安装的软件都托管在内网的[Nas](http://192.168.0.184:5000/)上，账号密码wukai/123456
1. 需要安装[git](https://git-scm.com/downloads) (1.9)
2. 需要安装[jdk](http://192.168.0.184:5000/sharing/PGDXvIInF)(1.8)
3. 需要安装[ant](http://192.168.0.184:5000/sharing/MYunJ7YZg)(1.9.7)
4. 需要安装[mysql](http://192.168.0.184:5000/sharing/xLzsSlLgX)(5.7)
5. 需要安装[eclipse](http://192.168.0.184:5000/sharing/aU7S5qhCp)(neon)
6. 需要安装[maven](http://192.168.0.184:5000/sharing/CKwi8gIlL) (3.3.9),为了使用maven私服，拷贝[setting.xml](http://192.168.0.184:5000/sharing/y5HhlI0FW)到本地的电脑~/.m2/下


## 项目起步
#### 注册码云账号和申请加入项目
1.  在[码云](http://git.oschina.net/)上注册账号


2.  向代码管理员李博申请加入随地付项目组

#### 克隆代码
1. 加入项目组后，克隆代码到本地，以sun项目为例，使用
> git clone https://git.oschina.net/myounique/sun.git
2. 克隆完所有的项目后，进入到zufangbao-springboot-center中,执行命令
>  mvn   clean install -Dmaven.test.skip=true
3. 导入这些项目到eclipse中

#### 运行项目
1. 进入项目中，一般会以项目的名称作为启动入口，如berkshire项目，启动入口的java类为Berkshire.java
2. 点击该类，右键运行`run as java application`即可
