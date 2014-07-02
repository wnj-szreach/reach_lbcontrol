package com.szreach.blackboard.lbcontrol.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MediacenterService {
	
	private String host = "";
    
    public void setMcHost(String ip) {
    	this.host = this.fixHost(ip);
    }
	
	
	/**
	 * 发送请求1
	 * @param code
	 * @param map
	 * @return
	 */
	public String request(int code, HashMap<String, Object> map) {
		return this.request(String.valueOf(code), map);
	}
	
	/**
	 * 发送请求2
	 * @param code
	 * @param map
	 * @return
	 */
	public String request(String code, HashMap<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<RequestMsg>");
		sb.append("<MsgHead><MsgCode>").append(code).append("</MsgCode></MsgHead>");
		sb.append("<MsgBody>");
		sb.append(this.entry2xml(map, null));
		sb.append("</MsgBody>");
		sb.append("</RequestMsg>");
		return this.request(sb.toString());
	}	
	
	/**
	 * 发送请求3
	 * @param date
	 * @return
	 */
	public String request(String date) {
		URL u = null;
		HttpURLConnection con = null;

		// 尝试发送请求
		try {
			u = new URL(host + "/XmlRpcService.action");
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(date);
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();		
	}
	
	
    // 修正媒体中心地址
    private String fixHost(String ip) {
        if (ip == null || ip == "") {
        	ip = "http://127.0.0.1";
        } else if (!ip.startsWith("http")) {//地址修正
            ip = "http://" + ip;
        }
        return ip;
    }	
	
	/**
	 * 参数转换成xml，entry仅支持HashMap、List、Integer、String类型
	 * @param entry
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String entry2xml(Object entry, String _key) {
		
		StringBuilder sb = new StringBuilder();
		
		if(entry == null) {// null
			return sb.toString();
		}else if(entry instanceof String) {// string
			return (String)entry;
			
		}else if(entry instanceof Integer) {// int
			return String.valueOf(((Integer)entry));
			
		}else if(entry instanceof HashMap) {// map
			HashMap<String, Object> map = (HashMap<String, Object>)entry;
			Iterator<String> itor = map.keySet().iterator();
			while(itor.hasNext()) {
				String key = itor.next();
				Object val = map.get(key);
				
				sb.append("<").append(key).append(">");
				
				sb.append(entry2xml(val, key));
				
				sb.append("</").append(key).append(">");
			}
			
		}else if(entry instanceof List) {// list
			if(CommonTools.isNotNull(_key)) {
				String __key = _key.substring(0, _key.length() - 1);//截取Users成User
				
				List<Object> list = (List<Object>)entry;
				for(Object obj : list) {
					sb.append("<").append(__key).append(">");
					
					sb.append(entry2xml(obj, null));//不支持二维数组
					
					sb.append("</").append(__key).append(">");
				}
			}
			
		}

		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		MediacenterService service = new MediacenterService();
		service.setMcHost("http://192.168.4.221");
		
//		request1测试		
//		String xml_request = "";
//        xml_request +=     "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
//        xml_request +=     "<RequestMsg>";
//        xml_request +=         "<MsgHead>";
//        xml_request +=             "<MsgCode>10108</MsgCode>";
//        xml_request +=         "</MsgHead>";
//        xml_request +=         "<MsgBody>";
//
//            xml_request +=             "<UserName>admin</UserName>";
//
//        xml_request +=             "<Keyword></Keyword>";
//        xml_request +=             "<BeginDate></BeginDate>";
//        xml_request +=             "<EndDate></EndDate>";
//        xml_request +=             "<PageSize>32</PageSize>";
//        xml_request +=             "<PageNum>1</PageNum>";
//        xml_request +=         "</MsgBody>";
//        xml_request +=     "</RequestMsg>";
//		String getMsg = service.request(xml_request);
		
//		request2测试		
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("PageSize", 25);
		map.put("PageNum", 1);
		map.put("UserName", "admin");		
		
		String getMsg = service.request(MsgCode.Msg_GetRoomLive, map);

		System.out.println(getMsg);		
	}
}
