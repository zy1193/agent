<#-- 分页导航条 
	传入参数
		formIndex 表单的序号(页面上必须有一个表单其action为当前页面,且该表单不能有名字为page的输入元素)
		pageSize 每页记录数
	数据模型里须存在totalRecordCount和page两个int型的数据，分别表示总记录数和当前页码
-->		
<#macro pagenav formIndex=0 pageSize=15>

	<#-- 总记录数，当前页码，下一页，上一页，总页数 -->
	<#local 
		count = totalRecordCount ! 0
		pn = page ! 1
		nextpn = pn + 1
		privpn = pn - 1
		pageCount = (count / pageSize) ? ceiling>
	
	<#if pn gt pageCount>
		<#local pn = pageCount>
	</#if>
	<#if pn lt 1>
		<#local pn = 1>
	</#if>
		
	<#if nextpn gt pageCount>
		<#local nextpn = pageCount>
	</#if>
	
	<#if privpn lt 1>
		<#local privpn = 1>
	</#if>

	共有记录  <font color="red">${count}</font> 条
	
	<#if count gt 0>
		每页显示 <font color="red">${pageSize }</font> 条记录
		当前第
		<#if pageCount == 1>
			<font color="red">1</font>
		<#elseif pageCount gt 100>
			<input type="text" value="${pn}" style="width:48px;color:red;text-align:center" title="回车即可换页" onkeypress="javascript:movePage(this)">
		<#else>
			<select onchange="javascript:switchPage(this)">
				<#list 1..pageCount as i>
					<#if pn == i>
						<option value="${i}" style="color:red" selected>${i}</option>
					<#else>	
						<option value="${i}">${i}</option>
					</#if>
				</#list>	
			</select>
		</#if>		


		页
		共 <font color="red">${pageCount }</font> 页
	<#if pn != 1>
		<a href="javascript:gotoPage(1)">首页</a>
	<#else>
		<font color="#808080">首页</font>
	</#if>		
	  	|
	<#if pn gt privpn>
	  	<a href="javascript:gotoPage(${privpn})">上页</a>
	<#else>
		<font color="#808080">上页</font>
	</#if>  	
	  	|
	<#if pn lt nextpn>	  	
	  	<a href="javascript:gotoPage(${nextpn})">下页</a>
	<#else>
		<font color="#808080">下页</font>
	</#if>  	
	  	|
	<#if pn lt pageCount>  	
	  	<a href="javascript:gotoPage(${pageCount})">末页</a>
	<#else>  	
	  	<font color="#808080">末页</font>
	</#if>
	<#if pageCount gt 1>
	  	<script language="javascript">
	  		function gotoPage(n) {
	  			var e = document.createElement("input");
	  			e.name = "page";
	  			e.type = "hidden";
	  			e.value = n;
	  			
	  			var f = document.forms[${formIndex}];
	  			f.appendChild(e);
	  			f.submit();
	  		}
	  		
	  		function switchPage(s) {
	  			var length = s.options.length;
	  			var o;
	  			for (var i = 0;i<length;i++) {
	  				o = s.options[i];
	  				if (o.selected) {
	  					break;
	  				}
	  			}
	  			gotoPage(o.value);
	  		}
	  		
	  		function movePage(t) {
	            if (window.event.keyCode == 13) {
	              	var i = parseInt(t.value);
	              	if (i == NaN) i = 1;
	            	if (i > ${pageCount}) i = ${pageCount};
	            	gotoPage(i);
	            }
	            return false;
	  		}
	  		
	  	</script>
	</#if>  	
	</#if>  	
</#macro>