<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
td {text-align:left}
</style>
<title>代理商平台-用户管理</title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>

<script>
	var delete_url = "<@s.url value='/aop/deleteuser.action'/>";
	function deleteUser(id) {
		if (confirm("您确定要删除该用户吗？")) {
			$.get(
				delete_url,
				{"user.id":id},
				function(txt){
					if (txt) {
						alert("删除用户失败：\r\n" + txt);
					} else {
						alert("用户已删除");
						$("#tr_" + id).hide(200);
					}
				}
			);
		}
	}

	var resetpwd_url = "<@s.url value='/aop/resetpwd.action'/>";
	function resetPassword(id) {
		if (confirm("您确定要重置该用户的密码吗？")) {
			$.get(
				resetpwd_url,
				{"user.id":id},
				function(txt){
					alert("用户密码已重置，新密码是：" + txt);
				}
			);
		}
	}
</script>


</head>
<body>
<#include "/template/include/header.ftl">
<div class="right_box">

<form>
<table width="100%" border="0" id="listTable" style="margin:20px 0 0 0px;text-align:center;">
<tr>
	<th>ID</th>
	<td>
		<input type="text" class="ipb_m" name="user.id" value="${(user.id)!''}"/>
		<button type="submit" class="btn but_s">查询</button>
<tr>
</table>
</form>

<div style="margin:10px 0px -18px 0px;text-align:right;"><a href="<@s.url value='/aop/adduser.action'/>">添加新用户</a></div>
<table width="100%" border="0" id="listTable" style="margin:20px 0 0 0px;text-align:center;">
<tr>
<th>ID</th>
<th>名称</th>
<th>角色</th>
<th>操作</th>
</tr>
<#if userList??>
	<#list userList as user>
	<tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'" id="tr_${user.id}">
	<td>${user.id}</td>
	<td>${user.name}</td>
	<td>${user.roleName}</td>
	<td style="text-align:center">
	<#if user.id!=myid>
	<#if user.role!="3"><a href="#" onclick="deleteUser('${user.id}')">删除</a></#if>
	<a href="<@s.url value='/aop/modifyuserinput.action'/>?user.id=${user.id}">修改</a>
	<a href="#" onclick="resetPassword('${user.id}')">重置密码</a>
	</#if>
	</td>
	</tr>
	</#list>
</#if>
</table>
</div>
<#include "/template/include/footer.ftl">
</body>
</html>