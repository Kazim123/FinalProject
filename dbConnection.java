/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {

    static Connection con = null;

    public static Connection makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();//
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ddbacademic", "root", "");

        } catch (Exception e) {
        }
        return con;
    }
}
