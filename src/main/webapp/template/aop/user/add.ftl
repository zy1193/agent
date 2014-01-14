<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
	li.label {width:120px}
	li.sl {width:80%}
</style>
<title>代理商后台-添加用户</title>
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
						$("#res").html(data).show();
						//$(":text").attr("value","");
						//$(":text:first").focus();
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
<h2 style="margin:20px 0 0 30px;">添加新用户</h2>
<form action="<@s.url value='/aop/adduser.action' />" name="inputform" method="post">
<ul class="fix">
<li class="nl label">ID </li>
<li class="sl"><input type="text" name="user.id" class="required ipb_m"/></li>
<li class="nl label">姓名 </li>
<li class="sl"><input type="text" name="user.name" class="required ipb_m"/></li>
<li class="nl label">角色 </li>
<li class="sl">
	<input type="radio" name="user.role" value="3" id="radio_1"/> <label for="radio_1">管理员</label>
	<input type="radio" name="user.role" value="2" id="radio_2"/> <label for="radio_2">监察员</label>
	<input type="radio" name="user.role" value="1" id="radio_3" checked="checked"/> <label for="radio_3">拓展员</label>
</li>
<li class="nl fix"><button type="submit" class="but_l mima">提交</button></li>
</ul>
</form>
<div style="margin:20px 0 0 30px;color:blue;display:none;" id="res" ></div>

<div style="margin:20px 0 0 30px;color:blue;">
角色说明<br/>
管理员：拥有所有权限<br/>
监察员：不可授信<br/>
拓展员：不可授信、审核、冻结、解冻
</div>

<#include "/template/include/footer.ftl">
</body>
</html>