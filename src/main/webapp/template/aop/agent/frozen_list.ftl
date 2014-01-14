<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台-冻结登录帐号</title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
<script>
	var url = "<@s.url value='/aop/frozen.action'/>";
	function doagent(status,id) {
		var c,s;
		if (status=="0") {
			c = "解冻";
			s = "正常";
		} else if (status=="1") {
			c = "冻结";
			s = "冻结";
		} else if (status=="2") {
			c = "注销";
			s = "注销";
		} else {
			c = "";
		}
		
		if (confirm(c + "该帐号，您确定吗？")) {
			$.get(
				url, {"agent.id":id,"agent.status":status },
				function(txt) {
					if (txt) {
						alert(txt);
					} else {
						if (status=="0") {
							$("#a1_" + id).show();
							$("#a2_" + id).hide();
							$("#a3_" + id).show();
						} else if (status=="1") {
							$("#a1_" + id).hide();
							$("#a2_" + id).show();
							$("#a3_" + id).show();
						} else if (status=="2") {
							$("#a1_" + id).hide();
							$("#a2_" + id).hide();
							$("#a3_" + id).hide();
							// 隐藏重置密码 显示删除
							$("#reset_" + id).hide();
							$("#delete_" + id).show();
						} else {
						}
						$("#td_" + id).html(s);
					}
				}
			);
		}
		return false;
			
	}
	
	var delete_url="<@s.url value='/aop/deleteagent.action' />";
	function deleteAgent(id) {
		if (confirm("删除该代理商资料，该代理商的钱包也将同时被删除，您确定吗？")) {
			$.get(
				delete_url,
				{"agent.id":id},
				function(data) {
					if(data) {
						alert("删除代理商资料失败：" + data);
					} else {
						$("#tr_" + id).hide(200);
					}
				}
			);
		}
		return false;
	}
	var reset_url="<@s.url value='/aop/resetagentpwd.action'/>";
	function resetPassword(id) {
		if (confirm("重置该代理商密码，您确定吗？")) {
			$.get(
				reset_url,
				{"agent.id":id},
				function(data) {
					alert("代理商密码已重置，新密码是：" + data);
				}
			);
		}
		return false;
	}
</script>
</head>
<body>
<#include "/template/include/header.ftl">
<form action="<@s.url value='/aop/frozenlist.action'/>">
<ul class="fix">
<li class="nl">登录名</li>
<li class="sl" style="padding-left:8px"><input type="text" name="agent.id" value='${(agent.id)!''}' class="ipb_m"/></li>
<li class="sl"><button type="submit"  class="btn but_s">搜索</button></li>
</ul>
<div style="margin:10px 0 0 40px;color:FireBrick;font-size:11px">操作提示 >> 注销：代理商不能登录系统，但资料和钱包将保留；删除：代理商资料和钱包都将被删除。</div>
</form>
<table width="100%" border="0" id="listTable" style="margin:10px 0 0 20px;">
<tr>
<th>登录名</th>
<th>真实名字</th>
<th>品牌</th>
<th>级别</th>
<th>手机号码</th>
<th>状态</th>
<th style="width:200px">操作</th>
</tr>
<#if agents??>
	<#list agents as agent>
	<tr id="tr_${agent.id}" title="点击编号查看详细信息" style="cursor:hand" onmouseover="this.bgColor='#C2D6EE'" onmouseout="this.bgColor='#f9f9f9'">
	<td><a  href="<@s.url value='/aop/showDetail.action?agent.id=${agent.id}'/>">${agent.id}</a></td>
	<td>${agent.name}</td>
	<td>${agent.brandid}</td>
	<td>${agent.rankDesc}</td>
	<td>${agent.mobileNo!""}</td>
	<td id="td_${agent.id}" align="center">${agent.statusDesc!""}</td>
	<td align="center">
		<#if agent.status=="0">
			<a id="a1_${agent.id}" href="#" onclick="return doagent('1','${agent.id}')">冻结</a>
			<a style="display:none" id="a2_${agent.id}" href="#" onclick="return doagent('0','${agent.id}')">解冻</a>
			<a id="a3_${agent.id}" href="#" onclick="return doagent('2','${agent.id}')">注销</a>
		<#elseif agent.status=="1" >
			<a style="display:none" id="a1_${agent.id}" href="#" onclick="return doagent('1','${agent.id}')">冻结</a>
			<a id="a2_${agent.id}" href="#" onclick="return doagent('0','${agent.id}')">解冻</a>
			<a id="a3_${agent.id}" href="#" onclick="return doagent('2','${agent.id}')">注销</a>
		</#if>
		<#if agent.status!="2">
			<a href="#" style="display:" id="reset_${agent.id}" onclick="return resetPassword('${agent.id}')">重置密码</a>
			<a href="#" style="display:none" id="delete_${agent.id}" onclick="return deleteAgent('${agent.id}')">删除代理商</a>
		<#else>
			<a href="#" style="display:none" id="reset_${agent.id}" onclick="return resetPassword('${agent.id}')">重置密码</a>
			<a href="#" style="display:" id="delete_${agent.id}" onclick="return deleteAgent('${agent.id}')">删除代理商</a>
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