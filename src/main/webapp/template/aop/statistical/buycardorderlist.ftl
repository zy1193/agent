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
  <title>提卡查询</title>
  <script type="text/javascript" src="<@s.url value='/js/time.js'/>"></script>
 </head>
 <body>
 <form name="queryform" namespace="/aop" action="buycardstat.action" >
   <div class="right_box">
  	<div id="titlediv"> 
  		<table>
  			<tr>
  				<th>&nbsp;&nbsp;</th>
  			    <th>代理商账号</th><th><input class="required ipb_s" type="text" name='agentId' id="agentId" value="${agentId!''}"/></th>
  				<th>&nbsp;订单日期：</th>
  				<th>&nbsp;从：<input  class="required ipb_s" type="text" name='dateStart' id="dateStart" onclick="setday(this,this)" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>&nbsp;到：<input  class="required ipb_s" type="text" name='dateEnd' id="dateEnd" onclick="setday(this,this)" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th>&nbsp;商品名称：</th>
  				<th><select class="required ipb_l" name='productId' id='productId'>
  					  <option value=''>--请选择--</option>
					  <#if products??>
	   				  <#list products as p>
	   				  <option value='${p.id!""}' <#if productId??><#if productId==p.id> selected="selected" <#else> </#if> </#if>>${p.name!""}</option>
	   				  </#list>
	   				  </#if>
	   				 </select>
  				</th>
  				<th style="padding-bottom:5px;"><button class="btn but_s" type='submit' >查 询</button></th>
  			</tr>
  		</table>
  	</div>
  </form>
	  <div id="div">
	  <table id="listTable" border='0'  width="100%">
	    <tr >
	        <th width="5%">序号</th>
		  	<th>商品名称</th>
		  	<th>代理商账号</th>
		  	<th>代理商名称</th>
		  	<th width="10%">购买数量</th>
		  	<th width="10%">总额</th>
		  	<th width="5%">状态</th>
		  	<th width="18%">订单时间</th>
	    </tr>
	    <#if buycardOrders??>
	    <#list buycardOrders as order>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
	    	<td align="center">${order_index}</td>
		  	<td align="center">${order.productName!""}</td>
		  	<td align="center">${order.agentId}</td>
		  	<td align="center">${order.agentName!""}</td>
		  	<td align="center">${order.sumProductCount!""}</td>
		  	<td align="center">${order.sumTotalPrice/1000!""}</td>
		  	<td align="center"><#if order.status=="0">初始<#elseif order.status=="1">处理中<#elseif order.status=="2">成功<#elseif order.status=="3">失败<#else> </#if></td>
		  	<td align="center">${(order.orderTime?string('yyyy-MM-dd'))!""}</td>
	    </tr>
	    </#list>
	    </#if>
	    <tr><th colspan="4" align="center">小计:</th><th>${sumcount}</th><th>${sumPrice/1000}</th><th colspan="2"></th></tr>
	    </table>
	    <div style="text-align:right"><@nav.pagenav /></div>
	    </div></div>
</body>
</html>
