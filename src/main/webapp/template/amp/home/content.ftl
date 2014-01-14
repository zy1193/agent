<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台</title>
<style>
pre{ 
white-space:pre-wrap;/*css-3*/ 
white-space:-moz-pre-wrap;/*Mozilla,since1999*/ 
white-space:-pre-wrap;/*Opera4-6*/ 
white-space:-o-pre-wrap;/*Opera7*/ 
word-wrap:break-word;/*InternetExplorer5.5+*/}
</style>
</head>
<body style="background:#f0f5fd;">
<#include "/template/include/header.ftl">
<#---div id="welcome"><!--<h1>欢迎登入代理商平台<h1></div>-->

<#if notice ??>
<div style="postion:relative;margin-left:0px;;margin-right:auto;">
	<ul style="float:left;"><li style="margin-top:50px;line-height:20px;font-weight:bold;margin-left:250px;text-align:center;font-size:18px;font-family:宋体;line-height:inherit;color:red;">最新公告:${notice.title!''}</li>
	<li style="float:center;margin-top:25px;font-size:18px;margin-left:250px;letter-spacing:2px;color:red;"><pre>${notice.content!''}</pre></li></ul>
	<#else>
	没有公告
	</#if>
	<div>
<#include "/template/include/footer.ftl">
</body>
</html>