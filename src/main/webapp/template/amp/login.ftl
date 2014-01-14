<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache"/> 
<meta http-equiv="Cache-Control" content="no-store, must-revalidate"/> 
<meta http-equiv="expires" content="0"/>
<link href="/agent/css/default.css" rel="stylesheet" type="text/css" media="all" />
<style>
	ul li {
		margin-left:0px;
		height:50px;line-height:50px;
	}
	ul li.nl {
		width:60px;text-align:right;padding-right:10px;
	}
	ul li img {
		margin:11px 0 0 5px;* margin:3px 0 0 5px;
	}
body{background:#054c8c;}
</style>
<title>代理商平台-登录</title>
<script type="text/javascript" src="/agent/js/jquery.js"></script>
<script type="text/javascript" src="/agent/js/jquery.form.js"></script>
<script>
$(document).ready(function() {
	var f = $("form:first");
	var options = {
		beforeSubmit : function(arr, form, options) {
			var validate = true;
			var alertInfo = new Array("登录帐号", "登录密码", "验证码");
			$(":input").each(function(i) {
				if (!validate || i > 2)
					return;
				if (!$(this).attr("value")) {
					validate = false;
					alert("请输入" + alertInfo[i]);
					$(this).focus();
				}
			});
			return validate;
		},
		success : function(txt) {
			if (txt) {
				alert(txt);
			} else {
				window.parent.location.href="/agent/amp/index.action";
			}
		}
	};
	f.ajaxForm(options);
	$(":input:first").focus();
	
	$(":button:last").click(function() {
		window.parent.location.href="register.action";
	});
});
</script>
</head>
<body>
<div  id="login_box"><div class="box">
<!--<h1 style="text-align:center;float:none">代理商登录</h1>-->
<form action="/agent/amp/login.action" method="post">
<ul>
	<li class="nl">帐号</li>
	<li class="sl"><input  class="login_name" type="text" name="agent.id"/></li>
	<li class="nl">密码<li>
	<li class="sl"><input  class="login_prass" type="password" name="agent.password"/></li>
	<li class="nl">验证码<li>
	<li class="sl"><input class="login_yan" type="text" name="securityCode" /></li>
	<li class="sl"><img src="/agent/sc.cgi" /></li>
	<li class="ll">
		<button type="submit" class="login_in"></button>
		<button type="button" class="login_injon"></button>
	</li>
</ul>
</form>
</div></div>
</body>
</html>