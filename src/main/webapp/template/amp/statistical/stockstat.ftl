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
  <title> 库存统计 </title>
  <script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
 
 </head>
 <body>
  <form name="queryform" namespace="/amp" action="stockstat.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr>
  				<th>&nbsp;&nbsp;</th>
  				<th>&nbsp;种类：</th>
  				<th><select class="required ipb_l" name='productId' id='productId'>
  					  <option value=''>--请选择--</option>
					  <#if list??>
	   				  <#list list as p>
	   				  <option value='${p.id!""}'>${p.name!""}</option>
	   				  </#list>
	   				  </#if>
	   				 </select>
  				</th>
  				<th style="padding-bottom:5px;"><button type='submit' class="btn but_s">查 询</button>&nbsp;<!--input type='button' onClick='reset1()' value='重 置' /--></th>
  			</tr>
  		</table>
  	</div>
  </form>
	  <div id="div">
	  <table id="listTable" border='0'  width="100%">
	    <tr >
	        <th width="5%">序号</th>
		  	<th>种类</th>
		  	<th>状态</th>
		  	<th>数量</th>
		  	<th width="20%">价值（小计/元）</th>
	    </tr>
	    <#if stockstatlist??>
	    <#list stockstatlist as sk>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
	    	<td>${sk_index}</td>
		  	<td>${sk.productName!""}</td>
		  	<td><#if sk.saleStatus=="0">未销售<#elseif sk.saleStatus=="1">销售中<#else> </#if></td>
		  	<td>${sk.counts!""}</td>
		  	<td>${sk.pvalue/1000!""}</td>
	    </tr>
	    </#list>
	    </#if>
	    <tr bgColor='#D5D5D5'>
		  	<th colSpan='3'>总计：</th>
		  	<th>${sumcounts!""}</th>
		  	<th>${sumpvalue/1000!""}</th>
	    </tr>
	    </table>
	  </div>
	  </div>
 </body>
</html>
