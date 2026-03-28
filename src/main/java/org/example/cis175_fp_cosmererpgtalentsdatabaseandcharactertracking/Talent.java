package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import java.io.Serializable;

public class Talent implements Serializable {
    private String name;
    private String description;
    private String flavorText;

    public Talent(){
        name = description = flavorText = "";
    }

    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getFlavorText(){return flavorText;}

    public void setName(String newName){name = newName;}
    public void setDescription(String newDescription){description = newDescription;}
    public void setFlavorText(String newFlavor){flavorText = newFlavor;}
}