// 刷新列表
function freshList() {
	location.reload();
}

// 直播预览
function openUrl(url) {
	window.open(url,"vod","height=600,width=800,left=200,top=100,fullscreen=no,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no");
}

// 启动录制、暂停录制、继续录制、停止录制
function roomControl(rid, opt) {
    var url = "lbcontrol.action?fn=20001&" + "&rid=" + rid + "&opt=" + opt;
	$.ajax({
	  	type: 'GET',
	  	url: url,
	  	data: null,
	  	success: function(result){
			if(result == 'success') {
				freshList();
			}else{
				alert(result);
			}
		}
	});
};

// 保存配置
function saveSetting() {
	var address  = $("input[name=address]").val();
	var auth	 = $("input[name=auth]").attr("checked")==true? "1":"0";
	
	var url = "lbcontrol.action?fn=20008&" + "&address=" + address + "&auth=" + auth;
	$.ajax({
	  	type: 'GET',
	  	url: url,
	  	data: null,
	  	success: function(result){
	  		alert(result);
		}
	});
}

$(document).reach(function(){
	$("#listContainer_datatable tr th:last").css({"width" : "325px"});
});
