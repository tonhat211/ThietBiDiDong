package DAO;

import model.Address;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDAO implements IDAO<Address>{
    public static AddressDAO getInstance(){
        return new AddressDAO();
    }
    @Override
    public int insert(Address address) {
        return 0;
    }

    @Override
    public int update(Address address) {
        return 0;
    }

    @Override
    public int delete(Address address) {
        return 0;
    }

    @Override
    public ArrayList<Address> selectAll() {
        return null;
    }

    @Override
    public Address selectById(int id) {
        return null;
    }

    public ArrayList<Address> selectByUserID(int userIDin) {
        ArrayList<Address> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from addresses where userID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String receiver = rs.getString("receiver");
                String phone = rs.getString("phone");
                String street = rs.getString("street");
                String village = rs.getString("village");
                String district = rs.getString("district");
                String province = rs.getString("province");
                res.add(new Address(id,receiver,phone,street,village,district,province));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
