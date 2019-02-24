package Application;

public class HLListNodeS {

    HLListNodeS next_Item;
    String description = "";

    public HLListNodeS(){ //whenever the HLListNodeS is called, set the next to null
        next_Item = null;
    }

    public void setNext_Item(HLListNodeS next_ItemLink) { //set the next
        this.next_Item = next_ItemLink;
    }

    public String getDescription() { //to send the node's description
        return description;
    }

    public void setDescription(String inDescription) { //set the value of node's description
        description = inDescription;
    }

}
