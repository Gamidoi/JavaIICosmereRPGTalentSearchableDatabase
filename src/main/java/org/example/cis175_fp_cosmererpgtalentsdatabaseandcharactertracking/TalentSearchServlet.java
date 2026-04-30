package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet(name = "search", value = "/search")
public class TalentSearchServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionPoolTalents pool = ConnectionPoolTalents.getInstance();
        Connection conn = pool.getConnection();
        String url = "/searchResultsPage.jsp";
        ResultSet output = null;
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM talent");
        sqlQuery.append(" left join talentbranch on talent.TalentBranchID = talentbranch.TalentBranchID");
        sqlQuery.append(" left join talentbranchswitchradiantpath on talentBranch.TalentBranchID = talentbranchswitchradiantpath.TalentBranchID");
        sqlQuery.append(" left join radiantpath on talentbranchswitchradiantpath.RadiantPathID = radiantpath.RadiantPathID");
        sqlQuery.append(" left join talentbranchswitchheroicpath on talentBranch.TalentBranchID = talentbranchswitchheroicpath.TalentBranchID");
        sqlQuery.append(" left join heroicpath on talentbranchswitchheroicpath.HeroicPathID = heroicpath.HeroicPathID");


        String descriptionSearch = request.getParameter("descriptionSearch");
        request.setAttribute("descriptionSearch", descriptionSearch);
        String nameSearch = request.getParameter("nameSearch");
        request.setAttribute("nameSearch", nameSearch);
        String flavorSearch = request.getParameter("flavorSearch");
        request.setAttribute("flavorSearch", flavorSearch);
        String sortBy = request.getParameter("sortBy");
        request.setAttribute("sortBy", sortBy);
        boolean hasOrderedSearch = (!(sortBy == null) && !sortBy.isEmpty());

        boolean hasWhereKeyword = false;
        if (descriptionSearch != null && !descriptionSearch.isEmpty()){
            sqlQuery.append(" WHERE TalentDescription LIKE \"%");
            sqlQuery.append(descriptionSearch);
            sqlQuery.append("%\"");
            hasWhereKeyword= true;
        }
        if (nameSearch != null && !nameSearch.isEmpty()){
            if (!hasWhereKeyword){
                sqlQuery.append(" WHERE ");
                hasWhereKeyword = true;
            } else {
                sqlQuery.append(" AND ");
            }
            sqlQuery.append("TalentName LIKE \"%").append(nameSearch).append("%\"");
        }
        if (flavorSearch != null && !flavorSearch.isEmpty()){
            if (!hasWhereKeyword){
                sqlQuery.append(" WHERE ");
                hasWhereKeyword = true;
            } else {
                sqlQuery.append(" AND ");
            }
            sqlQuery.append(" FlavorText LIKE \"%").append(flavorSearch).append("%\"");
        }
        if (sortBy != null && !sortBy.isEmpty()){
            sqlQuery.append(" ORDER BY ISNULL(").append(sortBy).append("), ").append(sortBy);
        }
        if (sortBy != null && sortBy.contains("Cost")){
            sqlQuery.append(" DESC");
        }

        try {
            output = conn.createStatement().executeQuery(sqlQuery.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            message = "There was a problem reaching the database, your search could not be completed.";
        }

        ArrayList<Talent> talentResults = new ArrayList<>();
        try {
            while(output.next()) {
                boolean repeatEntryForRadiantPath = false;
                Talent eachTalent = new Talent(output);

                String radiantPathName = output.getString("RadiantOrderName");
                int radiantPathID = output.getInt("RadiantPathID");
                String heroicPathName = output.getString("HeroicPathName");
                int heroicPathID = output.getInt("heroicPathID");
                if (radiantPathName != null && !hasOrderedSearch) {
                    if (talentResults.getLast().getPrimaryKey() == eachTalent.getPrimaryKey()) {
                        talentResults.getLast().setRadiantPath2(radiantPathName);
                        talentResults.getLast().setRadiantPath2ID(radiantPathID);
                        repeatEntryForRadiantPath = true;
                    } else {
                        eachTalent.setRadiantPath1(radiantPathName);
                        eachTalent.setRadiantPath1ID(radiantPathID);
                    }
                } else {
                    eachTalent.setRadiantPath1(radiantPathName);
                    eachTalent.setRadiantPath1ID(radiantPathID);
                }
                if (repeatEntryForRadiantPath){continue;}
                if (heroicPathName != null){
                    eachTalent.setPath(heroicPathName);
                    eachTalent.setPathID(heroicPathID);
                }

                talentResults.add(eachTalent);
            }
        } catch (Exception e){
            e.getStackTrace();
        }

        request.setAttribute("talentResults", talentResults);
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