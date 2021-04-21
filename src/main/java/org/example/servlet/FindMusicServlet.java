package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.MusicDao;
import org.example.entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/findMusic")
public class FindMusicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型
        resp.setContentType("application/json");

        String musicName = req.getParameter("musicName");

        MusicDao musicDao = new MusicDao();
        List<Music> musicList = new ArrayList<>();

        if (musicName != null) {
            musicList = musicDao.MusicExist(musicName);


        }else {
           musicList = musicDao.selectMusic();

        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),musicList);

    }
}
