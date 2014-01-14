<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台-冻结钱包帐号</title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
<script>
	var url = "<@s.url value='/aop/frozenacct.action' />";
	function doacct(status,id) {
		var c,s;
		if (status=="0") {
			c = "解冻";
			s = "正常";
		} else if (status=="1") {
			c = "冻结";
			s = "冻结";
		} else {
			c = "";
		}
		
		if (confirm(c + "该帐号，您确定吗？")) {
			$.get(
				url, {"acct.id":id,"acct.status":status },
				function(txt) {
					if (txt) {
						alert(txt);
					} else {
						if (status=="0") {
							$("#a1_" + id).hide();
							$("#a2_" + id).show();
						} else if (status=="1") {
							$("#a1_" + id).show();
							$("#a2_" + id).hide();
						} else {
						}
						$("#td_" + id).html(s);
					}
				}
			);
		}
		return false;
			
	}
</script>
</head>
<body>
<#include "/template/include/header.ftl">
<form action="<@s.url value='/aop/frozenacctlist.action'/>">
<ul class="fix">
<li class="nl">帐号</li>
<li class="sl" style="padding-left:8px"><input type="text" name="acct.id" value='${(acct.id)!''}' class="ipb_m"/></li>
<li class="sl"><button type="submit" class="btn but_s">搜索</button></li>
</ul>
</form>

<table width="100%" border="0" id="listTable" style="margin:20px 0 0 20px;">
<tr>
<th>帐号</th>
<th>品牌</th>
<th>可用余额</th>
<th>冻结余额</th>
<th>押金</th>
<th>状态</th>
<th style="width:100px">操作</th>
</tr>
<#if accts??>
	<#list accts as acct>
	<tr>
	<td>${acct.id}</td>
	<td>${acct.brandid}</td>
	<td align="right">${(acct.availableBalance/1000)?string.currency}</td>
	<td align="right">${(acct.frozenBalance/1000)?string.currency}</td>
	<td align="right">${(acct.deposit)?string.currency}</td>
	<td align="center" id="td_${acct.id}">${acct.statusDesc!""}</td>
	<td align="center">
		<#if acct.status=='0'>
			<a style="display:none" id="a1_${acct.id}" href="#" onclick="return doacct('0','${acct.id}');">解冻</a>
			<a id="a2_${acct.id}" href="#" onclick="return doacct('1','${acct.id}');">冻结</a>
		<#elseif acct.status='1'>
			<a id="a1_${acct.id}" href="#" onclick="return doacct('0','${acct.id}');">解冻</a>
			<a style="display:none" id="a2_${acct.id}" href="#" onclick="return doacct('1','${acct.id}');">冻结</a>
		</#if>	
	</td>
	</tr>
	</#list>
</#if>
</table>
<div style="text-align:right"><@nav.pagenav /></div>
<#include "/template/include/footer.ftl">
</body>
</html>