package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    protected static void createNewCharacter(Connection conn, String userName, HttpSession userSession) throws SQLException {
        PreparedStatement sqlQuery = conn.prepareStatement("select characterID from characters where userName = ? order by characterID DESC");
        ResultSet output;
        CosmereCharacter character = new CosmereCharacter();
        character.setUserName(userName);
        PreparedStatement SQLInsert = character.toSQLInsert(conn);
        SQLInsert.executeUpdate();

        sqlQuery.setString(1, userName);
        output = sqlQuery.executeQuery();
        output.next();
        int characterID = output.getInt("characterID");
        character.setCharacterID(characterID);
        userSession.setAttribute("character", character);

        sqlQuery = conn.prepareStatement("update users set currentCharacter = ? where name = ?");
        sqlQuery.setInt(1, characterID);
        sqlQuery.setString(2, userName);
        sqlQuery.executeUpdate();
    }
    protected static void updateUserCurrentCharacter(Connection conn, String userName, int characterID) throws SQLException {
        PreparedStatement sqlQuery = conn.prepareStatement("update users set currentCharacter = ? where name = ?");
        sqlQuery.setInt(1, characterID);
        sqlQuery.setString(2, userName);
        sqlQuery.executeUpdate();
    }

    protected static void readCharacterNames(Connection conn, String userName, HttpSession userSession) throws SQLException {
        Map<String, Integer> characterMap = new HashMap<>();
        PreparedStatement sqlQuery = conn.prepareStatement("select name, characterID from characters where userName = ?");
        sqlQuery.setString(1, userName);
        ResultSet output;
        output = sqlQuery.executeQuery();
        while (output.next()){
            characterMap.put(output.getString("name"), output.getInt("characterID"));
        }
        userSession.setAttribute("allUserCharacters", characterMap);
    }


    protected static void readCurrentCharacter(Connection conn,String userName, int characterID, HttpSession userSession) throws SQLException {
        PreparedStatement sqlQuery = conn.prepareStatement("select * from characters where characterID = ?");
        sqlQuery.setInt(1, characterID);
        ResultSet CharacterOutput = sqlQuery.executeQuery();
        CharacterOutput.next();
        CosmereCharacter character = new CosmereCharacter(CharacterOutput);
        String characterOwner = character.getUserName();

        // this if statement makes sure that users only access their own characters.
        if (characterOwner.equals(userName)) {
            userSession.setAttribute("character", character);
        }
    }

    protected static ArrayList<Integer> getCharacterTalentIDs(Connection conn, int characterID) throws SQLException {
        ArrayList<Integer> talentIDs = new ArrayList<>();
        PreparedStatement sqlQuery = conn.prepareStatement("select talentID from characterTalents where characterID = ?");
        sqlQuery.setInt(1, characterID);
        ResultSet output = sqlQuery.executeQuery();
        while (output.next()){
            talentIDs.add(output.getInt("talentID"));
        }
        return talentIDs;
    }

    protected static ArrayList<Talent> getTalentsFromArrayList(ArrayList<Integer> talentIDs) throws SQLException {
        ConnectionPoolTalents pool = ConnectionPoolTalents.getInstance();
        Connection conn = pool.getConnection();
        ArrayList<Talent> talentResults = new ArrayList<>();
        StringBuilder talentIDsString = new StringBuilder("(-1");
        for (int i = 0; i < talentIDs.size(); i++){
            talentIDsString.append(", ");
            talentIDsString.append(talentIDs.get(i));
        }
        talentIDsString.append(')');
        PreparedStatement sqlQuery = conn.prepareStatement(
                "select * from talent " +
                    " left join talentbranch on talent.TalentBranchID = talentbranch.TalentBranchID" +
                    " left join talentbranchswitchradiantpath on talentBranch.TalentBranchID = talentbranchswitchradiantpath.TalentBranchID" +
                    " left join radiantpath on talentbranchswitchradiantpath.RadiantPathID = radiantpath.RadiantPathID" +
                    " left join talentbranchswitchheroicpath on talentBranch.TalentBranchID = talentbranchswitchheroicpath.TalentBranchID" +
                    " left join heroicpath on talentbranchswitchheroicpath.HeroicPathID = heroicpath.HeroicPathID" +
                    " where talentID in " + talentIDsString.toString());

        ResultSet talents = sqlQuery.executeQuery();
        while (talents.next()){
            Talent newTalent = new Talent(talents);
            talentResults.add(newTalent);
        }
        pool.freeConnection(conn);
        return talentResults;
    }
}
