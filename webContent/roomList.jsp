<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="bbNG" uri="/bbNG"%>
<%@ taglib prefix="bbData" uri="/bbData"%>

<%@ page import="com.szreach.blackboard.lbcontrol.RoomItem, 
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
			<bbNG:pageTitleBar><%=adminBundle.getString("text.lbcontrol") %></bbNG:pageTitleBar>
		</bbNG:pageHeader>
			<bbNG:actionControlBar>
				<!-- bb按钮标签 -->
				<bbNG:actionButton url="lbcontrol.action?fn=10001" title='<%=adminBundle.getString("text.lbcontrol") %>' primary="true"/>
				<bbNG:actionButton url="lbcontrol.action?fn=20004" title='<%=adminBundle.getString("text.viewFile") %>' primary="true"/>
				<bbNG:actionButton url="lbcontrol.action?fn=20003" title='<%=adminBundle.getString("text.mediacenter") %>' target="_blank" primary="true"/>
			</bbNG:actionControlBar>
				<!-- list start -->
				<bbNG:inventoryList collection="${list}" objectVar="room" className="RoomItem" description="this classrooms come from reach platform">
					<bbNG:listElement  label='<%=adminBundle.getString("list.classRoomName") %>' name="classRoomName" isRowHeader="true">${room.classRoomName}</bbNG:listElement>
					<bbNG:listElement  label='<%=adminBundle.getString("list.conFlag") %>' name="conFlag">
						<%if(room.getConFlag() == 1) {%>
							<%=adminBundle.getString("text.connected") %>
						<%}else{%>
							<%=adminBundle.getString("text.disconnected") %>
						<%}%>
					</bbNG:listElement>
					<bbNG:listElement  label='<%=adminBundle.getString("list.recordFlag") %>' name="recordFlag">
						<%if(room.getRecordFlag() == 1) {%>
							<%=adminBundle.getString("text.recording") %>
						<%}else{%>
							<%=adminBundle.getString("text.notrecord") %>
						<%}%>					
					</bbNG:listElement>
					<bbNG:listElement  label='<%=adminBundle.getString("list.preview") %>' name="preview">		
						<%if(room.getConFlag() == 1) {%>
							<span class="mcButton" onclick="openUrl('<%=room.getLiveUrls().get(0) %>')">
								<img src="images/play.png" />
								<span><%=adminBundle.getString("text.preview") %></span><!-- 预览 -->
							</span>
						<%}else{%>
							---
						<%}%>		
					</bbNG:listElement>				
					<bbNG:listElement  label='<%=adminBundle.getString("list.operation") %>' name="operate" >
						<%if(room.getConFlag() == 1) {%>
							<%if(room.getRecordFlag() == 0) {%>
								<span class="mcButton" onclick="roomControl('${room.id}', '10201')">
									<img src="images/record.png" />
									<span><%=adminBundle.getString("text.record") %></span><!-- 录制 -->
								</span>
							<%}%>
							<%if(room.getRecordFlag() == 1){%>
								<span class="mcButton" onclick="roomControl('${room.id}', '10202')">
									<img src="images/pause.png" />
									<span><%=adminBundle.getString("text.pause") %></span><!-- 暂停	 -->
								</span>
							<%}%>
							<%if(room.getRecordFlag() == 2){%>
								<span class="mcButton" onclick="roomControl('${room.id}', '10203')">
									<img src="images/continue.png" />
									<span><%=adminBundle.getString("text.continue") %></span><!-- 继续 -->
								</span>
							<%}%>
							<%if(room.getRecordFlag() != 0){%>
								<span class="mcButton" onclick="roomControl('${room.id}', '10204')">
									<img src="images/stop.png" />
									<span><%=adminBundle.getString("text.stop") %></span><!-- 停止	 -->
								</span>
							<%}%>
						<%}else {%>
							---		
						<%}%>
					</bbNG:listElement>
					
					<bbNG:listOptions allowEditPaging="false" allowShowAll="true" allowRefresh="true" refreshUrl="javascript:freshList()"/>
				</bbNG:inventoryList>
				<!-- list end -->
	</bbNG:genericPage>
</bbData:context>

