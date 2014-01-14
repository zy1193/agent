<#import "/template/include/navbar.ftl" as nav>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
	li.label {width:100px;}li.sl {width:80%}
</style>
<title>代理商平台-授信</title>
<script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.form.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/jquery.validate.js"/>"></script>
<script type="text/javascript" src="<@s.url value="/js/messages_cn.js"/>"></script>
<script>
	$(function() {
		$(":text:first").focus();
		$("form:first").validate({
			submitHandler : function(form) {
				var options = {
					success : function(data) {
						if (data) {
							alert("授信失败：" + data);
							$(":text:first").focus();
						} else {
							alert("已成功授信");
							$(":password:first").attr("value","");
							$(":text:first").blur();
							
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
	
	var url = "<@s.url value='/aop/agentinfo.action'/>";
	function getAgent() {
		var ping = $("#ping");
		var ai = $("#ai");
		var id = document.forms[0].agentId.value;
		if (id) {
			ai.hide();
			ping.show();
			$.get(
				url,
				{"agentId":id},
				function(txt) {
					ping.hide();
					ai.show();
					ai.html(txt);
				}
			);
		}
	}
	
</script>
</head>
<body>
<#include "/template/include/header.ftl">
<form action="<@s.url value='/aop/provide.action'/>" method="post">

<ul class="fix">
<li class="nl label">代理商ID</li>
<li class="sl"><input type="text" name="agentId" class="required ipb_m" onblur="return getAgent();"/></li>
<li class="nl label">代理商信息</li>
<li class="sl" style="_min-height:150px;height:150px;"><div id="ai"></div>
<span id="ping" style="display:none"><img src="<@s.url value='/images/loading.gif'/>" width="16" height="16" align="absmiddle" border="0" /> 正查询代理商信息,请稍候...</span>

</li>
<li class="nl label">授信金额(元)</li>
<li class="sl"><input type="text" name="amount" class="required number ipb_m"/></li>
<li class="nl label">备注</li>
<li class="sl"><input type="text" name="rmk" class="ipb_m" style="width:200px" maxLength="25"/> (最多25个字)</li>
<li class="nl label">密码</li>
<li class="sl"><input type="password" name="password" class="required ipb_m"/></li>
<li class="nl fix"><button class="but_l mima" type="submit">提交</button>
</ul>
</form>
<#include "/template/include/footer.ftl">
</body>
</html>