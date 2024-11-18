package controller;

import DAO.OrderDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constant;
import model.OrderUnit;
import model.User;

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
        action = "ADMINORDER";
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
