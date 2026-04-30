package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "switch", value = "/switch")
public class switchCharactersServlet extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/characterPage.jsp";
        String newCharacterID = request.getParameter("newCharacterID");
        HttpSession userSession = request.getSession();
        String userName = userSession.getAttribute("userName").toString();
        ConnectionPoolCharacters pool = ConnectionPoolCharacters.getInstance();
        Connection conn = pool.getConnection();
        try {
            if (newCharacterID.equals("0")) {
                Utility.createNewCharacter(conn, userName, userSession);
            } else {
                Utility.updateUserCurrentCharacter(conn, userName, Integer.parseInt(newCharacterID));
                Utility.readCurrentCharacter(conn, userName, Integer.parseInt(newCharacterID), userSession);
                ArrayList<Integer> talentIDs = Utility.getCharacterTalentIDs(conn, Integer.parseInt(newCharacterID));
                ArrayList<Talent> talentResults = Utility.getTalentsFromArrayList(talentIDs);
                request.setAttribute("talentResults", talentResults);
            }
            Utility.readCharacterNames(conn, userName, userSession);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            String errorMessage = "There was a problem switching to that character";
            request.setAttribute("Message", errorMessage);
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
