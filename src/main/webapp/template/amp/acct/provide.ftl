<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
	<link href="<@s.url value='/css/tabs.css'/>" rel="stylesheet" type="text/css" media="all" />
	<link href="<@s.url value="/css/ui-lightness/jquery.ui.css"/>" rel="stylesheet" type="text/css" media="all" />
	<style>
		#div {
			width: auto;
			height: 400px;
			
			padding: 20px 0 0 0px;
		}
		#div li{line-height:30px;}
		li.sl {width:80%}
	</style>
  <title>授信 </title>
  <script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.ui.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.metadata.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.tools.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.form.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.validate.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/messages_cn.js"/>"></script>
 </head>
 <script type="text/javascript">
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
			 
		 	 $("form:first").validate({
		 		submitHandler : function(form) {
				var options = {
				
					beforeSubmit : function(arr,form,options) {
						if (confirm("确定提交吗？")) {
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
							dialog_content.html("授信失败！<br>" + data);
						} else {
							dialog_content.html("授信成功！");
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
 <body>
  <form name="queryform" namespace="/amp" action="accredited.action" >
  <div class="right_box">
  	<div id="div">
  		<ul class="fix">
  			<li class="nl label">可用余额：</li>
  			<#if acct??>
  			<li class="sl">${(acct.availableBalance / 1000)?string.currency }</li>
  			</#if>
  			<li class="nl label">账户状态：</li>
  			<#if acct??>
			<li class="sl">${acct.statusDesc}</li>
			</#if>
  		</ul>
  		<ul class="fix" style="margin-top:0px;">
  			<li class="nl label">下级：</li>
  			<li class="sl">
  				<select name="agents" id="agents" width="200px" class="required ipb_l" >
  				    <option value=''>--请选择--</option>
				  <#if subAgents??>
   				  <#list subAgents as agent>
   				    <option value='${agent.id!""}'>${agent.name!""}</option>
   				  </#list>
   				  </#if>
  				</select>金额：<input type='text' name='p_money' id='p_money' maxlength='6' class="required number ipb_m" />
  			密码：<input type='password' name='password' id='password' class="required ipb_m" />
  			</li>
  			<#--<li class="sl">金额：</li>
  			<li class="sl"><input type='text' name='p_money' id='p_money' maxlength='6' class="required number ipb_m" /></li>
  			<li class="sl">密码：</li>
  			<li class="sl"><input type='password' name='password' id='password' class="required ipb_m" /></li>-->
  			<li class="nl label"><button type='submit' class="btn but_l shouxin ">提 交</button></li>
  		</ul>
  	</div>
  	</div>
  </form>
  <!-- jquery对话框 -->
  <div id="dialog" title="处理中">
  <p id="dialog_content"><img src="<@s.url value='/images/loading.gif'/>" width="16" height="16" border="0" align="absmiddle" /> 正在处理,请稍候...</p>
  </div>
  
 </body>
</html>
