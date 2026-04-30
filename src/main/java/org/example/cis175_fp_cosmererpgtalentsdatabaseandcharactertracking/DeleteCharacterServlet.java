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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "delete", value = "/delete")
public class DeleteCharacterServlet extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/characterPage.jsp";
        HttpSession userSession = request.getSession();
        ConnectionPoolCharacters pool = ConnectionPoolCharacters.getInstance();
        Connection conn = pool.getConnection();
        CosmereCharacter character = (CosmereCharacter) userSession.getAttribute("character");
        String userName = userSession.getAttribute("userName").toString();
        try {
            conn.createStatement().executeUpdate(character.toSQLDelete());
            Utility.readCharacterNames(conn, userName, userSession);
            Map allCharacterNames = (HashMap) userSession.getAttribute("allUserCharacters");
            if (allCharacterNames.isEmpty()){
                Utility.createNewCharacter(conn, userName, userSession);
            } else {
                int characterID = (Integer) allCharacterNames.values().iterator().next();
                Utility.readCurrentCharacter(conn, userName, characterID, userSession);
                Utility.updateUserCurrentCharacter(conn, userName, characterID);
                ArrayList<Integer> talentIDs = Utility.getCharacterTalentIDs(conn, characterID);
                ArrayList<Talent> talentResults = Utility.getTalentsFromArrayList(talentIDs);
                request.setAttribute("talentResults", talentResults);
            }
            Utility.readCharacterNames(conn, userName, userSession);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            String errorMessage = "There was a problem deleting that character";
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
