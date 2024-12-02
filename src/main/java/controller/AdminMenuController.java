package controller;

import DAO.BrandDAO;
import DAO.OrderDAO;
import DAO.ProductUnitDAO;
import DAO.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/adminmenu")
public class AdminMenuController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");

        String action = req.getParameter("action");
//        action = "ADMINORDER";
        action = action.toUpperCase();
        System.out.println("Admin menu");
        switch (action) {
            case "INIT": {
                System.out.println("menu init");
                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitByStatus(-1,0,Constant.NUM_OF_ITEMS_A_PAGE);
                int numOfOrders = OrderDAO.getInstance().selectCountOrderUnitBy(-1);
                Integer numOfPages = numOfOrders / Constant.NUM_OF_ITEMS_A_PAGE;
                if(numOfOrders % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;
                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("orderUnits", orderUnits);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;

            }
            case "ADMINORDER": {
                System.out.println("menu admin order");
                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitByStatus(-1,0,Constant.NUM_OF_ITEMS_A_PAGE);
                int numOfOrders = OrderDAO.getInstance().selectCountOrderUnitBy(-1);
                Integer numOfPages = numOfOrders / Constant.NUM_OF_ITEMS_A_PAGE;
                if(numOfOrders % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;
                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("orderUnits", orderUnits);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "ADMINPRODUCT": {
                System.out.println("menu admin product");
                ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByCategoryForAdmin(Constant.SMARTPHONE_CATEGORY,0,Constant.NUM_OF_ITEMS_A_PAGE);
                int numOfProducts = ProductUnitDAO.getInstance().selectCountByCategory(Constant.SMARTPHONE_CATEGORY);
                Integer numOfPages = numOfProducts / Constant.NUM_OF_ITEMS_A_PAGE;
                if(numOfProducts % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;


                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("productUnits", productUnits);
                String message = req.getParameter("message");
                if(message != null) {
                    if(message.contains("updateSuccess"))
                        message = "Cập nhật sản phẩm id: "+ message.split("_")[1] +" thành công";
                    else if(message.contains("deleteSuccess")) message = "Xóa sản phẩm id: "+ message.split("_")[1] +" thành công";
                    else if(message.contains("addSuccess")) message = "Thêm sản phẩm thành công";
                    req.setAttribute("message", message);
                }



                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminProduct.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "ADMINCUSTOMER": {
                System.out.println("menu admin customer");
                ArrayList<User> customers = UserDAO.getInstance().selectCustomers();
//                int numOfProducts = ProductUnitDAO.getInstance().selectCountByCategory(Constant.SMARTPHONE_CATEGORY);
//                Integer numOfPages = numOfProducts / Constant.NUM_OF_ITEMS_A_PAGE;
//                if(numOfProducts % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;


//                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("customers", customers);
                String message = req.getParameter("message");
                if(message != null) {
                    if(message.contains("updateSuccess"))
                        message = "Cập nhật sản phẩm id: "+ message.split("_")[1] +" thành công";
                    else if(message.contains("deleteSuccess")) message = "Xóa sản phẩm id: "+ message.split("_")[1] +" thành công";
                    else if(message.contains("addSuccess")) message = "Thêm sản phẩm thành công";
                    req.setAttribute("message", message);
                }



                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminCustomer.jsp");
                dispatcher.forward(req, resp);
                break;
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }



}
