<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
	<style>
		#div {
			padding: 5px 0px;
		}
		#titlediv {
			padding-top: 15px;
			
		}
		 
	</style>
  <title> 我的日报 </title>
 </head>
 <body>

  <form name="queryform" namespace="/amp" action="mydayrpt.action" >
   <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr>
  				<th>&nbsp;&nbsp;</th>
  				<th>&nbsp;交易日期：</th>
  				<th>&nbsp;从：<input class="ipb_s" type="text" name='dateStart' id="dateStart" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>&nbsp;到：<input class="ipb_s" type="text" name='dateEnd' id="dateEnd" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th>&nbsp;交易类型：</th>
  				<th><select class="ipb_l" name='transType' id='transType'>
  						<option value='999' <#if transType??><#if transType=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='01' <#if transType??><#if transType=="01"> selected="selected" <#else> </#if> </#if> >购卡</option>
  						<option value='02' <#if transType??><#if transType=="02"> selected="selected" <#else> </#if> </#if> >授信</option>
  						<option value='03' <#if transType??><#if transType=="03"> selected="selected" <#else> </#if> </#if> >被授信</option>
  					</select>
  				</th>
  				<th style="padding-bottom:5px;"><button type='submit' class="btn but_s">查 询</button>&nbsp;<!--input type='button' onClick='reset1()' value='重 置' /--></th>
  			</tr>
  		</table>
  </form>
	  <table id="listTable" border='0'  width="100%">
	    <tr >
	        <th width="5%">序号</th>
		  	<th width="10%">交易类型</th>
		  	<th width="15%">交易日期</th>
		  	<th>支出金额（小计/元）</th>
		  	<th>收入金额（小计/元）</th>
		  	<th>交易笔数</th>
	    </tr>
	    <#if acctDayReportList??>
	    <#list acctDayReportList as dr>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
	    	<td>${dr_index}</td>
		  	<td><#if dr.transType=="01">购卡<#elseif dr.transType=="02">授信<#elseif dr.transType=="03">被授信<#else> </#if></td>
		  	<td>${(dr.transDate?string('yyyy-MM-dd'))!""}</td>
		  	<td>${dr.payAmount/1000!""}</td>
		  	<td>${dr.incomeAmount/1000!""}</td>
		  	<td>${dr.transCount!""}</td>
	    </tr>
	    </#list>
	    </#if>
	    <tr >
		  	<th colSpan='3'>总计：</th>
		  	<th>${sumpayamount/1000!""}</th>
		  	<th>${sumincomeAmount/1000!""}</th>
		  	<th>${sumtransCount!""}</th>
	    </tr>
	    </table>
	    <div style="text-align:right"><@nav.pagenav /></div>
   </div>
	  </div></body>
</html>
