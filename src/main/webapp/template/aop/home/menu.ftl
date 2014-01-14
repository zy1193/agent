<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<@s.url value='/css/default.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<@s.url value='/css/menu.css'/>" rel="stylesheet" type="text/css" media="all" />
<title>代理商平台</title>
<script type="text/javascript" src="<@s.url value='/js/jquery.js'/>"></script>
<script type="text/javascript" src="<@s.url value='/js/jquery.tools.js'/>"></script>
<script type="text/javascript">
$(function() { 
	$("#accordion").tabs("#accordion div.pane", 
		{tabs: 'h2', effect: 'slide', initialIndex: null}
	);
	
	var h = $(window).height();
	$("div.pane").height(h - 215);
	
	$("a").click(function(){
		$(".here").removeClass("here");
		$(this).addClass("here");
	});
	
});
</script>
<base target="content" />

</head>
<body style="background:#DCEFFF;">
<!-- accordion --> 
<div id="accordion">
	<#list root.subMenus as m1>
		<#if m1.visible = "Y">
		<#if m1_index == 0>
		<h2 class="current">${m1.name}</h2>
		<div class="pane" style="display:block">
		<#else>
		<h2>${m1.name}</h2>
		<div class="pane">
		</#if>
		<ul>
		<#list m1.subMenus as m2>
			<#if m2.visible == "Y">
			<li><a href="${m2.url}">${m2.name}</a></li>
			</#if>
		</#list>
		</ul>
		</div>
		</#if>
	</#list>
</div> 
</body>
</html>
