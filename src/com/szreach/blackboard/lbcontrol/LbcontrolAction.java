package com.szreach.blackboard.lbcontrol;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

import com.szreach.blackboard.lbcontrol.tool.CommonTools;
import com.szreach.blackboard.lbcontrol.tool.MediacenterService;
import com.szreach.blackboard.lbcontrol.tool.MsgCode;
import com.szreach.blackboard.lbcontrol.tool.ParamRequest;
import com.szreach.blackboard.lbcontrol.tool.XmlReader;

//TODO 所有操作都要求登录
public class LbcontrolAction extends Action{

	private static final long serialVersionUID = 1L;
	
	private static final int FN_ROOM_CONTROL = 20001;			// 录播控制
	private static final int FN_PROXY_LIVE = 20002;				// 直播
	private static final int FN_LOGIN_MC = 20003;				// 登录媒体中心
	
	
	private static final int FN_FILE_LIST = 20004;				// 课件列表
	private static final int FN_TOTAL = 20005;					// 点播统计
	private static final int FN_PROXY_VOD = 20006;				// 点播
	private static final int FN_SETTING = 20007;				// 设置页面
	private static final int FN_SETTING_SAVE = 20008;			// 保存设置
	
	protected final static String URL_ROOM_LIST = "/roomList.jsp";
	protected final static String URL_FILE_LIST = "/fileList.jsp";
	protected final static String URL_TOTAL = "/total.jsp";
	protected final static String URL_PROXY_PLAY = "/play.jsp";
	protected final static String URL_LOGIN_MC = "/login_mc.jsp";
	protected final static String URL_SETTING = "/setting.jsp";

	@Override
	public void execute() {
        int cmd = ParamRequest.getInt(request, "fn");
        switch (cmd) {
            case FN_LIST :
                list();
                break;
            case FN_ROOM_CONTROL :
            	roomControl();
            	break;
            case FN_PROXY_LIVE :
            	proxyLive();
            	break;
            case FN_PROXY_VOD :
            	proxyVod();
            	break;
            case FN_FILE_LIST :
            	fileList();
            	break;
            case FN_TOTAL :
            	total();
            	break;            	
            case FN_LOGIN_MC :
            	loginMc();
            	break;
            case FN_SETTING :
            	settting();
            	break;
            case FN_SETTING_SAVE :
            	settting_save();
            	break;
            default :
                index();
                break;
        }
	}
	

