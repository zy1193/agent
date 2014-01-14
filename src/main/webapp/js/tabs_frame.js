$(function() {
	var h = $(window).height();
	var pane = $("#tabpane > iframe");
	var loading = $("#loading");
	pane.attr("height", h - 106);

	var timeid = 0;
	var hided = true;

	$("a").click(function() {
		hided = true;
		pane.attr("src", "");
		$(".here").removeClass("here");
		$(this).addClass("here");
		pane.attr("src", $(this).attr("href"));

		timeid = setInterval(function() {
			var doc = window.frames[0].document;
			if (doc.location.href == "about:blank") {
				if (hided) {
					loading.show();
					hided = false;
				}
			} else {
				loading.hide();
				clearInterval(timeid);
			}
		}, 50);
		
		return false;
	});

	$("#tabnav a:first").click();
})
