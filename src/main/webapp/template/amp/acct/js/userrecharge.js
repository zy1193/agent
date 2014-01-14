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
					dialog_content.html("充值失败！<br>" + data);
				} else {
					dialog_content.html("充值成功！");
				}
			}
		};
		$(form).ajaxForm();
		$(form).ajaxSubmit(
			options
		);
	  }
 	});
 	
 	$("span[name='at']").click(function(){
 		$(this).children(":radio").attr("checked","checked");
 	});
});
function isEmail(value){
 	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
 	return filter.test(value);
}
	
function isMobel(value){
	if(/^182\d{8}$/g.test(value)||(/^147\d{8}$/g.test(value))||/^13\d{9}$/g.test(value)||(/^15[0-35-9]\d{8}$/g.test(value))|| (/^18[01-9]\d{8}$/g.test(value))){
	   return true; 
	}else{
	    return false; 
	}
}
  	
function isUU(value){
	 var filter  = /^[0-9]{1,10}$/;
	 return filter.test(value);
}
