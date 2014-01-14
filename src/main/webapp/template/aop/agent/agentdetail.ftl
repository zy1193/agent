<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台-代理商详细信息</title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
<style>
	td{
		width: 8%;
	}
</style>
<script type="text/javascript">
   function show(){
    document.getElementById("d").style.display=document.getElementById("d").style.display=="none"?"block":"none";
   }
    function showsj(){
    document.getElementById("ds").style.display=document.getElementById("ds").style.display=="none"?"block":"none";
   }
</script>
</head>
<body>
<center>代理商详细信息<br/><font color="#ac9dfd">温馨提示:点击编号查看下级代理商信息，点击姓名显示上级代理商信息</font></center>
<hr/>
<table width="100%" bordercolor="#EEEEEE" border="1">
  <tr>
    <td>代理商编号</td><td>代理商真实姓名</td><td>上级代理商</td><td>代理商级别</td><td>状态</td><td>电话号码</td><td>手机号码</td><td>身份证号码</td><td>QQ号码</td><td>旺旺号码</td><td>淘宝店网址</td><td>拍拍店网址</td>
  </tr>
  <#if agent??>
  <tr style="cursor:hand"   onmouseover="this.bgColor='#C2D6EE'" onmouseout="this.bgColor='#f9f9f9'">
   <td><a href="javascript:show();" <#if agent.agents.size() gt 0>title="点击编号查看其下级代理商"<#else>title="该代理商没有下级"</#if>>${agent.id!"无"}</a></td><td><a href="javascript:showsj();"  <#if ag ??>title="点击编号查看其上级代理商"<#else>title="该代理商没有上级"</#if>>${agent.name!"无"}</td><td>${agent.superName!"无"}</td><td>${agent.rankDesc!"无"}</td><td>${agent.statusDesc!"无"}</td><td>${agent.phoneNo!"无"}</td><td>${agent.mobileNo!"无"}</td><td>${agent.idCardNo!"无"}</td><td>${agent.qq!"无"}</td><td>${agent.wangwang!"无"}</td><td>${agent.taobaoShopUrl!"无"}</td><td>${agent.paipaiShopUrl!"无"}</td>
  </tr>
  </#if>
</table>
   	<table width="100%" border="1" style="" id="d" style="margin:-1px -1px -1px -1px;display:none;">
     <tr><td colspan="12" align="center">下级代理商列表</td></tr>
   	<tr bgcolor="#dee1fe" >
    <td>代理商编号</td><td>代理商真实姓名</td><td>上级代理商</td><td>代理商级别</td><td>状态</td><td>电话号码</td><td>手机号码</td><td>身份证号码</td><td>QQ号码</td><td>旺旺号码</td><td>淘宝店网址</td><td>拍拍店网址</td>
 	</tr>
 	<#if agent.agents ?? && agent.agents.size() gt 0>
   <#list agent.agents as age>
    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'"><td>${age.id!"无"}</td><td width="5%">${age.name!"无"}</td><td>${agent.name!"无"}</td><td>${age.rankDesc!"无"}</td><td>${age.statusDesc!"无"}</td><td>${age.phoneNo!"无"}</td><td>${age.mobileNo!"无"}</td><td>${age.idCardNo!"无"}</td><td>${age.qq!"无"}</td><td>${age.wangwang!"无"}</td><td>${age.taobaoShopUrl!"无"}</td><td>${age.paipaiShopUrl!"无"}</td>
    </tr>
   </#list>
   <#else>
   <tr><td colspan="12" align="center">温馨提示：该代理商无下级代理！</td></tr>
   </#if> 
    </table>
   	<table width="100%" border="1" style="margin:-1px -1px -1px -1px;display:none;" id="ds">
   	      <tr><td colspan="12" align="center">上级代理商信息</td></tr>
   	<tr  bgcolor="#d5c4bf">
    <td>代理商编号</td><td>代理商真实姓名</td><td>上级代理商</td><td>代理商级别</td><td>状态</td><td>电话号码</td><td>手机号码</td><td>身份证号码</td><td>QQ号码</td><td>旺旺号码</td><td>淘宝店网址</td><td>拍拍店网址</td>
 	</tr>
 	<#if ag ??>
    <tr onmouseover="this.bgColor='#C2D6EE'" onmouseout="this.bgColor='#f9f9f9'"><td>${ag.id!"无"}</td><td width="5%">${ag.name!"无"}</td><td><#if age ??>${age.name!"无"}<#else>无</#if></td><td>${ag.rankDesc!"无"}</td><td>${ag.statusDesc!"无"}</td><td>${ag.phoneNo!"无"}</td><td>${ag.mobileNo!"无"}</td><td>${ag.idCardNo!"无"}</td><td>${ag.qq!"无"}</td><td>${ag.wangwang!"无"}</td><td>${ag.taobaoShopUrl!"无"}</td><td>${ag.paipaiShopUrl!"无"}</td>
    </tr>
    <#else>
    <tr><td colspan="12" align="center">温馨提示：该代理商没有上级代理！</td></tr>
    </#if>
    </table>
</body>
</html>