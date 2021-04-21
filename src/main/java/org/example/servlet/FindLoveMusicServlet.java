package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.MusicDao;
import org.example.entity.Music;
import org.example.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@WebServlet("/findLoveMusic")

public class FindLoveMusicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型
        resp.setContentType("application/json");

        String loveMusicName = req.getParameter("loveMusicName");

        MusicDao musicDao = new MusicDao();

        User user = (User)req.getSession().getAttribute("user");
        int user_id = user.getId();

        List<Music> list = new ArrayList<>();

        if (loveMusicName != null) {

            list = musicDao.findByLike(loveMusicName,user_id);


        }else {
            //默认查找全部的音乐
            list = musicDao.findLoveById(user_id);

        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),list);

    }
}
