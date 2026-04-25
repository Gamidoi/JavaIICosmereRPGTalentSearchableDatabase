package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;


@WebServlet(name = "update", value = "/update")
public class UpdateCharacterServlet extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/characterPage.jsp";
        ConnectionPoolCharacters pool = ConnectionPoolCharacters.getInstance();
        Connection conn = pool.getConnection();
        String sqlQuery;
        HttpSession userSession = request.getSession();
        String userName = userSession.getAttribute("userName").toString();

        CosmereCharacter currentCharacter = (CosmereCharacter) userSession.getAttribute("character");

        int level = currentCharacter.getLevel();
        int maxHP = currentCharacter.getMaxHP();
        int currHP = currentCharacter.getCurrHP();
        int strength = currentCharacter.getStrength();
        int speed = currentCharacter.getSpeed();
        int intellect = currentCharacter.getIntellect();
        int willpower = currentCharacter.getWillpower();
        int awareness = currentCharacter.getAwareness();
        int presence = currentCharacter.getPresence();
        int characterID = currentCharacter.getCharacterID();

        try {level = Integer.parseInt(request.getParameter("level"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {maxHP = Integer.parseInt(request.getParameter("maxHP"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {
            currHP = Integer.parseInt(request.getParameter("currHP"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {
            strength = Integer.parseInt(request.getParameter("strength"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {
            speed = Integer.parseInt(request.getParameter("speed"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {
            intellect = Integer.parseInt(request.getParameter("intellect"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {
            willpower = Integer.parseInt(request.getParameter("willpower"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {
            awareness = Integer.parseInt(request.getParameter("awareness"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        } try {
            presence = Integer.parseInt(request.getParameter("presence"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        CosmereCharacter character = new CosmereCharacter(
            request.getParameter("name"), characterID,
            userName, level, maxHP, currHP, strength, speed,
            intellect, willpower, awareness, presence
        );

        userSession.setAttribute("character", character);
        sqlQuery = character.toSQLUpdate();
        if (characterID != 0) {
            try {
                conn.createStatement().executeUpdate(sqlQuery);
                Utility.readCharacterNames(conn, userName, userSession);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.getStackTrace();
            }
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
