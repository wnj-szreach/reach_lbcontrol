<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="bbNG" uri="/bbNG"%>
<%@ taglib prefix="bbData" uri="/bbData"%>

<%@ page import="java.util.*,
			blackboard.platform.BbServiceManager"
%>

<%
Locale locale = BbServiceManager.getLocaleManager().getLocale().getLocaleObject(); 
ResourceBundle adminBundle = ResourceBundle.getBundle( "admin", locale );
%>

<bbData:context id="ctx">
	<bbNG:genericPage>
		<bbNG:jsFile href="images/jquery.min.js" />
		<bbNG:jsFile href="images/major.js" />			
		<bbNG:cssFile href="images/style.css" />
		<bbNG:jsBlock>
			<script type="text/javascript">			
			window.onload(function(){
				var auth = '${setting.auth}';
				if(auth == "1") {
					$("input[name=auth]").attr("checked",true);
				}
			});
			</script>
		</bbNG:jsBlock>
		<!-- 控制面板标签 -->
		<bbNG:breadcrumbBar environment="sys_admin" navItem="admin_main">
		<bbNG:breadcrumb><%=adminBundle.getString("meunu.breadcrumb") %></bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<!-- 页面头部标签 -->
		<bbNG:pageHeader>
			<bbNG:pageTitleBar><%=adminBundle.getString("text.setting") %></bbNG:pageTitleBar>
		</bbNG:pageHeader>
			
	<!-- search start -->
	<table width="100%" class="bMedium" cellspacing="0" cellpadding="3" border="0" summary="">
	<tr>
		<td valign="left">
			<table class="bAccentLight" width="100%" border="0" cellpadding="2" cellspacing="0" summary="layout">
				<tr>
					<td nowrap style="text-align:right"><span class="label"><%=adminBundle.getString("field.mediacenter_addr") %>：</span></td>
					<td nowrap style="text-align:left"><INPUT TYPE="TEXT" NAME="address" VALUE="${setting.address }" size="20" maxlength="64"></td>
				</tr>
					
				<tr>
					<td nowrap style="text-align:right"><%=adminBundle.getString("field.check_auth") %>：</td>
					<td nowrap style="text-align:left"><input type="checkbox" name="auth" /></td>
				</tr>
				
				<tr>
					<td nowrap></td>
					<td nowrap><input onclick="saveSetting()" type="button" value='<%=adminBundle.getString("text.submit") %>' class="genericButton" /></td>
				</tr>
				
			</table>
		</td>
	</tr>
		
	</table>
	<br>
			
	</bbNG:genericPage>
</bbData:context>

