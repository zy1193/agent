<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台-开户审核</title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
<script>
	var url = "<@s.url value='/aop/audit.action' />";
	function audit(id) {
	
		var brandid = $("#brandid").val();
		if (brandid) {
			if (confirm("您确定要为该用户开通 " + brandid + " 钱包账户吗？")) {
				$.get(
					url, {"agent.id":id,"agent.brandid":brandid },
					function(txt) {
						if (txt) {
							alert(txt);
						} else {
							$("#td_" + id).html("<font color='gray'>账户已开通</font>");
						}
					}
				);
			}
		} else {
			alert("请输入代理商所属品牌");
			$("#brandid").focus();
		}		
		return false;
	}
		
	var delete_url="<@s.url value='/aop/deleteagent.action' />";
	function deleteAgent(id) {
		if (confirm("删除该代理商账户，该代理商的钱包也将同时被删除，您确定吗？")) {
			$.get(
				delete_url,
				{"agent.id":id},
				function(data) {
					if(data) {
						alert("删除代理商账户失败：" + data);
					} else {
						$("#td_" + id).html("<font color='red'>账户已删除</font>");
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
<form action="<@s.url value='/aop/audit.action'/>">
</form>
<div style="margin:20px 0 0 20px;">
代理商所属品牌(<font color="blue">品牌必须是如下内容[kc,sky,uu,efl,3g,...]</font>) <input type="text" id="brandid" class="ipb_m"/>

</div>
<table width="100%" border="0" id="listTable" style="margin:10px 0 0 20px;">
<tr>
<th>登录名</th>
<th>真实名字</th>
<th>级别</th>
<th>电话号码</th>
<th>手机号码</th>
<th style="width:100px">操作</th>
</tr>
<#if agents??>
	<#list agents as agent>
	<tr id="tr_${agent.id}">
	<td>${agent.id}</td>
	<td>${agent.name}</td>
	<td>${agent.rankDesc}</td>
	<td>${agent.phoneNo!""}</td>
	<td>${agent.mobileNo!""}</td>
	<td id="td_${agent.id}" align="center">
		<a href="#" onclick="return audit('${agent.id}')">审核</a>
		<a href="#" onclick="return deleteAgent('${agent.id}')">删除</a>
	</td>
	</tr>
	</#list>
</#if>
</table>
<div style="text-align:right"><@nav.pagenav /></div>
<#include "/template/include/footer.ftl">
</body>
</html>