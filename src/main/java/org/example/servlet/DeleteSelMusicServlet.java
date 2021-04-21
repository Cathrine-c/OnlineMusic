package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
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

@WebServlet("/deleteSelMusicServlet")

         //删除选中音乐
public class DeleteSelMusicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型
        resp.setContentType("application/json");

        //存放所有需要删除歌曲的id数组
        String[] values = req.getParameterValues("id[]");

        MusicDao musicDao = new MusicDao();

        int sum =0;
        Map<String,Object> map = new HashMap<>();
        for (int i =0;i<values.length;i++){
            int id = Integer.parseInt(values[i]);
            Music music = musicDao.selectMusicById(id);

            int delete = musicDao.deleteMusicById(id);


            if (delete == 1) {

                File file = new File("D:\\java_chongci\\OnlineMusic2.0\\src\\main\\webapp\\"+music.getUrl()+".mp3");
                if (file.delete()){
                    sum +=delete;

                }else {
                    map.put("msg",false);
                    System.out.println("服务器删除失败");
                }

            }else {
                map.put("msg",false);
                System.out.println("数据库删除失败");
            }
        }
        if (sum == values.length) {
            map.put("msg",true);

        }else {
            map.put("msg",false);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);

    }
}
