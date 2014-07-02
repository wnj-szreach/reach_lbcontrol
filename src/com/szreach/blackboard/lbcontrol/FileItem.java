package com.szreach.blackboard.lbcontrol;

public class FileItem {
	private String id;
	private String fileCname;
	private String recordTime; 				/* 录制时间 */
	private String duration;  				/* 录制时长  hh:mm:ss */
	private String previewUrl; 				/* 第一张预览图 */
	private String vodUrl;	 				/* 点播地址*/
	private String author; 					// 主讲人
	private int vodCount;  					/* 点播次数*/
	private long size;						// 文件大小
	private int publishBlackBoardStatus;	// 发布状态，0表示未发布，1表示已发布 
	private String uts;						// 最后修改时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileCname() {
		return fileCname;
	}
	public void setFileCname(String fileCname) {
		this.fileCname = fileCname;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	public String getVodUrl() {
		return vodUrl;
	}
	public void setVodUrl(String vodUrl) {
		this.vodUrl = vodUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getVodCount() {
		return vodCount;
	}
	public void setVodCount(int vodCount) {
		this.vodCount = vodCount;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getPublishBlackBoardStatus() {
		return publishBlackBoardStatus;
	}
	public void setPublishBlackBoardStatus(int publishBlackBoardStatus) {
		this.publishBlackBoardStatus = publishBlackBoardStatus;
	}
	public String getUts() {
		return uts;
	}
	public void setUts(String uts) {
		this.uts = uts;
	}
	
}
