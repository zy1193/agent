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
	var url = "<@s.url value='/aop/cardinfoaop.action'/>";
	
	$(function() {
		var dialog=$("#dialog");
		dialog.dialog({
			autoOpen: false,
			width:600,
			modal: false
		});
		
		$("#total_chk").click(function(){
			var checked=$(this).attr("checked");
			var chk=$(":checkbox:gt(0)");
			for(var i=0;i<chk.length;i++){
				$(chk[i]).attr("checked",checked);
			}
			
		});

		$("#frozen").click(function(){
			$("#operFlag").val("frozen");
			frozenOrThaw();
		});
		$("#thaw").click(function(){
			$("#operFlag").val("thaw");
			frozenOrThaw();
		});
	});
	
	function frozenOrThaw(){
		$("agentId1").val($("#agentId").val());
		var chk=$(":checkbox:gt(0)");
		var cardsNo='';
		for(var i=0;i<chk.length;i++){
			if($(chk[i]).attr("checked")){
				//判断是否是未充值
				if($(chk[i]).next(":hidden").val()=='0'){
					cardsNo=cardsNo+','+$(chk[i]).val();
				}
			}
		}
		if(!trim(cardsNo)){
			alert("请至少选择一条未充值的代理卡！")
			return;
		}else{
			cardsNo=cardsNo.substring(1);		
			var agentId=$("#agentId").val();
			$.ajax({
			    url: 'cardfrozen.action',
			    type: 'POST',
			    dataType: 'html',
			    timeout: 1000,
			     data: 'cardsNo='+cardsNo+'&agentId='+agentId+'&operFlag='+$("#operFlag").val(),
			    error: function(){
			        alert('更改失败！');
			    },
			    success: function(txt){
			    	if(txt){
			    		alert(txt);
			    	}else{//刷新页面
			    		if($("#operFlag").val()=='frozen'){
			    			alert("冻结成功！");
			    		}else{
			    			alert("解冻成功！");
			    		}
			    		window.location.reload();
			    	}
			    }
			});
				
		}
	}
	
	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
	
	function showcard(no,agent_id) {
		$.get(
				url,
				{"card.no":no,"agent.id":agent_id},
				function(txt) {
					$("#dialog_content").html(txt);
					$("#dialog").dialog("open");
				}
			);
	}

