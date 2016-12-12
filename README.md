# MaterializeJEE

###工作和学习过程中的产物，可能比较臃肿，可酌情自行增删功能。
###反馈和探讨联系：zhao.sk@qq.com

###后台

1. 数据库脚本使用flyway插件进行管理.      
>sql文件路径：resources/db/migration  
>常用maven命令：flyway:clean ,flyway:migrate  

2. ORM为mybatis，并配置了mysql和Phoenix双数据源，phoenix可用于操作hbase数据库    
>需要启用Phoenix数据源时，将spring-context-datasourse.xml中phoenix配置注释取消    
>同时，jdbc.properties文件中dbc.phoenix.url配置hbase集群zookeeper地址    
>mysql的mapper文件路径：resources/mybatis/mappers/  
>phoenix的mapper文件路径：resources/mybatis/phoenix/  

3. 实现提交本地mapreduce jar到远程hadoop集群（未提供页面管理功能）    
>hadoop仅支持2.x系列，2.x版本相较于1.x版本配置文件路径和内容有改动    
>web服务未运行在hadoop集群的节点中，则需复制hadoop配置文件到resources/cluster/conf/hadoop目录下，并在host文件中集群节点地址    

4. 实现根据配置通过代码动态生成和运行spring batch批量任务（未提供页面管理功能）          
>batch任务支持simple和flow两种模式，simple没有分支，flow可以有分支       

5. 通过代码实现动态定时任务，动态添加、修改、删除定时任务（未提供页面管理功能）         
>实现动态定时任务和spring batch任务，主要为了后期能与hadoop结合，动态远程提交mapreduce任务或任务流    
>若对定时任务需求不强，可使用基于spring配置文件的quartz定时任务    
  
6. 自己编写的基于freemarker模板的代码自动生成工具。（待完善）  
>暂不支持多表关联自动生成，只支持单表模式；只支持有主键的表。  
>生成dao、model、mapper、service文件。  
>使用方法：配置genconfig.properties，运行GenRunner.java  

###前端

1. jquery + html 架构 ，前后端数据交互采用json格式 
>理论上可以使用apache或nginx独立部署，但还没实验过，不知是否会出现跨域问题。  

2. 使用materialize template的html模板
>materialize是基于谷歌Material Design Language(MDL)设计语言所构建的一套CSS/HTML库 
>能适应手机、平板等小屏设备 

3. 对前端常用功能进行了封装。脚本位于webapp/assets/scripts
>bind-data：根据json数据，自动回填数据到表单，适合修改功能页面数据的初始化
>block-ui：遮罩层
>page-plugin：分页查询组件
>prompt-ui：materialize风格消息提示的封装，相当于alert、confirm
>select-dropdown：自动从后台数据字典表中加载下拉框选项；或加载数据字典到map中
>header：加载页面顶部导航条
>left-sidebar：加载页面左侧菜单栏

4. 用户上传的文件设计为保存在ftp/sftp工作目录下
>将文件保存在项目目录外部，再配置apache或nginx进行访问
>前端中有3个路径，配置在path.js中，分别为：
>>RootPath：前端访问路径，若未使用apache或nginx单独部署，则与ProjectPath相同
>>ProjectPath：前端ajax请求后台数据路径
>>FtpWebPath：访问用户上传资源路径

###前端效果展示

1. 电脑浏览    
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/web/web登录.png "登录页面")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/web/web首页.png "登录成功首页")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/web/web列表.png "列表查询")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/web/web卡片列表.png "卡片式列表查询")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/web/web新增.png "新增页面")  

2. 电脑浏览    
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/phone/phone登录.PNG "登录页面")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/phone/phone首页.PNG "登录成功首页")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/phone/phone菜单.PNG "菜单样式")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/phone/phone列表.PNG "列表查询")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/phone/phone卡片.PNG "卡片式列表查询")  
![](https://github.com/shukun-zhao/MaterializeJEE/raw/master/product_rendering/phone/phone新增.PNG "新增页面")  

  
##to be continued...