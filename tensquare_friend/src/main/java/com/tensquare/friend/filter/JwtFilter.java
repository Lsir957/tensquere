package com.tensquare.friend.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT鉴权拦截器
 */
@Component
public class JwtFilter extends HandlerInterceptorAdapter{

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在执行Controller方法之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //1 获取token字符串
        //1.1 获取请求头Authorization
        String auth = request.getHeader("Authorization");

        //1.2 判断是否为空
        if(auth!=null){
            //1.3 判断以Bearer开头
            if(auth.startsWith("Bearer")){
                //1.4 取出token字符串
                String token = auth.substring(7);

                //2. 解析token字符串是否合法的（包含是否过期）
                Claims body = jwtUtil.parseJWT(token);

                //3.从载荷获取角色信息，判断角色是否为管理器员
                String role = (String)body.get("roles");

                //4 是管理员，执行删除操作；不是管理员，提示"你不是管理员，权限不足"
                if("admin".equals(role)){
                    //是管理员，执行删除操作
                    //添加角色标记 request
                    request.setAttribute("admin_roles",body);
                }
            }
        }


        //true：放行；false：不放行
        return true;
    }
}
