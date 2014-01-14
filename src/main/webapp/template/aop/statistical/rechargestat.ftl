<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
	<style>
		#div {
			width: auto;
			height: 400px;
			padding: 5px 0px;
		}
		#titlediv {
			width: 100%;			
			padding-top: 15px;
			
		}
		 
	</style>
  <title> 充值统计列表 </title>
 <script type="text/javascript" src="<@s.url value='/js/time.js'/>"></script>
 
 </head>
 <script type="text/javascript">
  	  </script>
 <body>
  <form name="queryform" namespace="/aop" action="rechargestat.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr>
  				<th></th>
				<th>代理商账号</th><th><input class="required ipb_s" type="text" name='agentId' id="agentId" value="${agentId!''}"/></th>
  				<th>充值日期</th>
  				<th><input class="required ipb_s" type="text" name='dateStart' id="dateStart" onclick="setday(this,this)" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>到<input class="required ipb_s" type="text" name='dateEnd' id="dateEnd" onclick="setday(this,this)" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th style="padding-bottom:5px;"><button type='submit' class="btn but_s">查 询</button><!--input type='button' onClick='reset1()' value='重 置' /--></th>
  			</tr>
  		</table>
  	</div>
  </form>
	  <div id="div">
	  <table id="listTable" border='0'  width="100%">
	    <tr>
	        <th width="5%">序号</th>
	        <th>代理商账号</th>
	        <th>代理商名称</th>
		  	<th>充值状态</th>
		  	<th>充值笔数</th>
		  	<th>充值总金额</th>
		  	<th width="15%">充值时间</th>
	    </tr>
	    <#if cards??>
	    <#list cards as card>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
	    	<td align="center">${card_index}</td>
	    	<td align="center">${card.agentId}</td>
	    	<td align="center">${card.agentName}</td>
		  	<td align="center"><#if card.rechargeStatus=="0">未充值<#elseif card.rechargeStatus=="1">已充值<#else> </#if></td>
		  	<td align="center">${card.count}</td>
		  	<td align="center">${card.sumPaymoney}</td>
		  	<td align="center">${(card.rechargeTime?string('yyyy-MM-dd'))!""}</td>
	    </tr>
	    </#list>
	    </#if>
	    <tr><th colspan="4">小计：</th><th>${sumCount}</th><th>${sumprice/1000}</th><th></th></tr>
	    </table>
	    <div style="text-align:right"><@nav.pagenav /></div>
	  </div>
	  </div>
 </body>
</html>
