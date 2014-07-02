package com.szreach.blackboard.lbcontrol;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.session.BbSessionManagerServiceFactory;

import com.szreach.blackboard.lbcontrol.tool.CommonTools;

/**
 * servlet基类
 */
@SuppressWarnings("serial")
public abstract class Action extends HttpServlet {
	
	public final static String Encoding = "UTF-8";
	
    protected static final int FN_INDEX = 10000;
    protected static final int FN_LIST = 10001;
    protected static final int FN_ADD = 10002;
    protected static final int FN_INPUT = 10003;
    protected static final int FN_UPDATE = 10004;
    protected static final int FN_DEL = 10005;	
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		synchronized(this) {
			response.setContentType("text/html; charset=" + Encoding);
			request.setCharacterEncoding(Encoding);
			
			this.request = request;
			this.response = response;
			this.session = request.getSession();
	        
			// 找不到API，先这样处理登录校验
			try{
				ContextManagerFactory.getInstance().getContext().getUser().getUserName();
			}catch(Exception e) {
				returnLoginPage();
				return;
			}
			
			execute();
		}
	}
	
	public abstract void execute();
	
	public String getCurrentUserName(){
		if(this.request == null) {
			return null;
		}
//		ContextManagerFactory.getInstance().getContext().getUser();//这样也行
		String username = BbSessionManagerServiceFactory.getInstance().getSession(request).getUserName();// 法克，终于找到了
		if(CommonTools.isNotNull(username) && username.equals("administrator")) {
			username = "admin";//把BB的administrator转成MC的admin
		}
		return username;
	}
	
	public void redirect(String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
		}
	}
	
	public void returnLoginPage() {
	    try {
            response.sendRedirect("/webapps/login/");
        } catch (IOException e) {
        }
	}
	
	public void forword(String url) {
		try {
			getServletContext().getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
		} catch (IOException e) {
		}
	}
	
	public void returnResult(String result) {
		PrintWriter out = getOut();
		if (out != null) {
			out.print(result);
			out.flush();
			out.close();
		}
	}
	
	public void returnJson(String json) {
		PrintWriter out = getOut();
		if (out != null) {
			out.print(json);
			out.flush();
			out.close();
		}
	}
	
	public PrintWriter getOut() {
		try {
			return response.getWriter();
		} catch (IOException e) {
		}
		
		return null;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
