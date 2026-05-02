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
import java.util.ArrayList;

@WebServlet(name = "removeTalent", value = "/removeTalent")
public class RemoveTalentFromCurrentCharacterServlet extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/characterPage.jsp";
        ConnectionPoolCharacters pool = ConnectionPoolCharacters.getInstance();
        Connection conn = pool.getConnection();
        HttpSession userSession = request.getSession();
        CosmereCharacter character = (CosmereCharacter) userSession.getAttribute("character");
        int characterID = character.getCharacterID();
        int talentID = Integer.parseInt(request.getParameter("removeTalentIDFromCharacter"));
        try{
            PreparedStatement sqlQuery = conn.prepareStatement("delete from characterTalents where characterID = ? and talentID = ?");
            sqlQuery.setInt(1, characterID);
            sqlQuery.setInt(2, talentID);
            sqlQuery.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        try{
            ArrayList<Integer> talentIDs = Utility.getCharacterTalentIDs(conn, characterID);
            ArrayList<Talent> talentResults = Utility.getTalentsFromArrayList(talentIDs);
            request.setAttribute("talentResults", talentResults);
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.getStackTrace();
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
