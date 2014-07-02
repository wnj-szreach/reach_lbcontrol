<html>
<head>
<script type="text/javascript">
window.onload=function(){
	var qstr = window.location.search.substring(10);//?fn=20001&
	var playType = ${playType};//0直播，1点播
	if(playType == 0){
		qstr = '${msHost}/live/Live.action?' + qstr + '&sign=${sign}';	
	}else{
		qstr = '${msHost}/backstage/Vod.action?' + qstr + '&sign=${sign}';
	}
	document.getElementById('play').src=qstr;
}
</script>
</head>
<body style="margin:0px; padding:0px;">
<iframe id="play" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" style="width:100%; height:100%; margin:0px; padding:0px;" src=""></iframe>
</body>
</html>