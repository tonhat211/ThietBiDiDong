package controller;

import DAO.AddressDAO;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Address;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userLogging");
        String action = req.getParameter("action");
        action = action.toUpperCase();
        switch (action) {
            case "ADDRESSBOOK" : {
//                ArrayList<Address> addresses = AddressDAO.getInstance().selectByUserID(user.getId());
                ArrayList<Address> addresses = AddressDAO.getInstance().selectByUserID(1);
                String html = renderAddressList(addresses);
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(html);
                break;
            }
            case "INIT" : {

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

    public String renderAddressList(ArrayList<Address> addresses) {
        String re="";
        for(Address a: addresses) {
            re+="<div class=\"address-item\">\n" +
                "      <div class=\"flex-roww\" style=\"justify-content: space-between; align-items: start\">\n" +
                "           <div class=\"address-info\">\n" +
                "                <p><span class=\"receiver\">"+a.getReceiver()+"</span> |   <span class=\"phone\">"+a.getPhone()+"</span></p>\n" +
                "                <p class=\"id\" hidden>"+a.getId()+"</p>\n" +
                "                <p style=\"margin-top: 5px\">Địa chỉ:</p>\n" +
                "                <div>\n" +
                "                     <p class=\"address\" style=\"margin-left: 10px;\"><span class=\"street\">"+a.getStreet()+"</span> - <span class=\"village\">"+a.getVillage()+"</span> - <span class=\"district\">"+a.getDistrict()+"</span> - <span class=\"province\">"+a.getProvince()+"</span></p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"flex-coll update-action\">\n" +
                "                 <i class=\"bi bi-pencil-square btn-edit\" onclick=\"initUpdateAddress(event,'.address-item','#editAddressForm')\"></i>\n" +
                "                 <i class=\"bi bi-trash btn-delete\" onclick=\"initDeleteAddress(event,'.address-item','.modall','.address-confirm')\"></i>\n" +
                "                 <!--                                <i class=\"fa-solid fa-ellipsis editBtn\" onclick=\"showEditAddress.call(this,event)\"></i>-->\n" +
                "            </div>\n" +
                "       </div>\n" +
                "       <div class=\"seperate-horizontal-90\"></div>\n" +
                " </div>";
        }
        return re;
    }

}
