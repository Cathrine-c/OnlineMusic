package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.MusicDao;
import org.example.entity.Music;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型
        resp.setContentType("application/json");

        String idstr = req.getParameter("id");

        int id = Integer.parseInt(idstr);

        MusicDao musicDao = new MusicDao();
        Music music = musicDao.selectMusicById(id);

        if (music == null) {
            return;
        }
        int delete = musicDao.deleteMusicById(id);

        Map<String,Object> map = new HashMap<>();

        if (delete == 1) {//删除数据库失败
            File file = new File("D:\\java_chongci\\OnlineMusic2.0\\src\\main\\webapp\\"+music.getUrl()+".mp3");
          //删除服务器里的资源
            if(file.delete()){
              map.put("msg",true)  ;
            }else {
                map.put("msg",false);
            }
        }else {//删除数据库失败
            map.put("mag",false);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),map);

    }
}
