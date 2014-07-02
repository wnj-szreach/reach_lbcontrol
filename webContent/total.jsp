<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*,
			blackboard.platform.BbServiceManager"
%>

<%
Locale locale = BbServiceManager.getLocaleManager().getLocale().getLocaleObject(); 
ResourceBundle adminBundle = ResourceBundle.getBundle( "admin", locale );
%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="images/vertical-bar.css" rel="stylesheet" type="text/css" />
	<script src="images/jquery.min.js" type="text/javascript"></script>
	<script src="images/jquery.verticalchart.js" type="text/javascript"></script>
	<style type="text/css">
		.tab {float:left; padding:2px 4px; border:solid 1px #ccc; background-color:#eee; cursor:pointer;}
		.current {background-color:#ccc;}
		.tabBlock {width:100%; border:solid 1px #ccc; overflow:hidden;}
		table {width:100%; border-width: 1px; border-color: #666666; border-collapse: collapse; color:#333333;}
		table th {border-width: 1px; padding: 8px; border-style: solid; border-color: #666666; background-color: #dedede}
		table td {border-width: 1px; padding: 8px; border-style: solid; border-color: #666666; background-color: #ffffff;}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
		    var total_max = Math.ceil(Math.max(${c0}, ${c1}, ${c2}) * 1.2);
		    var total_len = total_max.toString().length;
		    var Hi = Math.pow(10, total_len);
		    
		    $('#verticalbar-chart-wrapper').verticalchart({
		      XData : ['${m2}','${m1}','${m0}'],
		      YData : [0, Hi/10, Hi/10*2, Hi/10*3, Hi/10*4, Hi/10*5, Hi/10*6, Hi/10*7, Hi/10*8, Hi/10*9, Hi],
		      barA :  [${c2}, ${c1}, ${c0}]
		     });
			
			
			$(".tab").click(function(){
				$(".tab").removeClass("current");
				$(this).addClass("current");
				var index = $(this).index();
				
				$(".tabBlock").hide();
				$(".tabBlock").eq(index).show();
			});
		})
	</script>
</head>
<body>
	<c:if test="${err == null || err == '' }">
		<c:if test="${list != null }">
		
			<div style="width:100%; display:inline-block;">
				<div class="tab current"><%=adminBundle.getString("text.totalpic") %></div>
				<div class="tab"><%=adminBundle.getString("text.totallist") %></div>
			</div>
			<div style="width:780px; margin-top:-5px;">
			
				<!-- 统计图 -->
				<div class="tabBlock">
					<div id="verticalbar-chart-wrapper" style="margin:0px; width:780px;"></div>
				</div>
							
				<!-- 点播详细 -->
				<div class="tabBlock" style="display:none;">
					<table>
						<tr>
						<th><%=adminBundle.getString("text.username") %></th>
						<th><%=adminBundle.getString("text.ip") %></th>
						<th><%=adminBundle.getString("text.time") %></th>
						</tr>
						
							<c:forEach var="item" items="${list}">
							<tr>
								<td>${item.username }</td>
								<td>${item.ip }</td>
								<td>${item.logTime }</td>
							</tr>
							</c:forEach>
						
					</table>
				</div>
				
			</div>
			
			
		</c:if>
		<c:if test="${list == null || fn:length(list) == 0}">
			no data!
		</c:if>
	</c:if>
	<c:if test="${err != null && err != '' }">
		${err}
	</c:if>
</body>
</html>

