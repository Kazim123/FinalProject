/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kamal Kant
 */
@WebServlet(name = "MyAccount", urlPatterns = {"/myAccount"})
public class MyAccount extends HttpServlet {

    static Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    String name = null;
    String dob = null;
    String gender = null;
    String email = null;
    String contno = null;
    String address = null;
    String city = null;
    String zipcode = null;
    String state = null;
    String country = null;
    String id = null;
    int i = 0;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        ServletConfig config = getServletConfig();
        String context = config.getServletContext().getRealPath("/");

        //connection from database
        try {
            con = connection.dbConnection.makeConnection();
        } catch (Exception e) {
        }

        id = request.getParameter("hidid");
        name = request.getParameter("txtname");
        gender = request.getParameter("cmbgender");
        dob = request.getParameter("txtdob");
        email = request.getParameter("txtemail");
        contno = request.getParameter("txtcontno");
        address = request.getParameter("txtaddress");
        city = request.getParameter("txtcity");
        zipcode = request.getParameter("txtzipcode");
        state = request.getParameter("txtstate");
        country = request.getParameter("txtcountry");
        

        try {
            String sqlquery = "update registers set name=?,gender=?,dob=?,email=?,contact_no=?,address=?,city=?,zipcode=?,state=?,country=? where id = '" + id + "'";
            pst = con.prepareStatement(sqlquery);
            pst.setString(1, name);
            pst.setString(2, gender);
            pst.setString(3, dob);
            pst.setString(4, email);
            pst.setString(5, contno);
            pst.setString(6, address);
            pst.setString(7, city);
            pst.setString(8, zipcode);
            pst.setString(9, state);
            pst.setString(10, country);

            i = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //success or failure message
        if (i > 0) {

            session.setAttribute("MSG", "Your profile has been successfully update.");
            response.sendRedirect("myaccount.jsp");
        } else {
            session.setAttribute("MSG", "Your profile has not been update.");
            response.sendRedirect("myaccount.jsp");
        }


    }
}
