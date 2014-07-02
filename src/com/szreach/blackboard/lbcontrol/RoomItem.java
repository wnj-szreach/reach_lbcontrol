package com.szreach.blackboard.lbcontrol;

import java.util.List;

public class RoomItem {
	private String id;					// 教室ID
	private String classRoomName;		// 教室名称
	private int channel;				// 录播通道
	private String recServerIp;			// 录播通道
	private String recServerType;		// 录播类型
	private int recordFlag;				// 当前录制状态
	private int conFlag;				// 连接状态
	private List<String> liveUrls;		// 直播地址
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassRoomName() {
		return classRoomName;
	}
	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public String getRecServerIp() {
		return recServerIp;
	}
	public void setRecServerIp(String recServerIp) {
		this.recServerIp = recServerIp;
	}
	public String getRecServerType() {
		return recServerType;
	}
	public void setRecServerType(String recServerType) {
		this.recServerType = recServerType;
	}
	public int getRecordFlag() {
		return recordFlag;
	}
	public void setRecordFlag(int recordFlag) {
		this.recordFlag = recordFlag;
	}
	public int getConFlag() {
		return conFlag;
	}
	public void setConFlag(int conFlag) {
		this.conFlag = conFlag;
	}
	public List<String> getLiveUrls() {
		return liveUrls;
	}
	public void setLiveUrls(List<String> liveUrls) {
		this.liveUrls = liveUrls;
	}

	
}
