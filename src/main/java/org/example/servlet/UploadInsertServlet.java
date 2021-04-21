package org.example.servlet;

import org.example.dao.MusicDao;
import org.example.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/uploadsucess")
public class UploadInsertServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");

        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型
        resp.setContentType("application/json");

        String singer = req.getParameter("singer");

        String fileName= (String)req.getSession().getAttribute("fileName");

        String[] string = fileName.split("\\.");

        String title = string[0];

        //获取时间 -年月日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String time =  sdf.format(new Date());

        User user = (User)req.getSession().getAttribute("user");
        int user_id = user.getId();
        String url = "music/"+title;

        MusicDao musicDao = new MusicDao();
        int ret = musicDao.Insert(title,singer,time,url,user_id);

        if (ret == 1) {
            //插入成功，重新跳转到list.html
            //响应体重新定义
            resp.sendRedirect("list.html");
        }
    }
}
