<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台-代理商资料</title>
</head>
<body>
<#include "/template/include/header.ftl">
<div class="right_box">
<table width="100%" border="0" id="listTable" style="margin:20px 0 0 0px;text-align:center;">
<tr>
<th>登录名</th>
<th>真实名字</th>
<th>上级代理</th>
<th>级别</th>
<th>电话号码</th>
<th>手机号码</th>
</tr>
<#if subAgents??>
	<#list subAgents as agent>
	<tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'" >
	<td>${agent.id}</td>
	<td>${agent.name}</td>
	<td>${agent.superId}</td>
	<td>${agent.rankDesc}</td>
	<td>${agent.phoneNo!""}</td>
	<td>${agent.mobileNo!""}</td>
	</tr>
	</#list>
</#if>
</table>
</div>
<#include "/template/include/footer.ftl">
</body>
</html>