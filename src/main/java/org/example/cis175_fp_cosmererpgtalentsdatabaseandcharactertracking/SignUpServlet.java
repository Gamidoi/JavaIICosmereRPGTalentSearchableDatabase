package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "signUp", value = "/signUp")
public class SignUpServlet  extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/SignInSignUp.jsp";
        ConnectionPoolCharacters pool = ConnectionPoolCharacters.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement sqlQuery;
        String userName = request.getParameter("userName");
        String password = request.getParameter("userPassword1");
        if (password.equals(request.getParameter("userPassword2"))){
            if (userName.length() <= 30){
                try {
                    sqlQuery = conn.prepareStatement("insert into users (name, password, currentCharacter) values (?, ?, 0)");
                    sqlQuery.setString(1, userName);
                    sqlQuery.setString(2, password);
                    sqlQuery.executeUpdate();
                    url = "/characterPage.jsp";
                    HttpSession userSession = request.getSession();
                    userSession.setAttribute("userName", userName);

                    Utility.createNewCharacter(conn, userName, userSession);
                    Utility.readCharacterNames(conn, userName, userSession);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.getStackTrace();
                    String errorMessage = "There was a problem signing up";
                    request.setAttribute("signUpMessage", errorMessage);
                }
            } else {
                String errorMessage = "User name cannot be more than 30 characters long.";
                request.setAttribute("signUpMessage", errorMessage);
            }
        } else {
            String errorMessage = "Passwords do not Match.";
            request.setAttribute("signUpMessage", errorMessage);
        }



        pool.freeConnection(conn);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void destroy() {
    }
}
