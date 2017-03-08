/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.io.IOException;
import java.io.PrintWriter;
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
 * @author KamalKant
 */
@WebServlet(name = "ContactUs", urlPatterns = {"/contactUs"})
public class ContactUs extends HttpServlet {

    static Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    String name = null;
    String email = null;
    String subject = null;
    String message = null;
    int i = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        //connection from database
        try {
            con = connection.dbConnection.makeConnection();
        } catch (Exception e) {
        }

        String feedbackID = request.getParameter("id");

        try {
            String sqlquery = "delete from contact_us where id='" + feedbackID + "'";
            pst = con.prepareStatement(sqlquery);
            i = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();;
        }

        //success or failure message
        if (i > 0) {
            session.setAttribute("MSG", "Contact has been deleted.");
            response.sendRedirect("adfeedback-list.jsp");
        } else {
            session.setAttribute("MSG", "Contact has not been deleted.");
            response.sendRedirect("adfeedback-list.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        //connection from database
        try {
            con = connection.dbConnection.makeConnection();
        } catch (Exception e) {
        }

        name = request.getParameter("name");
        email = request.getParameter("email");
        subject = request.getParameter("subject");
        message = request.getParameter("message");

        try {
            String sqlquery = "INSERT INTO contact_us(name,email,subjetcs,message,adding_date) VALUES (?,?,?,?,NOW())";
            pst = con.prepareStatement(sqlquery);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, subject);
            pst.setString(4, message);
            i = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //success or failure message
        if (i > 0) {
            session.setAttribute("MSG", "Thank you for your time. I will contact you soon.");
            response.sendRedirect("contact-us.jsp");
        } else {
            session.setAttribute("MSG", "Sorry! Your information has not been submitted.");
            response.sendRedirect("contact-us.jsp");
        }

    }
}
