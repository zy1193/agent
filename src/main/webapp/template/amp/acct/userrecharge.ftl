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
  <title>用户代充 </title>
  <script type="text/javascript" src="<@s.url value="/js/jquery.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.ui.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.metadata.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.tools.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.form.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/jquery.validate.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/js/messages_cn.js"/>"></script>
  <script type="text/javascript" src="<@s.url value="/template/amp/acct/js/userrecharge.js"/>"></script>
  
  <script type="text/javascript">
  	//自定义验证  判断是否手机、UID、邮件格式合格
	jQuery.validator.addMethod("mobile_email_uid",  
	    function(value, element) { 
	    	var type=$("input:radio:checked").val();
	    	if(type=='mobile'){
	    		return this.optional(element)||isMobel(value);
	    	}else if (type=='email'){
	    		return this.optional(element)||isEmail(value);
	    	}else if(type=='uid'){
	    		return this.optional(element)||isUU(value);
	    	}else{
	    		return false;
	    	}
			
	    },
	    "账号的格式不正确");
	    
	    //自定义验证
	jQuery.validator.addMethod("isZZS_C",  
	    function(value, element) { 
	    	var value=$("#sum").val();
    		return this.optional(element)||isZZS(value);
	    },
	    "金额格式不正确");
	    
	    function isZZS(value){
	 		var filter  = /^[0-9]*[1-9][0-9]*$/;
	 		return filter.test(value);
		}
  </script>
 </head>
 <body>
  <form name="userrechargeFrom" namespace="/amp" action="userrecharge.action" >
  <div class="right_box">
  	<div id="div">
  		<ul class="fix" style="margin-top:0px;">
  			<li class="nl label">
  				账户：<input type='text' name='account' id='account' maxlength='100' class="required mobile_email_uid ipb_m" />
  				<span name="at" style="cursor:pointer"><input type="radio" name="accountType" value="mobile" checked/> 手机号码 &nbsp;</span>
  				<span name="at"style="cursor:pointer"><input type="radio" name="accountType" value="email"/> 邮箱地址&nbsp;</span>
  				<span name="at" style="cursor:pointer"><input type="radio" name="accountType" value="uid"/> UID账号</span>
  			</li>	
  			
  			<li class="nl label">
  				金额：<input type='text' name='sum' id='sum' class="required  isZZS_C  ipb_m" />
  			</li>
  			<li class="nl label">
  				密码：<input type='password' name='password' id='password' class="required ipb_m" />
  			</li>
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
