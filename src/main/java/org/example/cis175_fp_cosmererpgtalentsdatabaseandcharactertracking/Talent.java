package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Talent implements Serializable {
    private int primaryKey;
    private String name;
    private String description;
    private String flavorText;
    private String actionType;
    private String branch;
    private int branchID;
    private String path;
    private int pathID;
    private String radiantPath1;
    private String radiantPath2;
    private int radiantPath1ID;
    private int radiantPath2ID;
    private int actionCost;
    private int focusCost;
    private int investitureCost;

    public Talent(){
        name = description = flavorText = "";
    }

    public Talent(ResultSet output) throws SQLException {

        primaryKey = output.getInt("TalentID");
        name = output.getString("TalentName");
        description = output.getString("TalentDescription");
        flavorText = output.getString("FlavorText");
        actionType = output.getString("ActionType");
        branch = output.getString("BranchName");
        branchID = output.getInt("TalentBranchID");
        actionCost = output.getInt("ActionCost");
        focusCost = output.getInt("FocusCost");
        investitureCost = output.getInt("InvestitureCost");

        String radiantPathName = output.getString("RadiantOrderName");
        int radiantPathID = output.getInt("RadiantPathID");
        if (radiantPathName != null) {
            radiantPath1 = radiantPathName;
            radiantPath1ID = radiantPathID;
        }
        String heroicPathName = output.getString("HeroicPathName");
        int heroicPathID = output.getInt("heroicPathID");
        if (heroicPathName != null) {
            path = (heroicPathName);
            pathID = (heroicPathID);
        }
    }

    public int getPrimaryKey(){return primaryKey;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getFlavorText(){return flavorText;}
    public String getActionType(){return actionType;}
    public String getBranch(){return branch;}
    public int getBranchID(){return branchID;}
    public String getPath(){return path;}
    public int getPathID(){return pathID;}
    public String getRadiantPath1(){return radiantPath1;}
    public String getRadiantPath2(){return radiantPath2;}
    public int getRadiantPath1ID(){return radiantPath1ID;}
    public int getRadiantPath2ID(){return radiantPath2ID;}
    public int getActionCost(){return actionCost;}
    public int getFocusCost(){return focusCost;}
    public int getInvestitureCost(){return investitureCost;}


    public void setPrimaryKey(int newKey){primaryKey = newKey;}
    public void setName(String newName){name = newName;}
    public void setDescription(String newDescription){description = newDescription;}
    public void setFlavorText(String newFlavor){flavorText = newFlavor;}
    public void setActionType(String newType){actionType = newType;}
    public void setBranch(String newBranch){branch = newBranch;}
    public void setBranchID(int newID){branchID = newID;}
    public void setPath(String newPath){path = newPath;}
    public void setPathID(int newID){pathID = newID;}
    public void setRadiantPath1(String newPath){radiantPath1 = newPath;}
    public void setRadiantPath2(String newPath){radiantPath2 = newPath;}
    public void setRadiantPath1ID(int newID){radiantPath1ID = newID;}
    public void setRadiantPath2ID(int newID){radiantPath2ID = newID;}
    public void setActionCost(int newCost){actionCost = newCost;}
    public void setFocusCost(int newCost){focusCost = newCost;}
    public void setInvestitureCost(int newCost){investitureCost = newCost;}
}