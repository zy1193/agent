SELECT * FROM menu WHERE category='1' AND super_id=100300


INSERT INTO menu (id,NAME,url,category,rank,super_id,visible) 
VALUES (10030001,'用户管理','/agent/aop/user.action','1','3',100300,'Y');
INSERT INTO menu (id,NAME,url,category,rank,super_id,visible) 
VALUES (10030002,'添加用户','/agent/aop/adduser.action','1','3',100300,'N');
INSERT INTO menu (id,NAME,url,category,rank,super_id,visible) 
VALUES (10030003,'删除用户','/agent/aop/deleteuser.action','1','3',100300,'N');
INSERT INTO menu (id,NAME,url,category,rank,super_id,visible) 
VALUES (10030004,'修改用户','/agent/aop/modifyuser.action','1','3',100300,'N');
INSERT INTO menu (id,NAME,url,category,rank,super_id,visible) 
VALUES (10030005,'重置密码','/agent/aop/resetpwd.action','1','3',100300,'N');
INSERT INTO menu (id,NAME,url,category,rank,super_id,visible) 
VALUES (10030006,'修改用户','/agent/aop/modifyuserinput.action','1','3',100300,'N');

/*
1、高金雅有最高权限，何仕杰跟吕林权限一样，有审核的功能。
2、有重置代理商密码的功能
3、输入卡密码可以查询是哪个代理商提的卡密码
那个因为现在你那边好些功能没做好，只有等做出来的时候才能看出来，到时候在弄吧，
*/


insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000104,'重置代理商密码','/agent/aop/resetagentpwd.action','1',3,100001,'N');
insert into menu (id,name,url,category,rank,super_id,visible)
	values (10000105,'删除代理商资料','/agent/aop/deleteagent.action','1',3,100001,'N');

	