package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultSet output = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // not the best security letting the username and password be in the code like this. But, currently that is
            // a user that only has permission to Select from the database, so the vulnerability is minimal.
            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.50.0.128:3306", "readOnly", "BadPassword");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cosmererpgtalentsprojecthapke", "readOnly", "BadPassword");
            output = conn.createStatement().executeQuery("select * from talent;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            message = "Whoops!";
        }

        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        try {
            while(output.next()) {
                out.println("<html><body>");
                out.println("<h1>" + output.getString("TalentName") + "</h1>");
                out.println("<p>" + output.getString("TalentDescription"));
                out.println("<p>" + output.getString("FlavorText"));
                out.println("</body></html>");
            }
        } catch (Exception e){
            e.getStackTrace();
        }
    }

    public void destroy() {
    }
}