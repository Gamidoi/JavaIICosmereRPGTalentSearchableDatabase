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

@WebServlet(name = "goToCharPage", value = "/goToCharPage")
public class GoToCharPageServlet extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/characterPage.jsp";
        HttpSession userSession = request.getSession();

        try {
            CosmereCharacter character = (CosmereCharacter) userSession.getAttribute("character");
            int characterID = character.getCharacterID();
            ConnectionPoolCharacters charPool = ConnectionPoolCharacters.getInstance();
            Connection conn = charPool.getConnection();

            ArrayList<Integer> talentIDs = Utility.getCharacterTalentIDs(conn, characterID);
            ArrayList<Talent> talentResults = Utility.getTalentsFromArrayList(talentIDs);
            request.setAttribute("talentResults", talentResults);

            charPool.freeConnection(conn);
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.getStackTrace();
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void destroy() {
    }
}
