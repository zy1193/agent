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
  <title>订单查询</title>
  <script type="text/javascript" src="<@s.url value="/js/calendar.js"/>"></script>
 </head>
 <body>
   <div class="right_box">
  	<div id="titlediv"> <form name="queryform" namespace="/amp" action="buycardorder.action" >
  		<table>
  			<tr>
  				<th>&nbsp;&nbsp;</th>
  				<th>订单号：</th>
  				<th><input  class="required ipb_s" type='text' name='orderSn' id='orderSn' value='${orderSn!""}' /></th>
  				<th>&nbsp;订单日期：</th>
  				<th>&nbsp;从：<input  class="required ipb_s" type="text" name='dateStart' id="dateStart" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>&nbsp;到：<input  class="required ipb_s" type="text" name='dateEnd' id="dateEnd" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th>&nbsp;状态：</th>
  				<th><select class="required ipb_l"  name='orderStatus' id='orderStatus'>
  						<option value='2' <#if orderStatus??><#if orderStatus=="2"> selected="selected" <#else> </#if> </#if> >成功</option>
  						<option value='3' <#if orderStatus??><#if orderStatus=="3"> selected="selected" <#else> </#if> </#if> >失败</option>
  						<option value='0' <#if orderStatus??><#if orderStatus=="0"> selected="selected" <#else> </#if> </#if> >初始中</option>
  						<option value='1' <#if orderStatus??><#if orderStatus=="1"> selected="selected" <#else> </#if> </#if> >处理中</option>
  						<option value='999' <#if orderStatus??><#if orderStatus=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
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
		    <th>订单号</th>
		  	<th>商品名称</th>
		  	<th width="10%">购买数量</th>
		  	<th width="10%">单价</th>
		  	<th width="10%">总额</th>
		  	<th width="5%">状态</th>
		  	<th width="18%">订单时间</th>
	    </tr>
	    <#if buycardOrderList??>
	    <#list buycardOrderList as order>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
	    	<td>${order_index}</td>
		  	<td>${order.orderSn!""}</td>
		  	<td>${order.productName!""}</td>
		  	<td>${order.productCount!""}</td>
		  	<td>${order.productPrice/1000!""}</td>
		  	<td>${order.totalAmount/1000!""}</td>
		  	<td><#if order.status=="0">初始<#elseif order.status=="1">处理中<#elseif order.status=="2">成功<#elseif order.status=="3">失败<#else> </#if></td>
		  	<td>${(order.orderTime?string('yyyy-MM-dd hh:mm:ss'))!""}</td>
	    </tr>
	    </#list>
	    </#if>
	    </table>
	    <div style="text-align:right"><@nav.pagenav /></div>
	    </div></div>
</body>
</html>
