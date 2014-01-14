<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/tabs_frame.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台</title>
<script type="text/javascript" src="<@s.url value='/js/jquery.js'/>"></script>
<script type="text/javascript" src="<@s.url value='/js/tabs_frame.js'/>"></script>
</head>
<body>
<span id="loading">
<img src="<@s.url value='/images/loading.gif'/>" width="16" height="16" align="absmiddle" border="0" /> 请稍候，正在为您加载页面......
</span>
<#include "/template/include/header.ftl">
<div ><h1> 代理商平台 >> ${superMenu.name} >> ${menu.name}</h1></div>
<div id="tabnav">
	<ul>
	<#if menu.subMenus??>
	<#list menu.subMenus as m>
		<#if m.visible == "Y">
		<li><a href="${m.url}">${m.name}</a></li>
		</#if>
	</#list>
	</#if>
	</ul>
</div>
<div id="tabpane"><iframe width="100%" frameborder="0"></iframe></div>
<#include "/template/include/footer.ftl">
</body>
</html>