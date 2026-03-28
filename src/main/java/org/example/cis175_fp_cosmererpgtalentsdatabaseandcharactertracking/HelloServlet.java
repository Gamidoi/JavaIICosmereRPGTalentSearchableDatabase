package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet(name = "search", value = "/search")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/searchResultsPage.jsp";
        ResultSet output = null;
        String sqlQuery = "SELECT * FROM talent";

        String descriptionSearch = request.getParameter("descriptionSearch");
        String nameSearch = request.getParameter("nameSearch");
        String flavorSearch = request.getParameter("flavorSearch");

        boolean hasWhereKeyword = false;
        if (descriptionSearch != null && !descriptionSearch.isEmpty()){
            sqlQuery += " WHERE TalentDescription LIKE \"%" + descriptionSearch + "%\"";
            hasWhereKeyword= true;
        }
        if (nameSearch != null && !nameSearch.isEmpty()){
            if (!hasWhereKeyword){
                sqlQuery += " WHERE ";
                hasWhereKeyword = true;
            } else {
                sqlQuery += " AND ";
            }
            sqlQuery += "TalentName LIKE \"%" + nameSearch + "%\"";
        }
        if (flavorSearch != null && !flavorSearch.isEmpty()){
            if (!hasWhereKeyword){
                sqlQuery += " WHERE ";
                hasWhereKeyword = true;
            } else {
                sqlQuery += " AND ";
            }
            sqlQuery += " FlavorText LIKE \"%" + flavorSearch + "%\"";
        }
        System.out.println(sqlQuery);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // not the best security letting the username and password be in the code like this. But, currently that is
            // a user that only has permission to Select from the database, so the vulnerability is minimal.
            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.50.0.128:3306", "readOnly", "BadPassword");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cosmererpgtalentsprojecthapke", "readOnly", "BadPassword");
            output = conn.createStatement().executeQuery(sqlQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            message = "Whoops!";
        }

        ArrayList<Talent> talentResults = new ArrayList<>();
        try {
            while(output.next()) {
                Talent eachTalent = new Talent();
                eachTalent.setName(output.getString("TalentName"));
                eachTalent.setDescription(output.getString("TalentDescription"));
                eachTalent.setFlavorText(output.getString("FlavorText"));
                talentResults.add(eachTalent);
            }
        } catch (Exception e){
            e.getStackTrace();
        }

        request.setAttribute("talentResults", talentResults);

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void destroy() {
    }
}