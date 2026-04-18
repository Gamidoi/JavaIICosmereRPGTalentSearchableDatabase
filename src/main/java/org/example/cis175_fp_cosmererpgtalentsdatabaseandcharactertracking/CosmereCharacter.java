package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

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

    public CosmereCharacter(){
        name = "";
        level = 1;
        maxHP = currHP = strength = speed = intellect = willpower = awareness = presence = 0;
    }
    public CosmereCharacter(ResultSet output) throws SQLException{
        parseSQLResult(output);
    }
    public CosmereCharacter(String newName, int newCharacterID, String newUserName, int newLevel, int newMaxHP, int newCurrHP, int newStrength, int newSpeed,
                            int newIntellect, int newWillpower, int newAwareness, int newPresence){
        name = newName;
        characterID = newCharacterID;
        userName = newUserName;
        level = newLevel;
        maxHP = newMaxHP;
        currHP = newCurrHP;
        strength = newStrength;
        speed = newSpeed;
        intellect = newIntellect;
        willpower = newWillpower;
        awareness = newAwareness;
        presence = newPresence;
    }

    public void parseSQLResult(ResultSet output) throws SQLException {
        characterID = output.getInt("characterID");
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
    }

    public String toSQLUpdate(){
        StringBuilder output = new StringBuilder("update characters set name = \"");
        output.append(name);
        output.append("\", userName = \"");
        output.append(userName);
        output.append("\", level = ");
        output.append(level);
        output.append(", maxHP = ");
        output.append(maxHP);
        output.append(", currHP = ");
        output.append(currHP);
        output.append(", strength = ");
        output.append(strength);
        output.append(", speed = ");
        output.append(speed);
        output.append(", intellect = ");
        output.append(intellect);
        output.append(", willpower = ");
        output.append(willpower);
        output.append(", awareness = ");
        output.append(awareness);
        output.append(", presence = ");
        output.append(presence);
        output.append(" where characterID = ");
        output.append(characterID);
        System.out.println(output);
        return (output.toString());
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

    public void setPresence(int presence) {this.presence = presence;}
    public void setAwareness(int awareness) {this.awareness = awareness;}
    public void setWillpower(int willpower) {this.willpower = willpower;}
    public void setIntellect(int intellect) {this.intellect = intellect;}
    public void setSpeed(int speed) {this.speed = speed;}
    public void setStrength(int strength) {this.strength = strength;}
    public void setCurrHP(int currHP) {this.currHP = currHP;}
    public void setMaxHP(int maxHP) {this.maxHP = maxHP;}
    public void setName(String name) {this.name = name;}
    public void setCharacterID(int characterID) {this.characterID = characterID;}
    public void setLevel(int level) {this.level = level;}
}
