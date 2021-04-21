package org.example.dao;

import org.example.Util.DBUtil;
import org.example.entity.Music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对music表进行增删查改操作
 */

public class MusicDao {
    public  List<Music> selectMusic(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Music> list= new ArrayList<>();
        try{
            String sql = " select * from music";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Music music = new Music();
                music.setId(rs.getInt("id"));
                music.setTitle(rs.getString("title"));
                music.setSinger(rs.getString("singer"));
                music.setTime(rs.getString("time"));
                music.setUrl(rs.getString("url"));
                music.setUserid(rs.getInt("userid"));
                list.add(music);
            }

            } catch (SQLException e) {
                e.printStackTrace();

            }
        return list;
    }


    //根据歌曲排序查询歌曲
    public  Music selectMusicById(int id){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Music music = null;


        try{
            String sql = "select * from music where id=?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            rs = ps.executeQuery();
            if (rs.next()) {
                music = new Music();
                music.setId(rs.getInt("id"));
                music.setTitle(rs.getString("title"));
                music.setSinger(rs.getString("singer"));
                music.setUrl(rs.getString("url"));
                music.setTime(rs.getString("time"));
                music.setUserid(rs.getInt("userid"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,rs);
        }
        return music;
    }


    //根据关键字查询歌曲
    public  List<Music> MusicExist(String str){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Music> musicList = new ArrayList<>();
        try{
            String sql = "select * from music where title like '%"+str+"%'";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                Music music = new Music();
                music.setId(rs.getInt("id"));
                music.setTitle(rs.getString("title"));
                music.setSinger(rs.getString("singer"));
                music.setUrl(rs.getString("url"));
                music.setTime(rs.getString("time"));
                music.setUserid(rs.getInt("userid"));

                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("查询失败");
        }finally {
            DBUtil.getclose(connection,ps,rs);
        }
        return musicList;
    }


    public int Insert(String title, String singer,String time,String url,int userid){
        Connection connection = null;
        PreparedStatement ps =null;
        ResultSet rs= null;

        try{
            String sql = "insert into music(title,singer,time,url,userid) values(?,?,?,?,?)";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1,title);
            ps.setString(2,singer);
            ps.setString(3,time);
            ps.setString(4,url);
            ps.setInt(5,userid);

            int line= ps.executeUpdate();
            if (line == 1) {
                return 1;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,rs);
        }
        return 0;
    }


    /**
     * 在删除某首歌时，如果它在库里存在，也要一起删除
     * @param id
     * @return
     */
    public  int deleteMusicById(int id) {
        Connection connection = null;
        PreparedStatement ps= null;

        try{

            String sql = "delete from music where id = ?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            //设置占位符
            ps.setInt(1,id);

            int line = ps.executeUpdate();
            //如果删除成功，返回成功删除的行数
            if (line == 1) {
                if (findOnloverMusic(id)){
                   int ret =  removeOnloverMusic(id);
                    if (ret == 1) {
                        return 1;
                    }
                }
                return 1;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,null);
        }
        //否则删除失败，返回 0
        return 0;
    }

    //删除库里的音乐
    private  int removeOnloverMusic(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int line = 0;

        try{
            String sql = "delete from lovermusic where id = ?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
           line = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,null);
        }
        return line;
    }

    //查找库里有没有存在要删除的音乐
    private  boolean findOnloverMusic(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select * from lovermusic where id = ?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,rs);
        }
        return false;
    }


    /**
     * 在添加音乐到喜欢的列表里，需要先判断该音乐是否已经被添加过了
     * @return
     */
    public boolean findlovermusicById(int user_id,int music_id){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select * from lovermusic where user_id=? and music_id=?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            ps.setInt(2,music_id);

            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,null);
        }
        return false;
    }


    //添加音乐到喜欢的音乐列表里
    public  boolean insertLovermusic(int user_id,int music_id){
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            String sql = "insert into lovermusic(user_id,music_id) values (?,?)";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);

            ps.setInt(2,music_id);

            int line = ps.executeUpdate();

            if (line == 1) {
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,null);
        }
        return false;
    }


     //在喜欢的音乐列表里移除音乐
    public int removeloverMusic(int user_id,int music_id){
        Connection connection = null;
        PreparedStatement ps = null;
        int line  =0;

        try{
            String sql = "delete from lovermusic where user_id=? and music_id=?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            ps.setInt(2,music_id);
            line = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,null);
        }
        return line;
    }



    //查询当前用户喜欢的所有音乐
    public  List<Music> findLoveById(int user_id){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Music> musicList = new ArrayList<>();

        try{
            String sql = "select * from music where id in(select music_id from lovermusic where user_id=?) ";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            rs = ps.executeQuery();

           while (rs.next()) {
               Music music = new Music();
               music.setId(rs.getInt("id"));
               music.setTitle(rs.getString("title"));
               music.setSinger(rs.getString("singer"));
               music.setUrl(rs.getString("url"));
               music.setTime(rs.getString("time"));
               music.setUserid(rs.getInt("userid"));
               musicList.add(music);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.getclose(connection,ps,null);
        }
        return musicList;
    }


    //某个用户喜欢的列表里根据歌名进行模糊查询
    public  List<Music> findByLike(String str,int user_id){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Music> musicList = new ArrayList<>();
        try{
            String sql = "select * from music where id in(select music_id from lovermusic where user_id=? and title like '%"+str+"%')";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(1,user_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                Music music = new Music();
                music.setId(rs.getInt("id"));
                music.setTitle(rs.getString("title"));
                music.setSinger(rs.getString("singer"));
                music.setUrl(rs.getString("url"));
                music.setTime(rs.getString("time"));
                music.setUserid(rs.getInt("userid"));

                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("查询失败");
        }finally {
            DBUtil.getclose(connection,ps,rs);
        }
        return musicList;
    }


    public static void main(String[] args) {
//        List<Music> musicList= selectMusic();
//        System.out.println(musicList);

//        Music music = selectMusicById(1);
//        System.out.println(music);

//        List<Music> musicList = MusicExist("Fade");
//        System.out.println(musicList);

        //Insert("有点甜","汪苏泷","2018-5-12","弗斯特\\有点甜",3);

        //System.out.println(deleteMusicById(6));

       // System.out.println(insertLovermusic(2, 4));

        //System.out.println(findLoveById(1));

        //System.out.println(findByLike("红", 1));

    }

}
