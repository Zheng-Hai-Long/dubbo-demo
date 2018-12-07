package com.guangde.home.filter;

import com.guangde.home.constant.UrlConstants;
import com.guangde.home.utils.CookieManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Date: 15-05-15
 *
 */
public class UrlFilter implements Filter {
    protected static final Log logger = LogFactory.getLog(UrlFilter.class);

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request2 =  (HttpServletRequest)request ;
        HttpServletResponse response2 =(HttpServletResponse) response;

        //String uid2 = (String) request2.getSession().getAttribute(SSOUtil.SESSION_USER);

        //指定允许其他域名访问
        response2.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");
        response2.setHeader("Access-Control-Allow-Headers", "token, Content-Type");
        response2.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response2.setHeader("Access-Control-Allow-Credentials", "true");
        // 响应类型

        if ("OPTIONS".equals(request2.getMethod())){
            response2.setStatus(HttpStatus.SC_OK);
        }

        String url=request2.getServletPath();
        String uid=request2.getParameter("uid");
        if(url==null || !url.endsWith(".jsp")){
            request.setCharacterEncoding("UTF-8");
        }
        StringBuffer  paraString = new StringBuffer();
        Enumeration<String> names = request2.getAttributeNames();

        String referer = request2.getHeader("referer");
        Map<String,String[]> param = request2.getParameterMap();
        boolean  is_rewrite = false;
        
        //非action请求过滤
      /*if(!(url.endsWith(".do") || url.endsWith(".htm")) ){
          chain.doFilter(request, response);
          return;
      }*/
        String agent = request2.getHeader("User-Agent");
        //判断是否为移动端访问  
        if(isMobile(agent)){
        	request2.getSession().setAttribute("ua","mobile");  
        }
//        else {
//            session.setAttribute("ua","pc");  
//		};
        //获取推广人id,存入cookie
        if(request2.getParameter("uid")!=null){
          	CookieManager.create("ePeopleId", request2.getParameter("uid"), 2592000, response2, false);
          }
        

        chain.doFilter(request, response);

    }
    
    boolean isMobile(String userAgent)
	{
		if (userAgent == null) {
			return false;
		} else {
			return userAgent.toLowerCase().matches("(.)*(android|iphone|phone)(.)*");
		}
	}

}
