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
  <title> 余额统计列表 </title>
 </head>
 <body>
  <form name="queryform" namespace="/aop" action="balstat.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr>
  				<th></th>
  				<th>&nbsp;状态：</th>
  				<th><select class="required ipb_l"  name='status' id='status'>
  						<option value='999' <#if status??><#if status=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='0' <#if status??><#if status=="0"> selected="selected" <#else> </#if> </#if> >正常 </option>
  						<option value='1' <#if status??><#if status=="1"> selected="selected" <#else> </#if> </#if> >冻结</option>
  						<option value='2' <#if status??><#if status=="2"> selected="selected" <#else> </#if> </#if> >注销</option>
  						<option value='3' <#if status??><#if status=="3"> selected="selected" <#else> </#if> </#if> >未审核</option>
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
	        <th>序号</th>
	        <th>总可用余额</th>
	        <th>冻结总余额</th>
		  	<th>状态</th>
		  	<th>代理商总个数</th>
	    </tr>
	    <#if accts??>
	    <#list accts as ac>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
	    	<td align="center">${ac_index}</td>
		  	<td align="center">${ac.sAvailableBalance/1000!""}</td>
		  	<td align="center">${ac.sFrozenBalance/1000!""}</td>
		  <td align="center"><#if ac.status=="0">正常<#elseif ac.status=="1">冻结 <#elseif ac.status=="2">注销<#elseif ac.status=="3">未审核<#else> </#if></td>
	    <td align="center">${ac.count}</td>
	    </tr>
	    </#list>
	    </#if>
	    <tr><th align="center">小计：</th><th>${sumaprice/1000!""}</th><th>${sumfprice/1000!""}</th><th></th><th>${scount!""}</th></tr>
	    </table>
	    <div style="text-align:right"><@nav.pagenav /></div>
	  </div>
	  </div>
 </body>
</html>
