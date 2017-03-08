/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
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
@WebServlet(name = "Registration", urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

    static Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    String name = null;
    String email = null;
    String pwd = null;
    String contno = null;
    String gender = null;   
    int i = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);

        try {
            con = connection.dbConnection.makeConnection();
            String query = "delete from users WHERE id= '" + id + "' ";
            pst = con.prepareStatement(query);
            i = pst.executeUpdate();

        } catch (Exception e) {
        }

        if (i > 0) {
            session.setAttribute("MSG", "User has been successfuly deleted !!");
            response.sendRedirect("aduser-list.jsp");
        } else {
            session.setAttribute("MSG", "User has not been deleted !!");
            response.sendRedirect("aduser-list.jsp");
        }
    }

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


        name = request.getParameter("name");
        email = request.getParameter("email");
        pwd = request.getParameter("password");
        contno = request.getParameter("mobile");
        gender = request.getParameter("gender");
        

        try {
            String sqlquery = "INSERT INTO users(name,email,password,mobile,gender,created) VALUES(?,?,?,?,?,NOW())";
            pst = con.prepareStatement(sqlquery);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, pwd);
            pst.setString(4, contno);
            pst.setString(5, gender);            
            i = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();;
        }

        //success or failure message
        if (i > 0) {

            session.setAttribute("MSG", "Your register form has been successfully submited.");
            response.sendRedirect("register.jsp");
        } else {
            session.setAttribute("MSG", "Your register form has not been submited.");
            response.sendRedirect("register.jsp");
        }


    }
}
