/** 初始化脚本 **/

truncate table data_dict;
/** 初始化数据字典 **/
insert into data_dict (category,property,value) values ('TRANS','01','充值');
insert into data_dict (category,property,value) values ('TRANS','02','提卡');
insert into data_dict (category,property,value) values ('TRANS','03','下级充值佣金');

/** 初始化代理商等级信息 **/

/** 初始化押金金额 **/

truncate table menu;

/** 根菜单 **/
insert into menu (id,name,url,category,rank,super_id,visible)
	values (0,'代理商平台',null,'0',0,-1,'N');

/** 初始化菜单 *** 个人资料 **/
insert into menu (id,name,url,category,rank,super_id,visible)
	values (1,'个人资料',null,'0',1,0,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100,'查询','/agent/amp/tabsFrame.action?id=100','0',2,1,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000,'我的资料','/agent/amp/myprofile.action','0',2,100,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10001,'下级资料','/agent/amp/subprofile.action','0',2,100,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (101,'修改密码','/agent/amp/tabsFrame.action?id=101','0',2,1,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10100,'修改密码','/agent/amp/password.action','0',3,101,'Y');

/** 初始化菜单 *** 账户管理 **/	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (2,'账户管理',null,'0',1,0,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (200,'余额查询','/agent/amp/tabsFrame.action?id=200','0',2,2,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (20000,'余额查询','/agent/amp/mybal.action','0',3,200,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (201,'在线充值','/agent/amp/tabsFrame.action?id=201','0',2,2,'N');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (20100,'在线充值','/agent/amp/recharge.action','0',3,201,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (202,'授信','/agent/amp/tabsFrame.action?id=202','0',2,2,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (20200,'授信','/agent/amp/provide.action','0',3,202,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (203,'代充','/agent/amp/tabsFrame.action?id=203','0',2,2,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (20300,'代充','/agent/amp/userrecharge.action','0',3,203,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (20301,'代充查询','/agent/amp/userrechargelist.action','0',3,203,'Y');	
	
	
/** 初始化菜单 *** 充值卡管理 **/	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (3,'充值卡管理',null,'0',1,0,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (300,'购卡','/agent/amp/tabsFrame.action?id=300','0',2,3,'Y');	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (30000,'购卡','/agent/amp/buycard.action','0',3,300,'Y');	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (301,'修改卡状态','/agent/amp/tabsFrame.action?id=301','0',2,3,'Y');	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (30100,'修改卡状态','/agent/amp/cardstatus.action','0',3,301,'Y');	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (302,'查询','/agent/amp/tabsFrame.action?id=302','0',2,3,'Y');	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (30200,'卡列表','/agent/amp/cardlist.action','0',3,302,'Y');	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (30201,'卡详情','/agent/amp/cardinfo.action','0',3,302,'Y');
	
	
/** 初始化菜单 *** 查询统计 **/	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (4,'查询统计',null,'0',1,0,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (400,'订单查询','/agent/amp/tabsFrame.action?id=400','0',2,4,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (40001,'充值订单','/agent/amp/rechargeorder.action','0',3,400,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (40002,'购卡订单','/agent/amp/buycardorder.action','0',3,400,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (401,'账户流水查询','/agent/amp/tabsFrame.action?id=401','0',2,4,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (40101,'我的流水','/agent/amp/myacct.action','0',3,401,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (40102,'下级流水','/agent/amp/subacct.action','0',3,401,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (402,'账户日报','/agent/amp/tabsFrame.action?id=402','0',2,4,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (40201,'我的日报','/agent/amp/mydayrpt.action','0',3,402,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (40202,'下级日报','/agent/amp/subdayrpt.action','0',3,402,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (403,'库存统计','/agent/amp/tabsFrame.action?id=403','0',2,4,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (40300,'库存统计','/agent/amp/stockstat.action','0',3,403,'Y');
	
	
/** 后台管理系统菜单 **/
	
/** 根菜单 **/
insert into menu (id,name,url,category,rank,super_id,visible)
	values (-1000,'代理商平台',null,'1',0,-1,'N');
	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (1000,'代理商管理',null,'1',1,-1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100000,'开户审核','/agent/aop/tabsFrame.action?id=100000','1',2,1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000000,'开户审核','/agent/aop/audit.action','1',3,100000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100001,'帐号管理','/agent/aop/tabsFrame.action?id=100001','1',2,1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000100,'管理登录帐号','/agent/aop/frozenlist.action','1',3,100001,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000101,'管理钱包帐号','/agent/aop/frozenacctlist.action','1',3,100001,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000102,'操作登录帐号','/agent/aop/frozen.action','1',3,100001,'N');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000103,'操作钱包帐号','/agent/aop/frozenacct.action','1',3,100001,'N');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100002,'资料修改','/agent/aop/tabsFrame.action?id=100002','1',2,1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000200,'资料修改','/agent/aop/profile.action','1',3,100002,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100003,'授信','/agent/aop/tabsFrame.action?id=100003','1',2,1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000300,'授信','/agent/aop/provide.action','1',3,100003,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000301,'授信-代理信息','/agent/aop/agentinfo.action','1',3,100003,'N');
	insert into menu (id,name,url,category,rank,super_id,visible)
	values (100003,'授信','/agent/aop/tabsFrame.action?id=100003','1',2,1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000300,'授信','/agent/aop/provide.action','1',3,100003,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (1001,'充值卡管理',null,'1',1,-1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100100,'卡信息','/agent/aop/tabsFrame.action?id=100100','1',2,1001,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10010000,'卡信息','/agent/aop/cardinfo.action','1',3,100100,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100101,'卡追踪','/agent/aop/tabsFrame.action?id=100101','1',2,1001,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10010100,'卡追踪','/agent/aop/cardtrace.action','1',3,100101,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100102,'卡冻结','/agent/aop/tabsFrame.action?id=100102','1',2,1001,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10010200,'卡冻结','/agent/aop/cardfrozen.action','1',3,100102,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10010201,'卡连号冻结','/agent/aop/serialcardfrozen.action','1',3,100102,'Y');	
	
	
	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (1002,'查询统计',null,'1',1,-1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100200,'日统计','/agent/aop/tabsFrame.action?id=100200','1',2,1002,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10020000,'提卡统计','/agent/aop/buycardstat.action','1',3,100200,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10020001,'销售统计','/agent/aop/salestat.action','1',3,100200,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100201,'钱包统计','/agent/aop/tabsFrame.action?id=100201','1',2,1002,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10020100,'钱包统计','/agent/aop/walletstat.action','1',3,100201,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100202,'代理商统计','/agent/aop/tabsFrame.action?id=100202','1',2,1002,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10020200,'充值统计','/agent/aop/rechargestat.action','1',3,100202,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10020201,'余额统计','/agent/aop/balstat.action','1',3,100202,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100203,'流水查询','/agent/aop/tabsFrame.action?id=100203','1',2,1002,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10020301,'账户流水','/agent/aop/acctloglist.action','1',3,100203,'Y');
	
insert into menu (id,name,url,category,rank,super_id,visible)
	values (1003,'其他功能',null,'1',1,-1000,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100300,'权限管理','/agent/aop/tabsFrame.action?id=100300','1',2,1003,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10030000,'权限管理','/agent/aop/permission.action','1',3,100300,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (100301,'修改密码','/agent/aop/tabsFrame.action?id=100301','1',2,1003,'Y');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10030100,'修改密码','/agent/aop/changepassword.action','1',3,100301,'Y');

/** 初始化超级管理员 密码 168168 **/	
insert into user (id,name,password,role) values ('admin','超级管理员','aa97eba124ab0c029fb7d5c37a6141b0','3');	
	