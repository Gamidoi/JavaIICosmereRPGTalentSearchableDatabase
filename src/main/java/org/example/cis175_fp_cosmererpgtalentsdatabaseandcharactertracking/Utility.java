package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    protected static void createNewCharacter(Connection conn, String userName, HttpSession userSession) throws SQLException {
        StringBuilder sqlQuery;
        ResultSet output;
        CosmereCharacter character = new CosmereCharacter();
        character.setUserName(userName);
        conn.createStatement().executeUpdate(character.toSQLInsert());

        sqlQuery = new StringBuilder("select characterID from characters where userName = \"");
        sqlQuery.append(userName);
        sqlQuery.append("\" order by characterID DESC");
        output = conn.createStatement().executeQuery(sqlQuery.toString());
        output.next();
        int characterID = output.getInt("characterID");
        character.setCharacterID(characterID);
        userSession.setAttribute("character", character);

        sqlQuery = new StringBuilder("update users set currentCharacter = ");
        sqlQuery.append(characterID);
        sqlQuery.append(" where name = \"");
        sqlQuery.append(userName);
        sqlQuery.append("\"");
        conn.createStatement().executeUpdate(sqlQuery.toString());
    }

    protected static void readCharacterNames(Connection conn, String userName, HttpSession userSession) throws SQLException {
        Map<String, Integer> characterMap = new HashMap<>();
        StringBuilder sqlQuery = new StringBuilder("select name, characterID from characters where userName = \"");
        sqlQuery.append(userName);
        sqlQuery.append('\"');
        ResultSet output;
        output = conn.createStatement().executeQuery(sqlQuery.toString());
        while (output.next()){
            characterMap.put(output.getString("name"), output.getInt("characterID"));
        }
        userSession.setAttribute("allUserCharacters", characterMap);
    }


    protected static void readCurrentCharacter(Connection conn,String userName, int characterID, HttpSession userSession) throws SQLException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("select * from characters where characterID = ");
        sqlQuery.append(characterID);
        ResultSet CharacterOutput = conn.createStatement().executeQuery(sqlQuery.toString());
        CharacterOutput.next();
        CosmereCharacter character = new CosmereCharacter(CharacterOutput);
        String characterOwner = character.getUserName();

        // this if statement makes sure that users only access their own characters.
        if (characterOwner.equals(userName)) {
            userSession.setAttribute("character", character);
        }
    }
}
