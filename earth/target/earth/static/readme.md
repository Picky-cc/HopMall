## 主要目录
```
dist: 上线时引用的静态文件目录
css: 样式文件
  |-- src: sass原文件
  |-- build: 编译后的文件
  |-- plugins: 主要为第三方插件的样式文件
  |-- 其他：旧版使用
test: 前端的单元测试文件，主要测试js/mods目录的非UI部分代码
node_modules: 第三方依赖。主要是打包编译需要用到
js: 脚本文件
  |-- mods: 模块化文件
    |-- component: 插件
    |-- entity: 模型
    |-- scaffold: 可单元测试的网站脚手架文件
    |-- initialize: 初始化文件，一开始全部都会加载
    |-- view: 视图，根据后台对一级和二级菜单进行规划
    |-- init.js: 启动文件
  |-- plugins: 主要为第三方插件的样式文件
  |-- utils/vendor: 第三方库
  |-- 其他：旧版使用
```


## 代码规范
使用[eslint](http://eslint.org/)对js代码进行审查。使用eslint的方式有两种：

1. gulp lint （已安装依赖包。该命令只审查'./js/plugins/common-content.js', './js/mods/**/*.js'中的文件）
2. 集成到编辑器中
    - [Sublime Text 3](https://github.com/roadhump/SublimeLinter-eslint)

以前的代码文件可以暂时先不改动，但现在编写的代码请一定**遵照规范**，linter检查出的错误及时修正；


## 编译部署
### 环境
1. 安装[node.js](https://nodejs.org/en/download/)，第一次安装完成就行
2. 进入static目录下，运行```npm install```。执行命令时如果出现 缺少依赖 的错误时需要再次运行此命令

### 执行，所有命令在static目录下执行
本地开发：gulp [develop]
上线前部署：gulp deploy
代码规范检查：gulp lint
单元测试：gulp unittest


## FAQ
1.  q: 对于需要执行编译部署命令的同学，在执行命令时可能会遇到执行错误的提示
    a: 往往是因为添加了新的依赖，但本地还没有下载该依赖包。重新执行 npm install 命令即可
2.  q: 遇到node-sass安装不上时
    a: 先执行npm rebuild node-sass命令，之后重新执行npm install gulp-sass命令
3.  q: npm2升迁到npm3时，包的管理方式发生改变
    a: 使用npm dedupe重新计算依赖关系


