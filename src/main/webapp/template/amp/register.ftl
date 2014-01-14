<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<style>
 body{background:#f3f3f3;}
 div{margin:0 auto;}
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
	li.sl {width:80%}
</style>
<title>代理商平台-注册</title>
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
					beforeSubmit : function(arr,form,options) {
						var reg = /^[a-zA-Z0-9]{3,15}$/;
						var t = $(":text:first");
						if (reg.test(t.attr("value"))) {
							return true;
						} else {
							alert("登录名为3至15个英文字母和阿拉伯数字，不能使用中文。请重新输入");
							t.focus();
							return false;
						}
					},
					success : function(data) {
						if (data) {
							alert("注册失败\r\n" + data);
						} else {
							alert("注册已成功，点击进入登录页");
							window.parent.location.href = "login.action";
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
<div id="logo2"></div>
<div class="register_box" >

<@s.form method="post" name="inputform" namespace="/amp" action="register" theme="simple">
<ul class="fix">
<li class="nl label">登录名 <span>*</span></li>
<li class="sl"><input type="text" name="agent.id" class="required ipb_m" maxLength="20" minLength="3"/></li>
<li class="nl label">真实名字 <span>*</span></li>
<li class="sl"><input type="text" name="agent.name" class="required ipb_m"/></li>

<li class="nl label">登录密码 <span>*</span></li>
<li class="sl"><input type="password" name="agent.password" id="pwd1" class="required ipb_m" minLength="6" /></li>

<li class="nl label">确认密码 <span>*</span></li>
<li class="sl"><input type="password" class="required ipb_m" equalTo="#pwd1" /></li>

<li class="nl label">上级代理商</li>
<li class="sl"><input class=" ipb_m" type="text" name="agent.superId" /></li>

<li class="nl label">押金金额 <span>*</span></li>
<li class="sl ">
	<@s.radio list={'30000':'  30,000元(顶级代理)','10000':'  10,000元(高级代理)','5000':'  5,000元(中级代理0)','0':'  0元(初级代理)'} listKey="key" listValue="value" name="acct.deposit" />
</li>

<li class="nl label">电话号码</li>
<li class="sl"><input class=" ipb_m" type="text" name="agent.phoneNo" /></li>

<li class="nl label">手机号码 <span>*</span></li>
<li class="sl"><input class="required ipb_m" type="text" name="agent.mobileNo" /></li>

<li class="nl label">身份证号 <span>*</span></li>
<li class="sl"><input class="required ipb_m" type="text" name="agent.idCardNo" class="required" minLength="15" maxLength="18" /></li>

<li class="nl label">QQ号码</li>
<li class="sl"><input class="ipb_m" type="text" name="agent.qq" class="digits" /></li>

<li class="nl label">拍拍店地址</li>
<li class="sl"><input class=" ipb_m" type="text" name="agent.paipaiShopUrl" class="l url" /></li>

<li class="nl label">旺旺号码</li>
<li class="sl"><input class=" ipb_m" type="text" name="agent.wangwang" /></li>

<li class="nl label">淘宝店地址</li>
<li class="sl"><input class=" ipb_m" type="text" name="agent.taobaoShopUrl" class="l url" /></li>
<li class="nl fix" style="margin-top:5px;"><button type="submit" class="but_l mima">提交</button></li>
</ul>
</@s.form>
<div class="tishi">
 <div><h2 >输入提示:</h2></div>
<ul>
<li>登录名为英文字母或数字，不能使用中文</li>
<li>标注 <span>*</span> 的必须输入</li>
<li>电话号码和手机号码至少要输入一个</li>
<li>QQ号码和旺旺号码至少要输入一个</li>
<li>淘宝店地址和拍拍店地址至少要输入一个</li>
</ul>
</div>
</div>
<#include "/template/include/footer.ftl">
</body>
</html>