</script>
</head>
 <body>
  <form id="queryform" name="queryform" namespace="/aop" action="querycardlist.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr style="text-align:right;">
  				<th>代理商ID</th>
  				<th><input class="ipb_s" style="float:left" type='text' name='agentId' id='agentId' value='${agentId!""}' /></th>
  				<th>卡号</th>
  				<th><input class="ipb_s" style="float:left"  type='text' name='cardno' id='cardno' value='${cardno!""}' /></th>
  				<th>密码</th>
  				<th><input class="ipb_s" style="float:left"  type='text' name='cardpassword' id='cardpassword' value='${cardpassword!""}' /></th>
  				<th>购买日期</th>
  				<th><input onClick="WdatePicker()"  style="width:80px" style="float:left;"  class="ipb_s"  type="text" name='dateStart' id="dateStart" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>到<input onClick="WdatePicker()"  style="width:80px"  class="ipb_s"  type="text" name='dateEnd' id="dateEnd" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th style="padding-bottom:5px;"><button type='submit' id="query_btn" class="btn but_s" >查 询</button></th>
  			</tr>
  			<tr  style="text-align:left;">
  				<th>销售状态</th>
  				<th><select  class="ipb_l"  name='saleStatus' id='saleStatus'>
  						<option value='999' <#if saleStatus??><#if saleStatus=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='0' <#if saleStatus??><#if saleStatus=="0"> selected="selected" <#else> </#if> </#if> >未销售</option>
  						<option value='1' <#if saleStatus??><#if saleStatus=="1"> selected="selected" <#else> </#if> </#if> >销售中</option>
  						<option value='2' <#if saleStatus??><#if saleStatus=="2"> selected="selected" <#else> </#if> </#if> >已销售</option>
  				    </select>
  				</th>
  				<th>充值状态</th>
  				<th><select  class="ipb_l"  name='rechargeStatus' id='rechargeStatus'>
  						<option value='999' <#if rechargeStatus??><#if rechargeStatus=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='0' <#if rechargeStatus??><#if rechargeStatus=="0"> selected="selected" <#else> </#if> </#if> >未充值</option>
  						<option value='1' <#if rechargeStatus??><#if rechargeStatus=="1"> selected="selected" <#else> </#if> </#if> >已充值</option>
  				    </select>
  				</th>
  				
  				<th>冻结状态</th>
  				<th><select  class="ipb_l"  name='status' id='status'>
  						<option value='999' <#if status??><#if status=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='0' <#if status??><#if status=="0"> selected="selected" <#else> </#if> </#if> >正常</option>
  						<option value='1' <#if status??><#if status=="1"> selected="selected" <#else> </#if> </#if> >冻结</option>
  				    </select>
  				</th>
  				<th>每页显示</th>
  				<th><input class="ipb_s" style="width:20px" type="text" value="${pagesize!15}" name="pagesize"/>条</th>
  			</tr>
  		</table>
  	</div>
  </form>
  
  <form id="frozenform" name="frozenform" namespace="/aop" action="cardfrozen.action" >
	  <div id="div">
		  <table id="listTable" border='0'  width="100%">
		    <tr>
		        <th style="width:32px;"><input type='checkbox' id="total_chk"/></th>
			  	<th>卡名称</th>
			  	<th>卡号</th>
			  	<th>密码</th>
			  	<th>价格(元)</th>
			  	<th>购入日期</th>
			  	<th>销售状态</th>
			  	<th>充值状态</th>
			  	<th>冻结状态</th>
			  	<th>详情</th>
		    </tr>
		    <#if cardlist??>
		    <#list cardlist as card>
		    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'">
		        <td><input type='checkbox' name="cardsNo"  value="${card.brandid!""}@${card.password!""}"/><input type="hidden" value="${card.rechargeStatus!""}"/></td>
			  	<td align="center">${card.productName!""}</td>
			  	<td>${card.no!""}</td>
			  	<td>${card.password!""}</td>
			  	<td>${card.price/1000!""}</td>
			  	<td>${(card.buyTime?string('yyyy-MM-dd'))!""}</td>
			  	<td><#if card.saleStatus=="0">未销售<#elseif card.saleStatus=="1"><font color="#0000FF">销售中</font><#elseif card.saleStatus=="2"><font color="#FF0000">已销售</font><#else> </#if></td>
			  	<td><#if card.rechargeStatus=="0">未充值<#elseif card.rechargeStatus=="1"><font color="#0000FF">已充值</font></#if></td>
			  	<td><#if card.status=="0">正常<#elseif card.status=="1"><font color="#0000FF">冻结</font></#if></td>
			  	<td><a onclick="return showcard('${card.no}','${card.agentId}');" href="#">查看</a></td>
		    </tr>
		    </#list>
		    </#if>
		    </table>
		    <div style="text-align:right">
		    	<input type="button"  class="btn but_s" id="frozen" value="冻结" style="float:left"/>
		    	<span style="float:left;" > | </span>
		    	<input type="button"  class="btn but_s" id="thaw" value="解冻" style="float:left"/>
		    	<span style="float:left;color:red;" > &nbsp;&nbsp;提示：只能对未充值的卡做冻结或者解冻！ </span>
		    	<input type="hidden" id="operFlag" name="operFlag"/>
		    	<input type="hidden" id="agentId1" name="agentId"/>
		    	<@nav.pagenav pageSize=(pagesize!15) />
		    </div>
		  </div>
		 </form>
	  </div>
	  
<!-- jquery对话框 -->
<div id="dialog" title="卡信息"><p id="dialog_content"></p></div>
</body>
</html>
