package com.flashmartj6.security;

import org.apache.tomcat.util.net.AbstractEndpoint.Handler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GlobalInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
