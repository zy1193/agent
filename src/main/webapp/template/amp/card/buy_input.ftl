<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<@s.url value='/css/tabs.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<@s.url value="/css/ui-lightness/jquery.ui.css"/>" rel="stylesheet" type="text/css" media="all" />
<style>
	div form ul,div ul {
		padding-left:2px;
		margin: 0px;
	}
	ul.fix li.label {
		width:120px;
	}
	span {
		color:red;
	}
	input.l {
		width:400px;
	}
</style>
<title>代理商平台-购卡</title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.ui.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.metadata.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.tools.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.form.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.validate.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/messages_cn.js"/>"></script>
<script>
	$(function() {
		var dialog=$("#dialog");
		var dialog_content=$("#dialog_content");
		var doing=dialog_content.html();
		
		var close_able = false;	
	
		dialog.dialog({
			autoOpen: false,
			width:300,
			modal: true,
			beforeClose: function(event, ui) { return close_able; }
		});
		
		$("ul.tabs").tabs("div.panes > div");
			
		$.metadata.setType("attr", "validate");
		
		$(":text:first").focus();
		$("form:first").validate({
			submitHandler : function(form) {
				var options = {
				
					beforeSubmit : function(arr,form,options) {
						if (confirm("提交购卡订单，确定吗？")) {
							close_able=false;
							dialog_content.html(doing);
							dialog.dialog("option","title","处理中");
							dialog.dialog("open");
							return true;
						} else {
							return false;
						}
					},
				
					success : function(data) {
						close_able = true;
						dialog.dialog("option","title","处理完成");
						if (data) {
							dialog_content.html("购卡失败<br>" + data);
						} else {
							dialog_content.html("购卡成功");
						}
					}
				};
				$(form).ajaxForm();
				$(form).ajaxSubmit(
					options
				);
			}
		});
	});
</script>
</head>
<body>
<#include "/template/include/header.ftl">
<@s.form method="get" name="inputform" namespace="/amp" action="buycard" theme="simple">
<div class="right_box">
<!-- the tabs -->
<ul class="tabs">
	<li><a href="#">充值卡</a></li><#--
	<li><a href="#">大额充值卡</a></li>
	<li><a href="#">包月卡[直拨]</a></li>
	<li><a href="#">包月卡[回拨]</a></li>-->
	<li><a href="#">包月卡</a></li>
</ul>

<!-- tab "panes" -->
<div class="panes">
<#list productsList as products>
<div style="padding-left : 40px; min-height : 390px">
	<#list products as product> 
		<#if products_index==0 && product_index==0>
		<input type="radio" id="radio_${product.id}" name="product.id" value="${product.id}" validate="required:true"/>
		<#else>
		<input type="radio" id="radio_${product.id}" name="product.id" value="${product.id}"/>
		</#if>
		<label for="radio_${product.id}">${product.name}<#--面额 ${(product.price / 100 )?string.currency}--></label></br>
	</#list>
	<label for="product.id" class="error" style="display:none">请选择您要购买的充值卡</label> 
</div>
</#list>
</div>
<div style="padding:20px 0 20px 0px;">
购买数量 <input type="text" class="required ipb_m digits" name="count" />
输入密码 <input type="password" class="required ipb_m" name="password" />
<button type="submit"  class="btn but_s">购 买</button>
</div></div>
</@s.form>
<!-- jquery对话框 -->
<div id="dialog" title="处理中">
<p id="dialog_content"><img src="<@s.url value='/images/loading.gif'/>" width="16" height="16" border="0" align="absmiddle" /> 正在处理,请稍候...</p>
</div>

<#include "/template/include/footer.ftl">
</body>
</html>
