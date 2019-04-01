package com.example.demo.main.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.ResultData;
import com.example.demo.util.UtilClass;

/**
 * 校验token
 * @author Minson.
 * @date 2019年3月21日下午10:14:58
 */
@WebFilter(urlPatterns = {"/user/*"},filterName="userFilter")
public class UserFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;    
        String token=(String) request.getParameter("token");
        Long user_id=Long.valueOf(request.getParameter("user_id"));
        Integer login_type=Integer.valueOf(request.getParameter("login_type"));
        ResultData resultData=UtilClass.cheToken(token, user_id, login_type,1);
        if(resultData.getFlag()==1) {
        	chain.doFilter(request, response);
        }else {
        	String requestType = request.getHeader("X-Requested-With");
            //判断是否是ajax请求
            if (requestType != null && "XMLHttpRequest".equals(requestType)) {
            	response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(JSON.toJSONString(resultData));
            } else {
                response.sendRedirect(request.getContextPath() + "填写页面");
            }
            return;
        }	
	}
	
	@Override
    public void destroy() {
    }

}
