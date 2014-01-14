<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
	li.label {width:120px}
	li.sl {width:80%}
</style>
<title>代理商后台-修改用户信息</title>
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
							$("#res").html("用户信息修改失败：" + data).show();
						} else {
							$("#res").html("用户信息已成功修改。<a href='user.action'>返回</a>").show();
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
<h2 style="margin:20px 0 0 30px;">修改用户信息</h2>
<form action="<@s.url value='/aop/modifyuser.action' />" name="inputform" method="post">
<ul class="fix">
<li class="nl label">ID </li>
<li class="sl">${user.id}</li>
<li class="nl label">姓名 </li>
<li class="sl"><input type="text" name="user.name" class="required ipb_m" value="${user.name}"/></li>
<li class="nl label">角色 </li>
<li class="sl">
	<input type="radio" name="user.role" <#if user.role=="3">checked="checked"</#if> value="3" id="radio_1"/> <label for="radio_1">管理员</label>
	<input type="radio" name="user.role" <#if user.role=="2">checked="checked"</#if> value="2" id="radio_2"/> <label for="radio_2">监察员</label>
	<input type="radio" name="user.role" <#if user.role=="1">checked="checked"</#if> value="1" id="radio_3"/> <label for="radio_3">拓展员</label>
</li>
<li class="nl fix"><button type="submit" class="but_l mima">提交</button></li>
</ul>
<input type="hidden" name="user.id" value="${user.id}"/>
</form>
<div style="margin:20px 0 0 30px;display:none;" id="res" ></div>

<div style="margin:20px 0 0 30px;color:blue;">
角色说明<br/>
管理员：拥有所有权限<br/>
监察员：不可授信<br/>
拓展员：不可授信、审核、冻结、解冻
</div>

<#include "/template/include/footer.ftl">
</body>
</html>