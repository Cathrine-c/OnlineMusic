package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.MusicDao;

import org.example.entity.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/removeLoveServlet")
public class RemoveLoveMusicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");

        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");

        //设置响应体的数据类型
        resp.setContentType("application/json");

        String idStr = req.getParameter("id");
        int music_id = Integer.parseInt(idStr);

        User user = (User)req.getSession().getAttribute("user");
        int user_id = user.getId();

        MusicDao musicDao = new MusicDao();
        int ret = musicDao.removeloverMusic(user_id,music_id);

        Map<String,Object> map = new HashMap<>();

        if (ret == 1) {
            map.put("msg",true);

        }else {
            map.put("msg",false);

        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);

    }
}

