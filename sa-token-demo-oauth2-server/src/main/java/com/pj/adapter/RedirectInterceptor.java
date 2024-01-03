package com.pj.adapter;

import cn.dev33.satoken.util.SaResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RedirectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在控制器方法处理请求前执行拦截逻辑
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在控制器方法处理请求后执行拦截逻辑

        // 判断响应是否为重定向
        if (request.getRequestURI().startsWith("/oauth2/authorize") && response.getStatus()==HttpStatus.FOUND.value()) {
            // 获取重定向的目标 URL
            String redirectUrl = response.getHeader("Location");
            System.out.println("重定向目标URL：" + redirectUrl);
            // 把重定向返回修改为正常的JSON响应返回
            response.setHeader("Content-Type", "application/json; charset=utf-8");
            response.setStatus(HttpStatus.OK.value());
            // 将目标 URL 作为响应返回给前端
            response.getWriter().write(SaResult.data(redirectUrl).toString());
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

}