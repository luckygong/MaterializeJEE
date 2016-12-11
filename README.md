# MaterializeJEE
1.数据库脚本使用flyway插件进行管理<br/>
  sql文件路径：resources/db/migration<br/>
  常用maven命令：flyway:clean ,flyway:migrate<br/>
  
2.自己编写的基于freemarker模板的代码自动生成工具。（待完善）<br/>
  暂不支持多表关联自动生成，只支持单表模式；只支持有主键的表。<br/>
  生成dao、model、mapper、service文件。<br/>
  使用方法：配置genconfig.properties，运行GenRunner.java<br/>
  
to be continued...