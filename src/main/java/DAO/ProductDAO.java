package DAO;

import model.Product;
import model.ProductDetail;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO implements IDAO<Product> {
    public static ProductDAO getInstance(){
        return new ProductDAO();
    }
    @Override
    public int insert(Product product) {
        return 0;
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public int delete(Product product) {
        return 0;
    }

    @Override
    public ArrayList<Product> selectAll() {
        return null;
    }

    @Override
    public Product selectById(int id) {
        return null;
    }

//    truy van theo loai, (mac dinh) khong filter
    public ArrayList<Product> selectByCategoryLimit(int cateID,int offset,int amount) {
        ArrayList<Product> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `products` WHERE cateID = ? and avai =1 ORDER by prominence DESC limit ?,?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, cateID);
            pst.setInt(1, offset);
            pst.setInt(1, amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double star = rs.getDouble("star");
                String thumbnail = rs.getString("thumbnail");
                res.add(new Product(id,name,star,thumbnail));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
