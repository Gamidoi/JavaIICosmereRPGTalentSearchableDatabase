package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CosmereCharacter {
    private int characterID;
    private String name;
    private String userName;
    private int level;
    private int maxHP;
    private int currHP;
    private int strength;
    private int speed;
    private int intellect;
    private int willpower;
    private int awareness;
    private int presence;
    private String inventory;

    public CosmereCharacter(){
        name = "default name";
        userName = inventory =  "";
        level = 1;
        characterID = maxHP = currHP = strength = speed = intellect = willpower = awareness = presence = 0;
    }
    public CosmereCharacter(ResultSet output) throws SQLException{
        parseSQLResult(output);
    }
    public CosmereCharacter(String newName, int newCharacterID, String newUserName, int newLevel, int newMaxHP, int newCurrHP, int newStrength, int newSpeed,
                            int newIntellect, int newWillpower, int newAwareness, int newPresence, String newInventory){
        name = newName;
        if (name.length() > 30){name = name.substring(0, 30);}
        characterID = newCharacterID;
        userName = newUserName;
        inventory = newInventory;
        level = newLevel;
        if (level > 30){level = 30;}
        maxHP = newMaxHP;
        if (maxHP > 500){maxHP = 500;}
        currHP = newCurrHP;
        if (currHP > 500 || currHP > maxHP){currHP = maxHP;}
        strength = newStrength;
        if (strength > 15){strength = 15;}
        speed = newSpeed;
        if (speed > 15){speed = 15;}
        intellect = newIntellect;
        if (intellect > 15){intellect = 15;}
        willpower = newWillpower;
        if (willpower > 15){willpower = 15;}
        awareness = newAwareness;
        if (awareness > 15){awareness = 15;}
        presence = newPresence;
        if (presence > 15){presence = 15;}
    }

    public void parseSQLResult(ResultSet output) throws SQLException {
        characterID = output.getInt("characterID");
        userName = output.getString("userName");
        name = output.getString("name");
        level = output.getInt("level");
        maxHP = output.getInt("maxHP");
        currHP = output.getInt("currHP");
        strength = output.getInt("strength");
        speed = output.getInt("speed");
        intellect = output.getInt("intellect");
        willpower = output.getInt("willpower");
        awareness = output.getInt("awareness");
        presence = output.getInt("presence");
        inventory = output.getString("inventory");
    }

    public PreparedStatement toSQLUpdate(Connection conn){
        PreparedStatement output;
        try{
            output = conn.prepareStatement(
                    "update characters set name = ?, userName = ?, " +
                        "inventory = ?, level = ?, maxHP = ?, currHP = ?, " +
                        "strength = ?, speed = ?, intellect = ?, " +
                        "willpower = ?, awareness = ?, presence = ? " +
                        "where characterID = ?");

            output.setString(1, name);
            output.setString(2, userName);
            output.setString(3, inventory);
            output.setInt(4, level);
            output.setInt(5, maxHP);
            output.setInt(6, currHP);
            output.setInt(7, strength);
            output.setInt(8, speed);
            output.setInt(9, intellect);
            output.setInt(10, willpower);
            output.setInt(11, awareness);
            output.setInt(12, presence);
            output.setInt(13, characterID);
            return (output);
        } catch (Exception e){
            return null;
        }
    }
    public PreparedStatement toSQLInsert(Connection conn){
        PreparedStatement output;
        try {
            output = conn.prepareStatement(
                    "insert into characters (name, userName, inventory, level, maxHP, currHP, strength, speed, intellect, willpower, awareness, presence) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?)");


            output.setString(1, name);
            output.setString(2, userName);
            output.setString(3, inventory);
            output.setInt(4, level);
            output.setInt(5, maxHP);
            output.setInt(6, currHP);
            output.setInt(7, strength);
            output.setInt(8, speed);
            output.setInt(9, intellect);
            output.setInt(10, willpower);
            output.setInt(11, awareness);
            output.setInt(12, presence);
            return (output);
    } catch (Exception e) {
            return null;
        }
    }

    public String toSQLDelete(){
        return ("delete from characters where characterID = " + characterID);
    }

    public int getPresence() {return presence;}
    public int getAwareness() {return awareness;}
    public int getWillpower() {return willpower;}
    public int getIntellect() {return intellect;}
    public int getSpeed() {return speed;}
    public int getStrength() {return strength;}
    public int getCurrHP() {return currHP;}
    public int getMaxHP() {return maxHP;}
    public String getName() {return name;}
    public int getCharacterID() {return characterID;}
    public int getLevel() {return level;}
    public String getUserName() {return userName;}
    public String getInventory() {return inventory;}

    public void setPresence(int presence) {this.presence = Math.min(presence, 15);}
    public void setAwareness(int awareness) {this.awareness = Math.min(awareness, 15);}
    public void setWillpower(int willpower) {this.willpower = Math.min(willpower, 15);}
    public void setIntellect(int intellect) {this.intellect = Math.min(intellect, 15);}
    public void setSpeed(int speed) {this.speed = Math.min(speed, 15);}
    public void setStrength(int strength) {this.strength = Math.min(strength, 15);}
    public void setCurrHP(int currHP) {this.currHP = Math.min(currHP, maxHP);}
    public void setMaxHP(int maxHP) {this.maxHP = Math.min(maxHP, 500);}
    public void setName(String name) {this.name = name;}
    public void setCharacterID(int characterID) {this.characterID = characterID;}
    public void setLevel(int level) {this.level = Math.min(level, 30);}
    public void setUserName(String userName) {this.userName = userName;}
    public void setInventory(String inventory) {this.inventory = inventory;}
}
