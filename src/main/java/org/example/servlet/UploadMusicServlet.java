package org.example.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.example.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class UploadMusicServlet extends HttpServlet {
    //定义路径,一般不会变，所以定义为成员变量
    private final String SavePath = "D:\\java_chongci\\OnlineMusic2.0\\src\\main\\webapp\\music";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型
        resp.setContentType("application/json");


        User user = (User)req.getSession().getAttribute("user");

        if (user == null) {
            System.out.println("请登录后再上传音乐哦！！！！");
            resp.getWriter().write("<h2> 请登录后再上传音乐哦！！！！</h2>");
            return;
        }else {

            //用来上传文件
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> fileItems = null;

            try{
                fileItems = upload.parseRequest(req);
            }catch (FileUploadException e){
                e.printStackTrace();
                return;
            }
            System.out.println("fileItems"+fileItems);

            FileItem fileItem = fileItems.get(0);

            System.out.println(""+fileItem);

            String fileName = fileItem.getName();//获取文件名


            try {
                fileItem.write(new File(SavePath,fileName));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            //2.上传到数据库
            resp.sendRedirect("uploadsucess.html");

        }

    }
}
