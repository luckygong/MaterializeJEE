SET FOREIGN_KEY_CHECKS=0;

#公司信息
insert into `SYS_COMPANY` (`ID`, `COMPANY_NAME`, `COMPANY_TYPE`, `COMPANY_ADDRESS`, `COMPANY_TELPHONE`, `COMPANY_FAX`, `COMPANY_POST`, `ORG_CODE`, `BUSSINESS_LICENCE`, `COMPANY_LINKMAN`, `LINKMAN_TELPHONE`, `DESCRIPTION`, `ACTIVE_FLAG` ) values('1','青岛桥通软件科技有限公司','1','青岛市城阳区智力岛路1号创业大厦B座1802','0532-88919081',NULL,'266000',NULL,NULL,NULL,NULL,NULL,'1');

#机构信息
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('1','AD','行政部',NULL,NULL,'1',NULL,'1');
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('2','FD','财务部',NULL,NULL,'1',NULL,'1');
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('3','DD','研发部',NULL,NULL,'1',NULL,'1');
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('4','BD','董事会',NULL,NULL,'1',NULL,'1');
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('5','JG','JAVA组',NULL,'3','1',NULL,'1');
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('6','AG','安卓组',NULL,'3','1',NULL,'1');
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('7','HG','硬件组',NULL,'3','1',NULL,'1');
insert into `SYS_ORGANIZATION` (`ID`, `ORG_CODE`, `ORG_NAME`, `LEVEL`, `PARENT_ID`, `COMPANY_ID`, `DESCRIPTION`, `ACTIVE_FLAG`) values('8','TG','测试组',NULL,'3','1',NULL,'1');

#角色
insert into `SYS_ROLE` (`ID`, `ROLE_CODE`, `ROLE_NAME`, `DESCRIPTION`, `ACTIVE_FLAG`) values('1','ADMIN','管理员',NULL,'1');
insert into `SYS_ROLE` (`ID`, `ROLE_CODE`, `ROLE_NAME`, `DESCRIPTION`, `ACTIVE_FLAG`) values('2','DEVELOPER','研发人员',NULL,'1');
insert into `SYS_ROLE` (`ID`, `ROLE_CODE`, `ROLE_NAME`, `DESCRIPTION`, `ACTIVE_FLAG`) values('3','TESTER','测试人员',NULL,'1');

#菜单
insert  into `SYS_RESOURCE`(`ID`,`NAME`,`TYPE`,`IS_DIRECTORY`,`VALUE`,`ICON`,`PARENT_ID`,`ORDERS`,`LEVEL`,`DESCN`,`ACTIVE_FLAG`,`CREATE_ID`,`CREATE_DATE`,`UPDATE_ID`,`UPDATE_DATE`) values 
(1,'HADOOP',0,1,'#','mdi-action-invert-colors',NULL,1,1,NULL,1,NULL,NULL,NULL,NULL),
(2,'系统管理',0,1,'#','mdi-action-invert-colors',NULL,2,1,NULL,1,NULL,NULL,NULL,NULL),
(3,'任务设定',0,0,'#','mdi-action-invert-colors',1,1,2,NULL,1,NULL,NULL,NULL,NULL),
(4,'任务调度',0,0,'#','mdi-action-invert-colors',1,2,2,NULL,1,NULL,NULL,NULL,NULL),
(5,'任务查看',0,0,'#','mdi-action-invert-colors',1,3,2,NULL,1,NULL,NULL,NULL,NULL),
(6,'用户管理',0,0,'admin/user/userList','mdi-action-invert-colors',2,1,2,NULL,1,NULL,NULL,NULL,NULL),
(7,'机构管理',0,0,'#','mdi-action-invert-colors',2,2,2,NULL,1,NULL,NULL,NULL,NULL),
(8,'定时任务',0,0,'#','mdi-action-invert-colors',2,3,2,NULL,1,NULL,NULL,NULL,NULL),
(9,'资源管理',0,0,'admin/resource/resourceList','mdi-action-invert-colors',2,4,2,NULL,1,NULL,NULL,NULL,NULL),
(10,'角色管理',0,0,'admin/role/roleList','mdi-action-invert-colors',2,5,2,NULL,1,NULL,NULL,NULL,NULL);


#用户数据
insert into `SYS_USER` (`ID`, `USERNAME`, `NIKE_NAME`, `REAL_NAME`, `SEX`, `PASSWORD`, `AVATAR`, `TEL_PHONE`, `PHONE`, `EMAIL`, `USER_TYPE`, `IS_ENABLED`, `IS_LOCK`, `CREATE_ID`, `CREATE_TIME`) values('1','zhaosk','不动明王','shukun','1','d215288e9ea0159c5a246c0374649803','assets/images/avatar.jpg',NULL,'18663875410','893599733@qq.com','3','1','0',NULL,'2016-06-07 22:34:24');

#用户机构关系
insert into `SYS_USER_ORGANIZATION` (`ID`, `USER_ID`, `ORGANIZATION_ID`) values('1','1','5');

#用户角色关系
insert into `SYS_USER_ROLE` (`ID`, `USER_ID`, `ROLE_ID`) values('1','1','2');
insert into `SYS_USER_ROLE` (`ID`, `USER_ID`, `ROLE_ID`) values('2','1','1');

#角色菜单关系
INSERT INTO `sys_role_resource` VALUES (1, 1, 2);
INSERT INTO `sys_role_resource` VALUES (2, 1, 6);
INSERT INTO `sys_role_resource` VALUES (3, 1, 7);
INSERT INTO `sys_role_resource` VALUES (4, 1, 8);
INSERT INTO `sys_role_resource` VALUES (5, 1, 9);
INSERT INTO `sys_role_resource` VALUES (6, 1, 10);
INSERT INTO `sys_role_resource` VALUES (7, 2, 1);
INSERT INTO `sys_role_resource` VALUES (8, 2, 3);
INSERT INTO `sys_role_resource` VALUES (9, 2, 4);
INSERT INTO `sys_role_resource` VALUES (10, 2, 5);

#数据字典
insert  into `sys_data_dic`(`ID`,`CATEGORY`,`CODE`,`TRANSLATION`,`RANKING`,`ACTIVE_FLAG`) values 
(1,'SEX','1','男',1,1),
(2,'SEX','0','女',2,1),
(3,'SYS_USER_TYPE','1','注册用户',1,1),
(4,'SYS_USER_TYPE','2','正式会员',2,1),
(5,'SYS_USER_TYPE','3','内部员工',3,1),
(6,'RESOURCE_TYPE','0','URL',1,1),
(7,'RESOURCE_TYPE','1','METHOD',2,1),
(8,'RESOURCE_IS_DIR','0','页面',1,1),
(9,'RESOURCE_IS_DIR','1','目录',0,1);

COMMIT;
SET FOREIGN_KEY_CHECKS=1;