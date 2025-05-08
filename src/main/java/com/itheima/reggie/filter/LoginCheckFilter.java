package com.itheima.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login",
                "/user/sendMsg"
        };

        boolean check = check(urls, request.getRequestURI());
        if(check){
            filterChain.doFilter(request, response);
            return;
        }
        if(null != request.getSession().getAttribute("emp")){
            BaseContext.setEId((Long)((Employee)request.getSession().getAttribute("emp")).getId());
            filterChain.doFilter(request, response);
            return;
        }

        if(null != request.getSession().getAttribute("user")){
            BaseContext.setEId((Long)((User)request.getSession().getAttribute("user")).getId());
            filterChain.doFilter(request, response);
            return;
        }
        log.info("用户未登录，拦截请求: {}", request.getRequestURI());
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }


    public boolean check(String[] urls,String requestURI)
    {
        for(String url: urls)
        {
            if(PATH_MATCHER.match(url, requestURI)){
                return true;
            }
        }
        return false;
    }
}
