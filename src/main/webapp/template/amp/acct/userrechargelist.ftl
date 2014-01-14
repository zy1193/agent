<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<@s.url value="/css/ui-lightness/jquery.ui.css"/>" rel="stylesheet" type="text/css" media="all" />
<style>
	#div {
		width: auto;
		height: 400px;
		padding: 5px 0px;
	}
	#titlediv {
		width:100%;			
		padding-top: 15px;
		
	}
	 
</style>
<title> 卡列表 </title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.ui.js"/>"></script>
 <script type="text/javascript" src="<@s.url value='/js/time.js'/>"></script>
 <script type="text/javascript" src="<@s.url value="/js/DatePicker/WdatePicker.js"/>"></script>
<script>
	var url = "<@s.url value='/aop/userrechargelist.action'/>";
	
	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
	

</script>
</head>
 <body>
  <form id="queryform" name="queryform" namespace="/aop" action="userrechargelist.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr style="text-align:right;">
  				<th>代充账号</th>
  				<th><input class="ipb_s" style="float:left" type='text' name='account' id='account' value='${account!""}' /></th>
  				<th>充值金额</th>
  				<th><input class="ipb_s" style="float:left;width:80px"  type='text' name='sum' id='sum' value='${sum!""}' /></th>
  				<th>充值日期</th>
  				<th><input onClick="WdatePicker()"  style="width:80px" style="float:left;"  class="ipb_s"  type="text" name='dateStart' id="dateStart" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>到<input onClick="WdatePicker()"  style="width:80px"  class="ipb_s"  type="text" name='dateEnd' id="dateEnd" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th>账号类型</th>
  				<th><select  class="ipb_l"  name='accountType' id='accountType'>
  						<option value='999' <#if accountType??><#if accountType=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='1' <#if accountType??><#if accountType=="1"> selected="selected" <#else> </#if> </#if> >UID</option>
  						<option value='2' <#if accountType??><#if accountType=="2"> selected="selected" <#else> </#if> </#if> >手机</option>
  						<option value='3' <#if accountType??><#if accountType=="3"> selected="selected" <#else> </#if> </#if> >邮箱</option>
  				    </select>
  				</th>
  				<th>充值状态</th>
  				<th><select  class="ipb_l"  name='status' id='status'>
  						<option value='999' <#if status??><#if status=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='2' <#if status??><#if status=="2"> selected="selected" <#else> </#if> </#if> >充值成功</option>
  						<option value='3' <#if status??><#if status=="3"> selected="selected" <#else> </#if> </#if> >充值失败</option>
  						<option value='1' <#if status??><#if (status=='0' || status=='1')> selected="selected" <#else> </#if> </#if> >处理中</option>
  				    </select>
  				</th>
  				<th>每页显示</th>
  				<th><input class="ipb_s" style="width:20px" type="text" value="${pagesize!15}" name="pagesize"/>条</th><th style="padding-bottom:5px;"><button type='submit' id="query_btn" class="btn but_s" >查 询</button></th>
  				
  			</tr>
  		</table>
  	</div>
  </form>
  
  <form id="frozenform" name="frozenform" namespace="/aop" action="cardfrozen.action" >
	  <div id="div">
		  <table id="listTable" border='0'  width="100%">
		    <tr>
			  	<th>代充账号</th>
			  	<th>充值金额(元)</th>
			  	<th>账号类型</th>
			  	<th>充值时间</th>
			  	<th>充值状态</th>
			  	<th>代理商</th>
			  	<th>充值订单</th>
		    </tr>
		    <#if userRechargeList??>
		    <#list userRechargeList as list>
		    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
			  	<td align="center">${list.account!""}</td>
			  	<td>${list.rechargeAmount/1000}</td>
			  	<td><#if list.accountType=="1">UID<#elseif list.accountType=="2">手机<#elseif list.accountType=="3">邮箱<#else> </#if></td>
			  	<td>${(list.rechargeTime?string('yyyy-MM-dd'))!""}</td>
			  	<td><#if (list.status=='0' || list.status=='1')><font color="#0000FF">处理中</font><#elseif list.status=="2">充值成功<#elseif list.status=="3"><font color="#FF0000">充值失败</font><#else></#if></td>
			  	<td>${list.agentId!""}</td>
			  	<td>${list.orderSn!""}</td>
		    </tr>
		    </#list>
		    </#if>
		    </table>
		    <div style="text-align:right">
		    	<span  style="float:left" >代充记录数：<span style="color:red">${rechargeCount!"0"}</span>,   代充金额:<span style="color:red">${rechargeSum!"0"}</span>元</span>
		    	<@nav.pagenav pageSize=(pagesize!15) />
		    </div>
		  </div>
		 </form>
	  </div>
</body>
</html>
