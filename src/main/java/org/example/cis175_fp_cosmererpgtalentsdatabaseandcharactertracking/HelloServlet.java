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
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        String url = "/searchResultsPage.jsp";
        ResultSet output = null;
        String sqlQuery = "SELECT * FROM talent";
        sqlQuery += " left join talentbranch on talent.TalentBranchID = talentbranch.TalentBranchID";
        sqlQuery += " left join talentbranchswitchradiantpath on talentBranch.TalentBranchID = talentbranchswitchradiantpath.TalentBranchID";
        sqlQuery += " left join radiantpath on talentbranchswitchradiantpath.RadiantPathID = radiantpath.RadiantPathID";
        sqlQuery += " left join talentbranchswitchheroicpath on talentBranch.TalentBranchID = talentbranchswitchheroicpath.TalentBranchID";
        sqlQuery += " left join heroicpath on talentbranchswitchheroicpath.HeroicPathID = heroicpath.HeroicPathID";


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
            output = conn.createStatement().executeQuery(sqlQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            message = "Whoops!";
        }

        ArrayList<Talent> talentResults = new ArrayList<>();
        try {
            while(output.next()) {
                boolean repeatEntryForRadiantPath = false;
                Talent eachTalent = new Talent();
                eachTalent.setPrimaryKey(output.getInt("TalentID"));
                eachTalent.setName(output.getString("TalentName"));
                eachTalent.setDescription(output.getString("TalentDescription"));
                eachTalent.setFlavorText(output.getString("FlavorText"));
                eachTalent.setActionType(output.getString("ActionType"));
                eachTalent.setBranch(output.getString("BranchName"));
                eachTalent.setActionCost(output.getInt("ActionCost"));
                eachTalent.setFocusCost(output.getInt("FocusCost"));
                eachTalent.setInvestitureCost(output.getInt("InvestitureCost"));

                String radiantPathName = output.getString("RadiantOrderName");
                int radiantPathID = output.getInt("RadiantPathID");
                String heroicPathName = output.getString("HeroicPathName");
                int heroicPathID = output.getInt("heroicPathID");
                if (radiantPathName != null) {
                    if (talentResults.getLast().getPrimaryKey() == eachTalent.getPrimaryKey()) {
                        talentResults.getLast().setRadiantPath2(radiantPathName);
                        talentResults.getLast().setRadiantPath2ID(radiantPathID);
                        repeatEntryForRadiantPath = true;
                    } else {
                        eachTalent.setRadiantPath1(radiantPathName);
                        eachTalent.setRadiantPath1ID(radiantPathID);
                    }
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
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void destroy() {
    }
}