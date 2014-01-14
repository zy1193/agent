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
  <title> 卡列表 </title>
  <script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
 
 </head>
 <script type="text/javascript">
  	  </script>
 <body>
  <form name="queryform" namespace="/amp" action="myacct.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr>
  				<th></th>
  				<th>流水号</th>
  				<th><input class="required ipb_s" type='text' name='transSn' id='transSn' value='${transSn!""}' /></th>
  				<th>交易日期</th>
  				<th><input class="required ipb_s" type="text" name='dateStart' id="dateStart" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>到<input class="required ipb_s" type="text" name='dateEnd' id="dateEnd" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th>交易类型</th>
  				<th><select  class="required ipb_l" name='transType' id='transType'>
  						<option value='999' <#if transType??><#if transType=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='01' <#if transType??><#if transType=="01"> selected="selected" <#else> </#if> </#if> >购卡</option>
  						<option value='02' <#if transType??><#if transType=="02"> selected="selected" <#else> </#if> </#if> >授信</option>
  						<option value='03' <#if transType??><#if transType=="03"> selected="selected" <#else> </#if> </#if> >被授信</option>
  					</select>
  				</th>
  				<th style="padding-bottom:5px;"><button type='submit' class="btn but_s">查 询</button><!--input type='button' onClick='reset1()' value='重 置' /--></th>
  			</tr>
  		</table>
  	</div>
  </form>
	  <div id="div">
	  <table id="listTable" border='0'  width="100%">
	    <tr >
	        <th width="5%">序号</th>
		    <th>流水号</th>
		  	<th>交易类型</th>
		  	<th width="10%">支出金额（元）</th>
		  	<th width="10%">收入金额（元）</th>
		  	<th width="10%">余额（元）</th>
		  	<th width="15%">交易时间</th>
		  	<th>备注</th>
	    </tr>
	    <#if acctTransLog??>
	    <#list acctTransLog as at>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
	    	<td>${at_index}</td>
		  	<td>${at.transSn!""}</td>
		  	<td><#if at.transType=="01">购卡<#elseif at.transType=="02">授信<#elseif at.transType=="03">被授信<#else> </#if></td>
		  	<td>${at.payAmount/1000!""}</td>
		  	<td>${at.incomeAmount/1000!""}</td>
		  	<td>${at.balance/1000!""}</td>
		  	<td>${(at.transTime?string('yyyy-MM-dd hh:mm'))!""}</td>
		  	<td>${at.rmk!""}</td>
	    </tr>
	    </#list>
	    </#if>
	    </table>
	    <div style="text-align:right"><@nav.pagenav /></div>
	  </div>
	  </div>
 </body>
</html>
