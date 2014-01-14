<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
	<style>
		#div {
			width: auto;
			height:1%;
			padding: 5px 0px;;			
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
		function showDetail(divid){			//隐藏层，传入每行的序号
				var len = ($("#listTable tr").length-1)/2;	//总行数
		  		var d = document.getElementById("div"+divid);	//隐藏层id
		  		var img = document.getElementById("img"+divid);	//按钮id
		  		if(d.style.display=='none'){
		  			d.style.display='';//'block';IE浏览器的时候写成block,隐藏的层显示正常，但是其他浏览器不正常。写成空值全正常
		  			img.src="../images/b.gif";	//+号变-号
		  	  		for(var i=divid-len;i<divid;i++){	//点击其他按钮时隐藏前隐藏层(以上的层)
		  	  	  		if(document.getElementById("div"+i)!= null && document.getElementById("img"+i) !=null){
		  	  		       document.getElementById("div"+i).style.display='none';	
		  	  		       document.getElementById("img"+i).src="../images/a.gif";
		  	  		         }
		  	  		       }
	  	  		    for(var j=divid+len;j>divid;j--){	//点击其他按钮时隐藏前隐藏层(以下的层)
		  	  	  		if(document.getElementById("div"+j)!= null && document.getElementById("img"+j) !=null){
		  	  	  		document.getElementById("div"+j).style.display='none';
		  	  	  		document.getElementById("img"+j).src="../images/a.gif";
		  	  	  		  }
		  	  	  		}
		         }else{
			  	     d.style.display='none';
			  		 img.src="../images/a.gif";
		  	           }
		  	}
		  	
		  	function reset1(){	
			  document.queryform.cardno.value="";
			  document.queryform.dateStart.value="";
			  document.queryform.dateEnd.value="";
			  document.queryform.saleStatus.value="999";
		  	}
		  	
			function changeStatus(status,id,divid){
				$.ajax({
				    url: 'changestatus.action?status='+status+'&id='+id,
				    type: 'GET',
				    dataType: 'html',
				    timeout: 1000,
				    error: function(){
				        alert('更改失败！');
				    },
				    success: function(xml){
				        var th = document.getElementById("th"+divid);
				        alert('更改成功！');
				        var a = $(".a_" + divid);
				        if('0'==status){
				          th.innerHTML="未销售";
				          a.eq(0).hide(200);
				          a.eq(1).show(200);
				          a.eq(2).show(200);
				        }else if('1'==status){
				          th.innerHTML="销售中";
				          a.eq(0).show(200);
				          a.eq(1).hide(500);
				          a.eq(2).show(200);
				        }else if('2'==status){
				          th.innerHTML="已销售";
				          a.eq(0).hide(200);
				          a.eq(1).hide();
				          a.eq(2).hide(2000);
				        }
				    }
				});
			}
  	  </script>
  <script type="text/javascript" src="<@s.url value="/js/calendar.js"/>"></script>
 <body>
  <form name="queryform" namespace="/amp" action="cardstatus.action" >
  <div class="right_box">
  	<div id="titlediv">
  		<table>
  			<tr>
  				<th>卡号</th>
  				<th><input  class="required ipb_s" type='text' name='cardno' id='cardno' value='${cardno!""}' /></th>
  				<th>密码</th>
  				<th><input   class="required ipb_s" type='text' name='cardpassword' id='cardpassword' value='${cardpassword!""}' /></th>
  				<th>购买日期</th>
  				<th><input onclick="showCalendar(this)"  class="required ipb_s"  type="text" name='dateStart' id="dateStart" value="${(dateStart?string('yyyy-MM-dd'))!""}"/></th>
  				<th>到 <input  class="required ipb_s"  type="text" name='dateEnd' id="dateEnd" value="${(dateEnd?string('yyyy-MM-dd'))!""}"/></th>
  				<th>状态</th>
  				<th><select  class="required ipb_l"  name='saleStatus' id='saleStatus'>
  						<option value='999' <#if saleStatus??><#if saleStatus=="999"> selected="selected" <#else> </#if> </#if> >全部</option>
  						<option value='0' <#if saleStatus??><#if saleStatus=="0"> selected="selected" <#else> </#if> </#if> >未销售</option>
  						<option value='1' <#if saleStatus??><#if saleStatus=="1"> selected="selected" <#else> </#if> </#if> >销售中</option>
  						<option value='2' <#if saleStatus??><#if saleStatus=="2"> selected="selected" <#else> </#if> </#if> >已销售</option>
  				    </select>
  				</th>
  				<th style="padding-bottom:5px;"><button  class="btn but_s" type='submit' >查 询</button><!--input type='button' onClick='reset1()' value='重 置' /--></th>
  			</tr>
  		</table>
  	</div>
  </form>

	<div id="div">
	  <table id="listTable" border='0'  width="100%">
	    <tr >
		    <!--<th>代理商编号</th>-->
		  	<th>卡名称</th>
		  	<th>卡号</th>
		  	<th>密码</th>
		  	<th width="10%">价格(元)</th>
		  	<th width="15%">购入日期</th>
		  	<!--<th width="15%">销售日期</th>-->
		  	<th width="10%">状态</th>
		  	<th width="12%">操作</th>
		  	<th width="5%">详情</th>
	    </tr>    
	    <#if cardlist??>
	    <#list cardlist as card>
	    <tr onmouseover="this.bgColor='#f1f1f1'" onmouseout="this.bgColor='#f9f9f9'" >
		  	<td align="center">${card.productName!""}</td>
		    <!--<th>${card.agentId!""}</th>-->
		  	<td>${card.no!""}</td>
		  	<td>${card.password!""}</td>
		  	<td>${card.price/1000!""}</td>
		  	<td>${(card.buyTime?string('yyyy-MM-dd'))!""}</td>
		  	<!--<th>${(card.saleTime?string('yyyy-MM-dd'))!""}</th>-->
		  	<td id="th${card_index}"><#if card.saleStatus=="0">未销售<#elseif card.saleStatus=="1"><font color="#0000FF">销售中</font><#elseif card.saleStatus=="2"><font color="#FF0000">已销售</font><#else> </#if></th>
		  	<td id="tha${card_index}">
		  		<a class="a_${card_index}" style="display:<#if card.saleStatus!='1'>none</#if>" href='#' onClick="changeStatus(0,${card.id},${card_index})" >未销售</a>
		  		<a class="a_${card_index}" style="display:<#if card.saleStatus!='0'>none</#if>" href='#' onClick="changeStatus(1,${card.id},${card_index})" >销售中</a>
		  		<a class="a_${card_index}" style="display:<#if card.saleStatus=='2'>none</#if>" href='#' onClick="changeStatus(2,${card.id},${card_index})" >已销售</a>
		  	</td>
		  	<td><img src="../images/a.gif" id ='img${card_index}' title="点击展开" onclick='showDetail(${card_index});'></td>
	    </tr>
	    <!--以下为隐藏的层-->
	    <tr style="display:none;" id="div${card_index}">
	    	<td colSpan='8'>
	    	   <div>
	    	   		<table width="100%" border='0' id="popup">
	    	   		    <tr>
	    	   		       <td>卡号</td>
	    	   		       <td>${card.no!""}</td>
	    	   		       <td>订单号</td>
	    	   		       <td>${card.orderSn!""}</td>
	    	   		       <td>价格(元)</td>
	    	   		       <td>${card.price/1000!""}</td>
	    	   		       <td>购入时间</td>
	    	   		       <td>${(card.buyTime?string('yyyy-MM-dd'))!""}</td>
	    	   		       <td>销售时间</th>
	    	   		       <td>${(card.saleTime?string('yyyy-MM-dd'))!""}</td>
	    	   		       <td>产品名称</th>
	    	   		       <td>${card.productName!""}</td>
	    	   		    </tr>
	    	   		    <tr>
	    	   		       <td>密码</td>
	    	   		       <td>${card.password!""}</td>
	    	   		       <td>充值状态</td>
	    	   		       <td><#if card.rechargeStatus=="0">未充值<#elseif card.rechargeStatus=="1">已充值<#else> </#if></td>
	    	   		       <td>销售状态</td>
	    	   		       <td><#if card.saleStatus=="0">未销售<#elseif card.saleStatus=="1">销售中<#elseif card.saleStatus=="2">已销售<#else> </#if></td>
	    	   		       <td>充值时间</td>
	    	   		       <td>${(card.rechargeTime?string('yyyy-MM-dd'))!""}</td>
	    	   		       <td>过期日期</th>
	    	   		       <td>${(card.expireDate?string('yyyy-MM-dd'))!""}</td>
	    	   		       <td>帐号</th>
	    	   		       <td>${card.uid!""}</td>
	    	   		    </tr>
	    	   		</table>
	    	   </div>
	    	</th>
	    </tr>
	    </#list>
	    </#if>
	    </table>
	   <div style="text-align:right "><@nav.pagenav /></div>
	    
	    
	  </div>
	  </div>
 </body>
</html>

