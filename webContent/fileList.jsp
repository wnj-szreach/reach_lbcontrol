<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="bbNG" uri="/bbNG"%>
<%@ taglib prefix="bbData" uri="/bbData"%>

<%@ page import="com.szreach.blackboard.lbcontrol.FileItem, 
			java.util.*,
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
		<!-- 控制面板标签 -->
		<bbNG:breadcrumbBar environment="sys_admin" navItem="admin_main">
		<bbNG:breadcrumb><%=adminBundle.getString("meunu.breadcrumb") %></bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<!-- 页面头部标签 -->
		<bbNG:pageHeader>
			<bbNG:pageTitleBar><%=adminBundle.getString("text.viewFile") %></bbNG:pageTitleBar>
		</bbNG:pageHeader>

		<bbNG:actionControlBar>
			<!-- bb按钮标签 -->
			<bbNG:actionButton url="lbcontrol.action?fn=10001" title='<%=adminBundle.getString("text.lbcontrol") %>' primary="true"/>
			<bbNG:actionButton url="lbcontrol.action?fn=20004" title='<%=adminBundle.getString("text.viewFile") %>' primary="true"/>
			<bbNG:actionButton url="lbcontrol.action?fn=20003" title='<%=adminBundle.getString("text.mediacenter") %>' target="_blank" primary="true"/>
			
			<bbNG:actionPanelButton type="SEARCH" alwaysOpen="true">
				<form name="fileManagerForm" action="lbcontrol.action" method="get">
				<input type="hidden" name="fn" value="20004" />
				<input type="hidden" name="startIndex" value="startIndex" />
				<input type="hidden" name="numResults" value="numResults" />
				<input type="hidden" name="showAll" value="showAll" />
				<br>
				<!-- search start -->
				<table width="100%" class="bMedium" cellspacing="0" cellpadding="3" border="0" summary="">
					<tr>
						<td valign="left">
							<table class="bAccentLight" width="100%" border="0" cellpadding="2" cellspacing="0" summary="layout">
								<tr>
									<td nowrap><span class="label"><%=adminBundle.getString("text.keyword") %></span></td>
									<td nowrap><INPUT TYPE="TEXT" NAME="searchText" VALUE="${searchText }" size="20" maxlength="64"></td>
					  				<td nowrap="nowrap" width="100%"><input type="submit" value='<%=adminBundle.getString("text.search") %>' class="genericButton" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<br>
				<!-- search end -->
				</form>
			</bbNG:actionPanelButton>	
					
		</bbNG:actionControlBar>
			<!-- list start -->
			<bbNG:inventoryList collection="${list}" objectVar="item" className="FileItem" description="this is record file">
				<bbNG:listElement  label='<%=adminBundle.getString("list.previewUrl") %>' name="previewUrl" isRowHeader="true"><img border="0" class="previewPic" src="${item.previewUrl}" /></bbNG:listElement>
				<bbNG:listElement  label='<%=adminBundle.getString("list.fileCname") %>' name="fileCname">${item.fileCname}</bbNG:listElement>							
				<bbNG:listElement  label='<%=adminBundle.getString("list.recordTime") %>' name="recordTime">${item.recordTime}</bbNG:listElement>							
				<bbNG:listElement  label='<%=adminBundle.getString("list.duration") %>' name="duration">${item.duration}</bbNG:listElement>							
				<bbNG:listElement  label='<%=adminBundle.getString("list.author") %>' name="author">${item.author}</bbNG:listElement>
				<bbNG:listElement  label='<%=adminBundle.getString("list.vodCount") %>' name="vodCount">${item.vodCount}</bbNG:listElement>							
				<bbNG:listElement  label='<%=adminBundle.getString("list.publishStatus") %>' name="publishStatus">
				<% if(item.getPublishBlackBoardStatus() == 1){ %>
					<%=adminBundle.getString("text.published") %>
				<%}else{ %>
					<%=adminBundle.getString("text.notpublish") %>
				<%} %>
				</bbNG:listElement>		
				
				<bbNG:listElement  label='<%=adminBundle.getString("list.operation") %>' name="operate" >
	    			<a class="inlineAction" href="#" onclick="openUrl('${item.vodUrl}')"><%=adminBundle.getString("text.play") %></a>
	    			&nbsp;&nbsp;|&nbsp;&nbsp;
	    			<a class="inlineAction" href="#" onclick="openUrl('lbcontrol.action?fn=20005&a=${item.id}')"><%=adminBundle.getString("text.total") %></a>
	    			&nbsp;&nbsp;|&nbsp;&nbsp;
	    			<a class="inlineAction" href="#" onclick="alert('还没搞');"><%=adminBundle.getString("text.publish") %></a> 
				</bbNG:listElement>									
				
				<bbNG:listOptions allowEditPaging="false" allowShowAll="true" allowRefresh="true" refreshUrl="javascript:freshList()"/>
			</bbNG:inventoryList>
			<!-- list end -->
	</bbNG:genericPage>
</bbData:context>

