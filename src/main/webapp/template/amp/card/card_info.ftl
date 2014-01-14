<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
	ul {margin:0px;padding:0xp;width:510px;}
	li.nl {font-size:11px;line-height:180%;width:100px}
	li.sl {font-size:11px;line-height:180%;width:400px}
</style>
<title>代理商平台-购卡</title>
</head>
<body>
<#if card??>
<ul class="fix">
<li class="nl">购卡订单号</li><li class="sl">${card.orderSn}</li>
<li class="nl">购卡时间</li><li class="sl">${card.buyTime?string("yyyy-MM-dd HH:mm")}</li>
<li class="nl">卡号</li><li class="sl">***</li>
<li class="nl">密码</li><li class="sl">***</li>
<li class="nl">价格</li><li class="sl">${(card.price / 1000)?string.currency}</li>
<li class="nl">过期日期</li><li class="sl">${card.expireDate?string("yyyy-MM-dd")}</li>
<li class="nl">代理商ID</li><li class="sl">${card.agentId}</li>
<#if agent??>
<li class="nl">代理商名称</li><li class="sl">${agent.name}</li>
<li class="nl">代理商级别</li><li class="sl">${agent.rankDesc}</li>
</#if>
<li class="nl">销售状态</li><li class="sl">${card.saleStatusDesc}</li>
<li class="nl">销售时间</li><li class="sl">${(card.saleTime?string("yyyy-MM-dd HH:mm"))!''}</li>
<li class="nl">充值状态</li><li class="sl">${card.rechargeStatusDesc}</li>
<li class="nl">充值订单号</li><li class="sl">${(card.payOrderno)!''}</li>
<li class="nl">充值帐号</li><li class="sl">${(card.uid)!''}</li>
<li class="nl">充值电话号码</li><li class="sl">${(card.phone)!''}</li>
<li class="nl">充值时间</li><li class="sl">${(card.rechargeTime?string("yyyy-MM-dd HH:mm"))!''}</li>
</ul>
</#if>
</body>
</html>
