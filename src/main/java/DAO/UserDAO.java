package DAO;

import com.google.gson.Gson;
import model.Image;
import model.User;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements IDAO<User> {
    public static UserDAO getInstance(){
        return new UserDAO();
    }
    @Override
    public int insert(User user) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into users (email,name,password,avai) values(?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getName());
            pst.setInt(4,user.getAvai());
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set name = ? , email = ? , info = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getInfo());
            pst.setInt(4, user.getId());
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public ArrayList<User> selectAll() {
        ArrayList<User> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from users;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                res.add(new User(id,name,email));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User selectById(int idin) {
        User user = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String info = rs.getString("info");
                user = new User(id,name,email,info);
            }

            JDBCUtil.closeConnection(conn);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User checkLogin(String email, String password) {
        User user = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` WHERE email = ? and password =?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String reEmail = rs.getString("email");
                String rolesJson = rs.getString("roles");
                System.out.println("rolesJson: " + rolesJson);
                Gson gson = new Gson();
                String[] roles = gson.fromJson(rolesJson, String[].class);
                System.out.println("roles1: " + roles[0]);

                user = new User(id,name,reEmail,roles);
            }

            JDBCUtil.closeConnection(conn);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkEmail(String email) {
        int count =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT count(*) FROM `users` WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                count = rs.getInt("count(*)");
            }
            JDBCUtil.closeConnection(conn);
            if(count>0) return true;
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updatePassword(int idin, String pwd) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set password = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,pwd);
            pst.setInt(2, idin);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        User u = UserDAO.getInstance().checkLogin("2003tonhat@gmail.com", "1234");
        System.out.println(u.getRoles()[0]);

    }


}
