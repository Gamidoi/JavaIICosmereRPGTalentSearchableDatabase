package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
