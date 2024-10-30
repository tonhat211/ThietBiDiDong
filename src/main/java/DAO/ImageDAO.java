package DAO;

import model.Image;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImageDAO implements IDAO<Image>{
    public static ImageDAO getInstance(){
        return new ImageDAO();
    }

    @Override
    public int insert(Image image) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into images (title,url,parentID) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, image.getTitle());
            pst.setString(2, image.getUrl());
            pst.setInt(3,image.getParentID());
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Image image) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update images set url = ? where parentID =?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,image.getUrl());
            pst.setInt(2,image.getParentID());
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException ex) {
            // TODO: handle exception
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int delete(Image image) {
        return 0;
    }

    @Override
    public ArrayList<Image> selectAll() {
        ArrayList<Image> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "select * from images;";
            PreparedStatement pst = conn.prepareStatement(sql);


            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String url = rs.getString("url");
                int parentID = rs.getInt("parentID");

                res.add(new Image(id,title,url,parentID));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Image selectById(int id) {
        return null;
    }

    public ArrayList<Image> selectByParentID(int pID) {
        ArrayList<Image> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "select * from images " +
                    "where parentID = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, pID);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String url = rs.getString("url");
                int parentID = rs.getInt("parentID");
                res.add(new Image(id,title,url,parentID));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ArrayList<Image> images = ImageDAO.getInstance().selectAll();
        for(Image image : images){
            System.out.println(image);
        }
    }

}
