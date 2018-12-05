$(function() {
	$("#signature").jSignature({
		height : 300,
		width : 800,
		color : "#000",
		"decor-color" : "transparent",
		lineWidth : 2
	});
	// $(".jSignature").css({ "width": "100%", "height": "100%" });
});

function reset() {
	var $sigdiv = $("#signature");
	$sigdiv.jSignature("reset");
}

function outputImage() {
	var $sigdiv = $("#signature");
	var datapair = $sigdiv.jSignature("getData", "svgbase64"); // 设置输出的格式，具体可以参考官方文档

	// var i = new Image();
	// i.src = "data:" + datapair[0] + "," + datapair[1]
	// $(i).appendTo($("#image")) // append the image (SVG) to DOM.

	$("#img").attr("src", "data:" + datapair);
	$("img").show();
	// 以上是预览
	// 以下是 ajax保存
	var $sigdiv = $("#signature");
	var datapair = $sigdiv.jSignature("getData", "image"); // 设置输出的格式，具体可以参考官方文档
	$.ajax({
		type : "POST",
		url : "/getBase64FromatImg",
		data : {
			"name" : "q",
			"base" : datapair[1]
		},
		success : function(msg) {
		}
	});

}

function downloadPng() {
	/*
	 * $.ajax({ type : "POST", url : "/downloadFile", data : { "name" : "付飞虎" },
	 * success : function(msg) { alert("OK!"); } });
	 */
	window.location.href = "/downloadPng?name=q";
}

function downloadRTF() {
	window.location.href = "/downloadRTF?name=q";
}