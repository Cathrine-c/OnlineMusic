package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.example.entity.User;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/loginServlet")

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型
        resp.setContentType("application/json");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //实现登录
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);


        //让service调用登录功能
        UserService userService = new UserService();
        User user = userService.login(loginUser);

        Map<String,Object> map = new HashMap<>();

        if (user != null) {
            System.out.println("登录成功");
            req.getSession().setAttribute("user",user);
            map.put("msg",true);
        }else {
            System.out.println("登录失败");

            map.put("msg",false);
        }

        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(resp.getWriter(),map);

       // System.out.println(username+" "+password);

    }

}