	/**
	 * 获取教室列表
	 */
	public void list() {	
		List<RoomItem> list = new ArrayList<RoomItem>();
		String err			= "";
		
		// 获取参数
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startIndex = ParamRequest.getInt(request, "startIndex", 0);
		int numResults = ParamRequest.getInt(request, "numResults", 25);
		boolean showAll = ParamRequest.getBoolean(request, "showAll", false);
		
		map.put("PageSize", (showAll? 1024 : numResults));
		map.put("PageNum", (startIndex / numResults + 1));
		map.put("UserName", getCurrentUserName());	
		
		// 发送请求
		MediacenterService mcService = new MediacenterService();
		mcService.setMcHost("http://192.168.4.221");
		String getMsg = mcService.request(MsgCode.Msg_GetRoomLive, map);
		
		try{
			if(CommonTools.isNotNull(getMsg)) {
				XmlReader xml = new XmlReader();
				xml.parse(getMsg);
				String returnCode = xml.find("/MsgHead/ReturnCode").getText();
				
				if(returnCode.equals("1")) {
					
					Element paramRoot = xml.find("/MsgBody");
					List<Element> elementList = xml.finds("/RoomLives/RoomLive", paramRoot);
					if(elementList != null){
						for (Element el : elementList) {
							RoomItem item = new RoomItem();
							item.setId(xml.find("/Id", el).getText());
							item.setClassRoomName(xml.find("/ClassRoomName", el).getText());
							item.setConFlag(Integer.parseInt(xml.find("/ConFlag", el).getText()));
							item.setRecordFlag(Integer.parseInt(xml.find("/RecordFlag", el).getText()));
							item.setRecServerType(xml.find("/RecServerType", el).getText());
							item.setRecServerIp(xml.find("/RecServerIp", el).getText());
							item.setChannel(Integer.parseInt(xml.find("/Channel", el).getText()));
							List<String> liveUrls = new ArrayList<String>();
							for(Element url : xml.finds("/LiveUrls/LiveUrl", el)) {
								liveUrls.add(changeUrl2Moodle(url.getText(), "live"));
							}
							item.setLiveUrls(liveUrls);
							list.add(item);
						}
					}
				}else {
					err = xml.find("/MsgBody/FaultString").getText();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("err", err);
		request.setAttribute("list",list);
		forword(URL_ROOM_LIST);
	}

	/**
	 * 录播控制指令
	 */
	public void roomControl(){
		// 获取参数
		String rid = ParamRequest.getString(request, "rid");
		String opt = ParamRequest.getString(request, "opt");
		
		if(CommonTools.isNull(rid) ||  CommonTools.isNull(opt)) {
			returnResult("rid or opt is empty");
			return;
		}
		
		//TODO 权限检测，1.管理员权限，2模块权限
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("RoomId", rid);
		map.put("UserName", getCurrentUserName());
		map.put("UserOption", 1);
		map.put("Publish", 0);
		
		// 发送请求
		MediacenterService mcService = new MediacenterService();
		mcService.setMcHost("http://192.168.4.221");
		String getMsg = mcService.request(opt, map);
		
		try{
			if(CommonTools.isNotNull(getMsg)) {
				XmlReader xml = new XmlReader();
				xml.parse(getMsg);
				String returnCode = xml.find("/MsgHead/ReturnCode").getText();
				
				if(returnCode.equals("1")) {
					
					Element paramRoot = xml.find("/MsgBody");
					String result = xml.find("/Result", paramRoot).getText();
					returnResult(result);
					return;
					
				}else {
					returnResult(xml.find("/MsgBody/FaultString").getText());
					return;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return;
	}

	/**
	 * 代理直播
	 */
	public void proxyLive() {
		// 获取参数
		String recip = ParamRequest.getString(request, "recip");
		String channel = ParamRequest.getString(request, "channel");
		
		if(CommonTools.isNull(recip) ||  CommonTools.isNull(channel)) {
			returnResult("recip or channel is empty");
			return;
		}
		
		//TODO 权限检测，1.管理员权限，2模块权限，4课程下放权限
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Type", 0);
		map.put("UserName", getCurrentUserName());
		map.put("ClientIp", request.getRemoteAddr());
		map.put("Content", recip + "-" + channel);
		
		_proxyPlay(map, 0);
	}
	
	/**
	 * 代理点播
	 */
	public void proxyVod() {
		// 获取参数
		String fileId = ParamRequest.getString(request, "a");
		
		if(CommonTools.isNull(fileId)) {
			returnResult("fileId is empty");
			return;
		}
		
		//TODO 权限检测，1.管理员权限，2模块权限，4课程下放权限
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Type", 1);
		map.put("UserName", getCurrentUserName());
		map.put("ClientIp", request.getRemoteAddr());
		map.put("Content", fileId);
		
		_proxyPlay(map, 1);
	}	
	
	// 直播或者点播
	private void _proxyPlay(HashMap<String, Object> map, int playType) {		
		MediacenterService mcService = new MediacenterService();
		mcService.setMcHost("http://192.168.4.221");
		String getMsg = mcService.request(MsgCode.Msg_GetSign, map);
		
		try{
			if(CommonTools.isNotNull(getMsg)) {
				XmlReader xml = new XmlReader();
				xml.parse(getMsg);
				String returnCode = xml.find("/MsgHead/ReturnCode").getText();
				
				if(returnCode.equals("1")) {
					
					Element paramRoot = xml.find("/MsgBody");
					String sign = xml.find("/Sign", paramRoot).getText();
					request.setAttribute("msHost", "http://192.168.4.221");
					request.setAttribute("sign",sign);
					request.setAttribute("playType",playType);
					forword(URL_PROXY_PLAY);
					return;
					
				}else {
					returnResult(xml.find("/MsgBody/FaultString").getText());
					return;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		returnResult("err");
		return;		
	}
	
	/**
	 * 登录媒体中心后台
	 */
	public void loginMc() {
		// 获取参数
		String type = ParamRequest.getString(request, "type");
		String menu = ParamRequest.getString(request, "menu");
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Type", 2);
		map.put("UserName", getCurrentUserName());
		map.put("ClientIp", request.getRemoteAddr());
		map.put("Conent", "");
		
		// 发送请求
		MediacenterService mcService = new MediacenterService();
		mcService.setMcHost("http://192.168.4.221");
		String getMsg = mcService.request(MsgCode.Msg_GetSign, map);
		
		try{
			if(CommonTools.isNotNull(getMsg)) {
				XmlReader xml = new XmlReader();
				xml.parse(getMsg);
				String returnCode = xml.find("/MsgHead/ReturnCode").getText();
				
				if(returnCode.equals("1")) {
					
					Element paramRoot = xml.find("/MsgBody");
					String sign = xml.find("/Sign", paramRoot).getText();
					
					String mclogin_url = "http://192.168.4.221" + "/backstage/Login.action?cmd=17006&userOption=1&username=" + getCurrentUserName() + "&sign=" + sign;
				    String mcback_url = "http://192.168.4.221" + "/backstage/index.jsp?";
				    if(CommonTools.isNotNull(type)) {
				    	mcback_url += "type=" + type;
				        if(CommonTools.isNotNull(menu)) {
				        	mcback_url += "&menu=" + menu;
				        }
				    }
				    request.setAttribute("mclogin_url", mclogin_url);
				    request.setAttribute("mcback_url", mcback_url);
				    forword(URL_LOGIN_MC);
					return;
					
				}else {
					returnResult(xml.find("/MsgBody/FaultString").getText());
					return;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		returnResult("err");
		return;
	}
	
	public void index() {
		
	}	
	
	/**
	 * 课件列表
	 */
	public void fileList() {	
		List<FileItem> list = new ArrayList<FileItem>();
		String err			= "";
		
		// 获取参数
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startIndex = ParamRequest.getInt(request, "startIndex", 0);
		int numResults = ParamRequest.getInt(request, "numResults", 25);
		boolean showAll = ParamRequest.getBoolean(request, "showAll", false);
		String searchText = ParamRequest.getString(request, "searchText", "");
		
		map.put("PageSize", (showAll? 1024 : numResults));
		map.put("PageNum", (startIndex / numResults + 1));
		map.put("UserName", getCurrentUserName());
		map.put("Keyword", searchText);
		
		// 发送请求
		MediacenterService mcService = new MediacenterService();
		mcService.setMcHost("http://192.168.4.221");
		String getMsg = mcService.request(MsgCode.Msg_GetFileList, map);
		
		try{
			if(CommonTools.isNotNull(getMsg)) {
				XmlReader xml = new XmlReader();
				xml.parse(getMsg);
				String returnCode = xml.find("/MsgHead/ReturnCode").getText();
				
				if(returnCode.equals("1")) {
					
					Element paramRoot = xml.find("/MsgBody");
					List<Element> elementList = xml.finds("/Files/File", paramRoot);
					if(elementList != null){
						for (Element el : elementList) {
							FileItem item = new FileItem();
							item.setId(xml.find("/Id", el).getText());
							item.setFileCname(xml.find("/FileCName", el).getText());
							item.setAuthor(xml.find("/Author", el).getText());
							item.setSize(Long.parseLong(xml.find("/Size", el).getText()));
							item.setDuration(xml.find("/Duration", el).getText());
							item.setRecordTime(xml.find("/RecordTime", el).getText());
							item.setVodCount(Integer.parseInt(xml.find("/VodCount", el).getText()));
							item.setPublishBlackBoardStatus(Integer.parseInt(xml.find("/PublishBlackBoardStatus", el).getText()));
							item.setUts(xml.find("/UTS", el).getText());
							item.setVodUrl(changeUrl2Moodle(xml.find("/VodUrl", el).getText(), "vod"));
							item.setPreviewUrl("http://192.168.4.221" + xml.find("/PreviewUrl", el).getText());
							
							list.add(item);
						}
					}
				}else {
					err = xml.find("/MsgBody/FaultString").getText();
				}
			}
		}catch(Exception e) {
			err = "system error!";
			e.printStackTrace();
		}
		
		request.setAttribute("err", err);		
		request.setAttribute("list",list);
		request.setAttribute("startIndex",startIndex);
		request.setAttribute("numResults",numResults);
		request.setAttribute("showAll",showAll);
		request.setAttribute("searchText",searchText);
		forword(URL_FILE_LIST);
	}
	
	/**
	 * 点播统计
	 */
	public void total() {
		List<LogItem> list = new ArrayList<LogItem>();
		String err			= "";		
		
		// 获取参数
		String fileId = ParamRequest.getString(request, "a");
		
		Calendar cal = Calendar.getInstance();
		String m0 = CommonTools.dateToString(cal.getTime(), "yyyy-MM") + "-01";//当前月
		cal.add(Calendar.MONTH, -1);
		String m1 = CommonTools.dateToString(cal.getTime(), "yyyy-MM") + "-01";//上月
		cal.add(Calendar.MONTH, -1);
		String m2 = CommonTools.dateToString(cal.getTime(), "yyyy-MM") + "-01";//上上月
		int c0 = 0;
		int c1 = 0;
		int c2 = 0;
		
		if(CommonTools.isNull(fileId)) {
			err = "fileId is empty!";
		}else{
			
			//TODO 权限检测，1.管理员权限，2模块权限，3媒体中心权限，4课程下放权限
			
			// step1: --------------先获取文件名-------------
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("FileId", fileId);
			MediacenterService mcService = new MediacenterService();
			mcService.setMcHost("http://192.168.4.221");
			String getMsg = mcService.request(MsgCode.Msg_GetFileDetail, map);
			
			String fileCname = null;
			try{
				if(CommonTools.isNotNull(getMsg)) {
					XmlReader xml = new XmlReader();
					xml.parse(getMsg);
					String returnCode = xml.find("/MsgHead/ReturnCode").getText();
					
					if(returnCode.equals("1")) {
						
						Element paramRoot = xml.find("/MsgBody");
						fileCname = xml.find("/File/FileCName", paramRoot).getText();
						
					}else {
						err = xml.find("/MsgBody/FaultString").getText();
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(CommonTools.isNull(fileCname)) {
				err = "system error!";
			}else{
				// step2: --------------------根据文件名获取日志----------------

				map.clear();
				map.put("PageSize", 12800);
				map.put("PageNum", 1);
				map.put("Type", 160);
				map.put("BeginDate", m2);//最近三个月
				map.put("Keyword", fileCname);
						
				
				// 发送请求
				mcService.setMcHost("http://192.168.4.221");
				getMsg = mcService.request(10301, map);
				
				try{
					if(CommonTools.isNotNull(getMsg)) {
						XmlReader xml = new XmlReader();
						xml.parse(getMsg);
						String returnCode = xml.find("/MsgHead/ReturnCode").getText();
						
						if(returnCode.equals("1")) {
							Element paramRoot = xml.find("/MsgBody");
							List<Element> elementList = xml.finds("/Logs/Log", paramRoot);
							if(elementList != null){
								for (Element el : elementList) {
									LogItem item = new LogItem();
									item.setUsername(xml.find("/UserName", el).getText());
									item.setIp(xml.find("/Ip", el).getText());
									item.setLogTime(xml.find("/Time", el).getText());
									list.add(item);
									
									if(item.getLogTime().startsWith(m0)){
										c0++;
									}else  if(item.getLogTime().startsWith(m1)){
										c1++;
									}else if(item.getLogTime().startsWith(m2)) {
										c2++;
									}
								}
							}
						}else {
							err = xml.find("/MsgBody/FaultString").getText();
						}
					}
				}catch(Exception e) {
					err = "system error!";
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("err", err);		
		request.setAttribute("list",list);
		request.setAttribute("m0", m0);
		request.setAttribute("m1", m1);
		request.setAttribute("m2", m2);
		request.setAttribute("c0", c0);
		request.setAttribute("c1", c1);
		request.setAttribute("c2", c2);
		forword(URL_TOTAL);
	}
	
	/**
	 * 设置页面
	 */
	private void settting() {
		SettingItem settting = new SettingItem();
		
		//TODO 获取
		
		request.setAttribute("settting", settting);
		forword(URL_SETTING);
	}
	
	/**
	 * 保存设置
	 */
	private void settting_save() {
		SettingItem setting = new SettingItem();
		setting.setAddress(ParamRequest.getString(request, "address", "127.0.0.1"));
		setting.setAuth(ParamRequest.getString(request, "auth", "0"));
		
		//TODO 保存
		
		returnResult("success");
	}	
	
	// 把播放地址改成代理地址
	private String changeUrl2Moodle(String url, String type) {
        if(CommonTools.isNotNull(url)) {
        	String[] arr = url.split("[?]");
            //$arr2 = explode('repository', $_SERVER['PHP_SELF']);//娘的，处理可能的上下文，虽然PHP里面没有上下文的概念
            //$url = 'http://'.$_SERVER['HTTP_HOST'].$arr2[0].'blocks/lbcontrol/live.php?'.$arr[1];
            String qstr = arr[1].replaceAll("&preview=1", "");
            if("vod".equals(type)){
            	url = "lbcontrol.action?fn=20006&" +qstr;
            }else {//默认是live
            	url = "lbcontrol.action?fn=20002&" +qstr;
            }
        }
        return url;
	}
}
