<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
	li.label {width:120px}
	li.sl {width:80%}
</style>
<title>代理商平台-代理商资料</title>
</head>
<body>
<#include "/template/include/header.ftl">
<div class="right_box">
<ul class="fix">
<li class="nl label">登录名</li>
<li class="sl">${agent.id}</li>
<li class="nl label">真实名字</li>
<li class="sl">${agent.name}</li>
<li class="nl label">上级代理商</li>
<li class="sl">${agent.superId}</li>
<li class="nl label">代理商级别</li>
<li class="sl">${agent.rankDesc}</li>
<li class="nl label">电话号码</li>
<li class="sl">${agent.phoneNo!""}</li>
<li class="nl label">手机号码</li>
<li class="sl">${agent.mobileNo!""}</li>
<li class="nl label">身份证号</li>
<li class="sl">${agent.idCardNo}</li>
<li class="nl label">QQ号码</li>
<li class="sl">${agent.qq!""}</li>
<li class="nl label">拍拍店地址</li>
<li class="sl">${agent.paipaiShopUrl!""}</li>
<li class="nl label">旺旺号码</li>
<li class="sl">${agent.wangwang!""}</li>
<li class="nl label">淘宝店地址</li>
<li class="sl fix">${agent.taobaoShopUrl!""}</li>
</ul>
</div>
<#include "/template/include/footer.ftl">
</body>
</html>