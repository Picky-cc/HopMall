## 项目介绍
> zufangbao-springboot-center提供所有maven模块项目的依赖统一管理

## 环境准备
以linux 64位系统为例，安装的软件都托管在内网的[Nas](http://192.168.0.184:5000/)上，账号密码wukai/123456
1. 需要安装[git](https://git-scm.com/downloads) (1.9)
2. 需要安装[jdk](http://192.168.0.184:5000/sharing/2FkR8Q7wr)(1.8)
4. 需要安装[mysql](http://192.168.0.184:5000/sharing/PRtYYf5Bf)(5.7)
5. 需要安装[eclipse](http://192.168.0.184:5000/sharing/aQjUsJtQ5)(neon)
6. 需要安装[maven](http://192.168.0.184:5000/sharing/IaHCQsQAm) (3.3.9),为了使用maven私服，拷贝[setting.xml](http://192.168.0.184:5000/sharing/CJYhv2IAc)到本地的电脑~/.m2/下

## 项目起步
#### 注册码云账号和申请加入项目
1.  在[码云](http://git.oschina.net/)上注册账号

2.  向代码管理员李博申请加入随地付项目组

#### 克隆代码
1. 加入项目组后，先克隆`zufangbao-springboot-center`代码到本地
```
git clone http://git.oschina.net/trustno1/zufangbao-springboot-center -b yunxin_internal
```

2.进入到`zufangbao-springboot-center`中，执行`sh init_project.sh`脚本，克隆并编译剩下项目。
3. 导入这些项目到eclipse中

#### 运行项目
1. 进入项目中，一般会以项目的名称作为启动入口，如berkshire项目，启动入口的java类为Berkshire.java
2. 点击该类，右键运行`run as java application`即可


## zufangbao-springboot-center脚本介绍

1、git.sh[批量操作git的一些命令]<br>
2、package.sh[运维打包部署命令]<br>
3、init_project.sh[初始化项目的脚本]
4、addMember.js[用来添加gitoschina.net账号]
