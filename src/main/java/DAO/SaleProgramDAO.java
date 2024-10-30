package DAO;

import model.Image;
import model.Property;
import model.SaleProgram;
import service.JDBCUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaleProgramDAO implements IDAO<SaleProgram> {
    public static SaleProgramDAO getInstance(){
        return new SaleProgramDAO();
    }

    @Override
    public int insert(SaleProgram saleProgram) {
        return 0;
    }

    @Override
    public int update(SaleProgram saleProgram) {
        return 0;
    }

    @Override
    public int delete(SaleProgram saleProgram) {
        return 0;
    }

    @Override
    public ArrayList<SaleProgram> selectAll() {
        return null;
    }

    @Override
    public SaleProgram selectById(int id) {
        SaleProgram saleProgram = null;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "select * from saleprograms where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int reid = rs.getInt("id");
                int objectID = rs.getInt("objectID");
                String name = rs.getString("name");
                int main = rs.getInt("main");
                String content = rs.getString("content");
                double discount = rs.getDouble("discount");
                Date startTime =rs.getDate("startTime");
                LocalDateTime endTime =rs.getObject("endTime",LocalDateTime.class);
                int avai = rs.getInt("avai");
                saleProgram = new SaleProgram(reid, objectID, name, main, content, discount, startTime, endTime, avai);

            }

            JDBCUtil.closeConnection(conn);
            return saleProgram;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SaleProgram selectByObjectId(int id) {
        SaleProgram saleProgram = null;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "select * from saleprograms where objectID = ? and main = " + Property.MAIN;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int reid = rs.getInt("id");
                int objectID = rs.getInt("objectID");
                String name = rs.getString("name");
                int main = rs.getInt("main");
                String content = rs.getString("content");
                double discount = rs.getDouble("discount");
                Date startTime =rs.getDate("startTime");
                LocalDateTime endTime =rs.getObject("endTime",LocalDateTime.class);
                int avai = rs.getInt("avai");
                saleProgram = new SaleProgram(reid, objectID, name, main, content, discount, startTime, endTime, avai);

            }

            JDBCUtil.closeConnection(conn);
            return saleProgram;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDateTime getEndTimeOnline() {
        LocalDateTime re = LocalDateTime.parse("2003-10-23T14:30:00");
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "select endTime from saleprograms where main = ? and avai = 1 order by id desc limit 0,1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Property.ONLINE);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                re = rs.getObject("endTime",LocalDateTime.class);
            }

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
