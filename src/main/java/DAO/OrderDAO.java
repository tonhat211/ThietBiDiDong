package DAO;

import model.CartUnit;
import model.Order;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderDAO implements IDAO<Order> {
    public static OrderDAO getInstance(){
        return new OrderDAO();
    }

    @Override
    public int insert(Order order) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into orders (money,userID,address) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, order.getMoney());
            pst.setInt(2, order.getUserID());
            pst.setString(3, order.getAddress());
            re = pst.executeUpdate();

            String sql2 = "select max(id) as id from orders where userID= "+order.getUserID()+";";
            PreparedStatement pst2 = conn.prepareStatement(sql2);
            ResultSet rs = pst2.executeQuery();
            while(rs.next()){
                re = rs.getInt("id");
            }

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Order order) {
        return 0;
    }

    @Override
    public int delete(Order order) {
        return 0;
    }

    @Override
    public ArrayList<Order> selectAll() {
        return null;
    }

    @Override
    public Order selectById(int id) {
        return null;
    }

    public int insertOrderDetail(int orderID,ArrayList<CartUnit> carts) {
        int re=0;
        String valueParams = "";
        for(CartUnit c : carts){
            valueParams+="(" + orderID +","+c.getProductDetailID()+","+c.getPrice()+","+c.getQty() +"),";
        }
        valueParams = valueParams.substring(0, valueParams.length()-1);

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into orderDetails (orderID,productDetailID,currentPrice,qty) values "+ valueParams+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Order> selectByUserID(int userIDin) {
        ArrayList<Order> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders where userID= ;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime dateFinish = rs.getObject("dateFinish",LocalDateTime.class);
                int status = rs.getInt("status");
                res.add(new Order(id,money,userID,address,dateSet,dateFinish,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<CartUnit> selectByOrderID(ArrayList<Integer> ids) {
        ArrayList<CartUnit> res = new ArrayList<>();
        String idCondition = "(";
        for(Integer id : ids){
            idCondition+=id+",";
        }
        idCondition=idCondition.substring(0, idCondition.length()-1);
        idCondition+=")";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, d.id as productDetailID, p.name, p.version, p.thumbnail, d.color, d.ram, d.rom, od.currentPrice, od.qty\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tjoin orderdetails od on d.id = od.productDetailID" +
                    "where od.id in " + idCondition +";\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int deid = rs.getInt("productDetailID");
                String name = rs.getString("name");
                String version = rs.getString("version");
                String thumbnail = rs.getString("thumbnail");
                String color = rs.getString("color");
                int ram = rs.getInt("ram");
                int rom = rs.getInt("rom");
                double price = rs.getDouble("currentPrice");
                int qty = rs.getInt("qty");

                res.add(new CartUnit(id,deid,name,version,thumbnail,color,ram,rom,price,qty));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
