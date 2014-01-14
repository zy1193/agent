<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
	li.label {width:120px}
	li.sl {width:80%}
</style>
<title>代理商平台-账户余额</title>
</head>
<body>
<#include "/template/include/header.ftl">

<ul class="fix">
<li class="nl label">帐号</li>
<li class="sl">${acct.id}</li>
<li class="nl label">可用余额</li>
<li class="sl">${(acct.availableBalance / 1000)?string.currency }</li>
<li class="nl label">冻结余额</li>
<li class="sl">${(acct.frozenBalance / 1000)?string.currency}</li>
<li class="nl label">押金</li>
<li class="sl">${(acct.deposit / 1000)?string.currency}</li>
<li class="nl label">账户状态</li>
<li class="sl">${acct.statusDesc}</li>
</ul>
</div>
<#include "/template/include/footer.ftl">
</body>
</html>