<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="bbNG" uri="/bbNG"%>
<%@ taglib prefix="bbData" uri="/bbData"%>

<bbData:context id="ctx">
	<bbNG:genericPage>
		<bbNG:jsBlock>
			<script type="text/javascript">			
			function validateRefresh() {
				alert(1);
			}
			</script>
			
		</bbNG:jsBlock>
		<!-- 控制面板标签 -->
		<bbNG:breadcrumbBar environment="sys_admin" navItem="admin_main">
		<bbNG:breadcrumb>课程</bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<!-- 页面头部标签 -->
		<bbNG:pageHeader>
			<bbNG:pageTitleBar>课程</bbNG:pageTitleBar>
		</bbNG:pageHeader>
			<bbNG:actionControlBar>
				<!-- bb按钮标签 -->
				<bbNG:actionButton url="../course/add.jsp" title="创建课程" primary="true"/>
				<bbNG:actionPanelButton type="SEARCH" alwaysOpen="true">
				<form name="courseManagerForm" action="../execute/courseManager" method="post">
				<input type="hidden" name="action" value="search" />
				<br>
				<!-- search start -->
				<table width="100%" class="bMedium" cellspacing="0" cellpadding="3" border="0" summary="">
				<tr>
					<td valign="left">
						<table class="bAccentLight" width="100%" border="0" cellpadding="2" cellspacing="0" summary="layout">
							<tr>
								<td nowrap><span class="label">搜索</span></td>
								<td nowrap colspan="3">
								<td nowrap>
									<select NAME="searchKey">
									</select>
								</td>
								<td nowrap>包含</td>
								<td nowrap><INPUT TYPE="TEXT" NAME="searchText" VALUE="" size="20" maxlength="64"></td>
				  				<td nowrap="nowrap" width="100%"><input type="submit" value="执行" class="genericButton" /></td>
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
				<%if (  list != null  &&  list.size() != 0  )
				{
				%>
				<!-- list start -->
				<bbNG:inventoryList collection="<%=list%>" objectVar="room" className="CourseBean">
				<bbNG:listElement  label="课程名称" name="courseName" isRowHeader="true">
				  <%=room.getClassRoomName()%>
				</bbNG:listElement>
				
				<bbNG:listElement  label="课程ID" name="courseUid">
				   <%=room.getClassRoomName()%>
				</bbNG:listElement>
				
				<bbNG:listElement  label="当前授课教师" name="teacher">
				   <%=room.getClassRoomName()%>
				</bbNG:listElement>
				
				<bbNG:listElement   label="操  作" name="operate" >
	    			<a class="inlineAction" href="../execute/courseManager?action=registered&coursePk=<%=room.getClassRoomName() %>&courseUid=<%=tmpcourse.getClassRoomName()%>">注册信息 | </a> 
	    			<a class="inlineAction" href="../execute/courseManager?action=modify_init&courseUid=<%=room.getClassRoomName()%>">编辑 |</a> 
	    			<a class="inlineAction" href="../execute/courseManager?action=delete&courseUid=<%=room.getClassRoomName()%>" onclick="return confirm('此操作不可恢复,是否继续?')">删除</a> 
				</bbNG:listElement>
				
				<bbNG:listOptions allowEditPaging="true" allowShowAll="true" allowRefresh="true" refreshUrl="javascript:validateRefresh()"/>
				</bbNG:inventoryList>
				
				<%
				}%>
				<!-- list end -->
	</bbNG:genericPage>
</bbData:context>

