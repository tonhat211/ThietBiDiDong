package DAO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Image;
import model.ProductDetail;
import model.ProductUnit;
import model.Property;
import service.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductUnitDAO implements IDAO<ProductUnit>{
    public static ProductUnitDAO getInstance(){
        return new ProductUnitDAO();
    }
    @Override
    public int insert(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public int update(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public int delete(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public ArrayList<ProductUnit> selectAll() {
        return null;
    }

    @Override
    public ProductUnit selectById(int pid) {
        ProductUnit pu = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "where p.id=?\n" +
                    "group by p.id\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, pid);
//            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                pu = new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, 0, null);

            }
            JDBCUtil.closeConnection(conn);
            return pu;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByCategory(int categoryID,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String condition="";
        if(categoryID!=Property.ALL) {
            condition = "where p.cateID=?\n";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                    condition +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            if(categoryID!=Property.ALL) {
                pst.setInt(1, categoryID);
                pst.setInt(2, offset);
                pst.setInt(3, amount);
            } else {
                pst.setInt(1, offset);
                pst.setInt(2, amount);
            }

            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectCrossSells(int productID,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id and s.main=1\n" +
                    "where p.id in (select otherID from crosssells where productID = ?)\n" +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, productID);
            pst.setInt(2, offset);
            pst.setInt(3, amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectBySaleProgram(int main,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                    "where s.main=?\n" +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, main);
            pst.setInt(2, offset);
            pst.setInt(3, amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByBrands(ArrayList<Integer> brandIDs,int sort, int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String condition="";
        if(!brandIDs.contains(0)) {
            condition = " where p.brandID in (";
            for(Integer id : brandIDs) {
                condition+=id+",";
            }
            condition=condition.substring(0, condition.length()-1);
            condition+=")";

        }
        String sortSql="";
        if(sort==Property.NEW) {
            sortSql="JSON_EXTRACT(p.firstsale, '$.date') DESC";
        } else if(sort==Property.PRICE_UP) {
            sortSql="min(d.price) asc";
        } else if (sort==Property.PRICE_DOWN) {
            sortSql="min(d.price) desc";
        } else {
            sortSql="p.prominence desc";
        }

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                     condition +"\n " +
                    "group by p.id\n" +
                    "order by "+ sortSql +"\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,offset);
            pst.setInt(2,amount);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByFilters(ArrayList<String> osList, ArrayList<Integer> priceRange, int sort, int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String osCondition="";
        String osListTemp="";
        if(!osList.isEmpty()) {
            for(String os : osList) {
                osListTemp+=os+"|";
            }
            osListTemp=osListTemp.substring(0, osListTemp.length()-1);
            osCondition = " where JSON_EXTRACT(p.config, '$.os') REGEXP '(?i)"+osListTemp+"'";
        }

        String priceCondition="";
        if(priceRange.size()==2) {
            priceCondition = " and d.price >= "+ priceRange.get(0)+" and d.price< "+priceRange.get(1)+" ";
        }

        String sortSql="";
        if(sort==Property.NEW) {
            sortSql="JSON_EXTRACT(p.firstsale, '$.date') DESC";
        } else if(sort==Property.PRICE_UP) {
            sortSql="min(d.price) asc";
        } else if (sort==Property.PRICE_DOWN) {
            sortSql="min(d.price) desc";
        } else {
            sortSql="p.prominence desc";
        }

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram\n" +
                    "from products p join productdetails d on p.id = d.productID " +
                                            priceCondition+ "\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                    osCondition + "\n" +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,offset);
            pst.setInt(2,amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        ArrayList<ProductUnit> saledOnlineProductUnits = new ArrayList<>();
        ArrayList<ProductUnit> suggestedProductUnits = new ArrayList<>();
//        saledOnlineProductUnits = ProductUnitDAO.getInstance().selectBySaleProgram(Property.ONLINE,0,20);
//        suggestedProductUnits = ProductUnitDAO.getInstance().selectByCategory(Property.ALL,0,20);
        ArrayList<Integer> priceRange = new ArrayList<>();
        priceRange.add(0);
        priceRange.add(10000000);
        ArrayList<String> osList = new ArrayList<>();
        osList.add("android");
        ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByFilters(osList,priceRange,Property.PROMINENCE,0,20);
//        ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByCategory(1,0,20);
        System.out.println(productUnits.size());


    }

}
