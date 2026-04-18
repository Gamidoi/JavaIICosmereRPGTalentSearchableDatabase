package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

@WebServlet(name = "signIn", value = "/signIn")
public class SignInServlet  extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/SignInSignUp.jsp";
        ConnectionPoolCharacters pool = ConnectionPoolCharacters.getInstance();
        Connection conn = pool.getConnection();
        ResultSet output = null;
        StringBuilder sqlQuery = new StringBuilder();
        String userName = request.getParameter("userName");
        String password = request.getParameter("userPassword");
        sqlQuery.append("select * from users where name = \"");
        sqlQuery.append(userName);
        sqlQuery.append("\"");

        try {
            output = conn.createStatement().executeQuery(sqlQuery.toString());
            output.next();

            if (output.getString("password").equals(password)) {
                url = "/characterPage.jsp";
                HttpSession userSession = request.getSession();
                userSession.setAttribute("userName", userName);
                if (output.getInt("currentCharacter") != 0){
                    sqlQuery = new StringBuilder();
                    sqlQuery.append("select * from characters where characterID = ");
                    sqlQuery.append(output.getInt("currentCharacter"));
                    ResultSet CharacterOutput = conn.createStatement().executeQuery(sqlQuery.toString());
                    CharacterOutput.next();
                    CosmereCharacter character = new CosmereCharacter(CharacterOutput);
                    userSession.setAttribute("character", character);
                }
            } else {
                String errorMessage = "The password was incorrect.";
                request.setAttribute("signInMessage", errorMessage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            String errorMessage = "There was a problem signing in";
            request.setAttribute("signInMessage", errorMessage);
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
