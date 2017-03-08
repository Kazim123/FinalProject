/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kamal Kant
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    static Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    String name = null;
    String uname = null;
    String password = null;
    String result = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        uname = request.getParameter("email");
        password = request.getParameter("password");
        try {
            con = connection.dbConnection.makeConnection();
            String query = "SELECT id,name,utype FROM users WHERE email = '" + uname + "' AND password = '" + password + "' AND ustatus ='1'";
            pst = con.prepareStatement(query);
            rst = pst.executeQuery();
            if (rst.next()) {

                session.setAttribute("ID", rst.getString(1));
                session.setAttribute("UNAME", uname);
                session.setAttribute("NAME", rst.getString(2));
                session.setAttribute("UTYPE", rst.getString(3));

                response.sendRedirect("myaccount.jsp");
            } else {
                session.setAttribute("MSG", "User name and password are wrong.!Retry Again");
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            session.setAttribute("MSG", "User name and password are wrong.!Retry Again");
            response.sendRedirect("login.jsp");
        }
    }
}
