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
<script>
	var url = "<@s.url value='/amp/cardinfo.action'/>";
	
	$(function() {
		var dialog=$("#dialog");
		dialog.dialog({
			autoOpen: false,
			width:600,
			modal: false
		});
	
	});
	
	function showcard(no) {
		$.get(
				url,
				{"card.no":no},
				function(txt) {
					$("#dialog_content").html(txt);
					$("#dialog").dialog("open");
				}
			);
	}

</script>
</head>
 <body>
  <form name="queryform" namespace="/amp" action="cardlist.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr>
  				<th>卡号</th>
  				<th><input class="ipb_s"  type='text' name='cardno' id='cardno' value='${cardno!""}' /></th>
  				<th>密码</th>
  				<th><input class="ipb_s"  type='text' name='cardpassword' id='cardpassword' value='${cardpassword!""}' /></th>
  				<th>购买日期</th>
  				<th><input style="width:80px" class="ipb_s"  type="text" name='dateStart' id="dateStart" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>到<input style="width:80px" class="ipb_s"  type="text" name='dateEnd' id="dateEnd" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th>状态</th>
  				<th><select  class="ipb_l"  name='saleStatus' id='saleStatus'>
  						<option value='999' <#if saleStatus??><#if saleStatus=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='0' <#if saleStatus??><#if saleStatus=="0"> selected="selected" <#else> </#if> </#if> >未销售</option>
  						<option value='1' <#if saleStatus??><#if saleStatus=="1"> selected="selected" <#else> </#if> </#if> >销售中</option>
  						<option value='2' <#if saleStatus??><#if saleStatus=="2"> selected="selected" <#else> </#if> </#if> >已销售</option>
  				    </select>
  				</th>
  				<th>每页显示<input class="ipb_s" style="width:20px" type="text" value="${pagesize!15}" name="pagesize"/>条</th>
  				<th style="padding-bottom:5px;"><button type='submit' class="btn but_s" >查 询</button></th>
  			</tr>
  		</table>
  	</div>
  </form>
	  <div id="div">
	  <table id="listTable" border='0'  width="100%">
	    <tr>
		  	<th>卡名称</th>
		  	<th>卡号</th>
		  	<th>密码</th>
		  	<th>价格(元)</th>
		  	<th>购入日期</th>
		  	<th>状态</th>
		  	<th>详情</th>
	    </tr>
	    <#if cardlist??>
	    <#list cardlist as card>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
		  	<td align="center">${card.productName!""}</td>
		  	<td>${card.no!""}</td>
		  	<td>${card.password!""}</td>
		  	<td>${card.price/1000!""}</td>
		  	<td>${(card.buyTime?string('yyyy-MM-dd'))!""}</td>
		  	<td><#if card.saleStatus=="0">未销售<#elseif card.saleStatus=="1"><font color="#0000FF">销售中</font><#elseif card.saleStatus=="2"><font color="#FF0000">已销售</font><#else> </#if></td>
		  	<td><a onclick="return showcard('${card.no}');" href="#">查看</a></td>
	    </tr>
	    </#list>
	    </#if>
	    </table>
	    <div style="text-align:right"><@nav.pagenav pageSize=(pagesize!15) /></div>
	  </div>
	  </div>
	  
<!-- jquery对话框 -->
<div id="dialog" title="卡信息"><p id="dialog_content"></p></div>
</body>
</html>
