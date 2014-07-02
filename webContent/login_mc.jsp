<html>
<head>
<script type="text/javascript" src="images/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    jQuery.ajax({
        url:"${mclogin_url}",
        dataType:"jsonp",
        jsonp:"callback",
        timeout:5000,
        success:function(data) {
            if(data != '') {
                $('body').html(data);
            }else {
                window.location.href = "${mcback_url}";
            }
        }
    });
});
</script>
</head>
<body>
</body>
</html>