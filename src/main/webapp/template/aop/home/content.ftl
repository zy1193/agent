<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台</title>
<script>
function go_notice() {
	var brand = document.getElementById("brandid").value;
	if (brand) {
		window.location.href='<@s.url value="/aop/content.action"/>?notice.brandid='+brand;
	}
}
</script>
</head>
<body style="background:#f0f5fd;">
<#include "/template/include/header.ftl">
<div style="margin:20px 0 0 20px;">
<form action="<@s.url value='/aop/save.action'/>" method="post">
<table border="0" cellpadding="10">
	<tr><td colspan="2" align="center"><h1 style="float:none;">公告</h1></td></tr>
	<tr>
	<td style="width:40px;">品牌</td>
	<td>
		<input type="text" style="width:100px;" class="ipb_l" id="brandid" name="notice.brandid" value="${(notice.brandid)!''}"/>
		<button class="but_l" type="button" onclick="javascript:go_notice();">查看</button>
	</td>
	</tr>
	<tr>
	<td>标题</td>
	<td><input type="text" class="ipb_l" style="width:300px;" name="notice.title" value="${(notice.title)!''}"/></td>
	</tr>
	<tr>
	<td>内容</td>
	<td><textarea style="padding:4px;" name="notice.content" cols="80" rows="20">${(notice.content)!''}</textarea></td>
	</tr>
	<tr><td colspan="2" align="center"><button type="submit" class="but_l" style="width:100px;">保存</button></td></tr>
</table>
</form>
<#if errmsg??><hr/>${errmsg}</#if>
<hr/>
说明<br/>
管理员可以为每个品牌发布一条公告<br/>
公告会显示在代理商系统的首页<br/>
输入品牌后，点击查看按钮将显示发布中的公告<br/>
点击保存按钮，如果内容为空，表示清除发布中的广告
</div>
<#include "/template/include/footer.ftl">
</body>
</html>