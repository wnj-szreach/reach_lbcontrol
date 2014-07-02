package com.szreach.blackboard.lbcontrol.tool;

public class MsgCode {
	// XML-RPC第三方对外接口
	public final static String Msg_UserMgr 					= "10101";		// 用户管理-----101+是基础接口
	public final static String Msg_GetUserList 				= "10102";		// 用户列表
	public final static String Msg_CourseMgr 				= "10103";		// 课表管理
	public final static String Msg_GetCourseList 			= "10104";		// 课表列表
	public final static String Msg_GetCourseDetail 			= "10105";		// 课表详细
	public final static String Msg_CourseTimeSet 			= "10106";		// 节次设置
	public final static String Msg_Deletefiles 				= "10107";		// 删除课件
	public final static String Msg_GetFileList 				= "10108";		// 课件列表
	public final static String Msg_GetFileDetail 			= "10109";		// 课件详细
	public final static String Msg_GetRoomLive 				= "10110";		// 教室直播
	public final static String Msg_GetCourseLive 			= "10111";		// 课程直播
	public final static String Msg_RoomMgr 					= "10112";		// 教室管理
	public final static String Msg_GetRoomList 				= "10113";		// 教室列表
	public final static String Msg_GetRoomDetail 			= "10114";		// 教室详细
	public final static String Msg_GetNewRoomLive 			= "10115";		// 新教室直播
	public final static String Msg_Lb_Start 				= "10201";		// 开始录制 ------102+是控制接口
	public final static String Msg_Lb_Pause					= "10202";		// 暂停录制 
	public final static String Msg_Lb_Continue 				= "10203";		// 继续录制 
	public final static String Msg_Lb_Stop 					= "10204";		// 停止录制 
	public final static String Msg_Log_List					= "10301";		// 获取日志-----103+是日志接口
	public final static String Msg_GetSign	 				= "10401";		// 获取动态sign-----104+是权限接口
	public final static String Msg_Room_Add_Users 			= "10402";		// 授权教室给用户
	public final static String Msg_Room_Remove_Users		= "10403";		// 取消用户的教室授权
	public final static String Msg_Get_Users_By_Room_Auth	= "10404";		// 获取对教室有权限的用户
	public final static String Msg_Check_Auth_In_Room		= "10405";		// 获取判断用户对教室是否有权限
}
