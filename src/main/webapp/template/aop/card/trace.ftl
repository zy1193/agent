<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
	#q > li {padding-left:8px;}
	.line {width:100%;border-bottom:1px dotted DarkGray}
	.label {width:100px}
	li.sl {width:80%}
</style>
<title>代理商平台-卡追踪</title>
</head>
<body>
<#include "/template/include/header.ftl">
<form action="<@s.url value='/aop/cardtrace.action'/>"
<div><ul id="q" class="fix" style="list-style-type:none">
<li class="nl">
<input type="radio" id="r1" name="keytype" value="0" <#if keytype=="0">checked="checked"</#if>><label for="r1">卡号</label> 
<input type="radio" id="r2" name="keytype" value="1" <#if keytype=="1">checked="checked"</#if>><label for="r2">密码</label>
<!--</li>
<li class="sl">--><input class="ipb_m" style="width:280px;margin-left:10px;" type="text" name="key" value='${key!''}'/> <button type="submit" class="btn but_s" style="margin-left:10px;">搜索</button></li>
</ul></div>

<#if card??>

<ul class="fix" style="border:1px solid darkgray;margin-bottom:15px;">
<li class="nl line" style="text-align:center;font-weight:bold">卡信息</li>
<li class="nl label">购卡订单号</li><li class="sl">${card.orderSn}</li>
<li class="nl label">购卡时间</li><li class="sl">${card.buyTime?string("yyyy-MM-dd HH:mm")}</li>
<li class="nl label">状态</li><li class="sl">${card.statusDesc}</li>
<li class="nl line"></li>
<li class="nl label">卡号</li><li class="sl">***</li>
<li class="nl label">密码</li><li class="sl">***</li>
<li class="nl label">价格</li><li class="sl">${(card.price / 1000)?string.currency}</li>
<li class="nl label">过期日期</li><li class="sl">${card.expireDate?string("yyyy-MM-dd")}</li>
<li class="nl line"></li>
<li class="nl label">代理商ID</li><li class="sl">${card.agentId}</li>
<#if agent??>
<li class="nl label">代理商名称</li><li class="sl">${agent.name}</li>
<li class="nl label">代理商级别</li><li class="sl">${agent.rankDesc}</li>
</#if>
<li class="nl line"></li>
<li class="nl label">销售状态</li><li class="sl">${card.saleStatusDesc}</li>
<li class="nl label">销售时间</li><li class="sl">${(card.saleTime?string("yyyy-MM-dd HH:mm"))!''}</li>
<li class="nl line"></li>
<li class="nl label">充值状态</li><li class="sl">${card.rechargeStatusDesc}</li>
<li class="nl label">充值订单号</li><li class="sl">${(card.payOrderno)!''}</li>
<li class="nl label">充值kc号</li><li class="sl">${(card.uid)!''}</li>
<li class="nl label">充值电话号码</li><li class="sl">${(card.phone)!''}</li>
<li class="nl label">充值时间</li><li class="sl">${(card.rechargeTime?string("yyyy-MM-dd HH:mm"))!''}</li>
</ul>
</#if>
<#if errmsg??><div style="padding:20px 0px 0px 30px;overflow: hidden;height:30px;"><label class="error">${errmsg}</label></div></#if></form>
<#include "/template/include/footer.ftl">
</body>
</html>