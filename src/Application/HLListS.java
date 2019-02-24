package Application;

public class HLListS {//List for the String types

    HLListNodeS head = null; //Initialize the head node and declare as null

    public void clear(){ //To clear the list, set the head node as null
        head = null;
    }

    public void add(String item){ //Add String values to the list

        HLListNodeS node = new HLListNodeS();
        node.setDescription(item); //set the value using the setDescription method of the node class
        node.setNext_Item(null); //set null to the next

        if(head == null){
            head = node; //If head is null, the node should be given the head reference
        }
        else{ //If the head isn't null
            HLListNodeS n = head; //set the head to the new node
            while (n.next_Item!=null){  //traverse until the next is null
                n = n.next_Item;
            }
            n.next_Item = node;  //set the value to the next
        }
    }

    public void remove(int index){ //to remove a node from the list
        if(index ==1){ //if the index is one,
            head = head.next_Item; //set the head reference to the node after the head
        }
        else{ //If the index is some other value
                HLListNodeS nodePrev = head; //set the head to a new node
                HLListNodeS nodeNxt = null;

                for (int i = 1; i < index - 1; i++) {
                    nodePrev = nodePrev.next_Item;  //traverse until the node before index
                }

                //remove the index which needs to be removed by setting the reference off the previous node's next to the next node after the index
                nodeNxt = nodePrev.next_Item;
                nodePrev.next_Item = nodeNxt.next_Item;

        }
    }

    public String get(int index){ //get values of a certain index

        HLListNodeS node = head;

        for(int i=0; i<index-1; i++){ //traverse to the node before the required index
            node = node.next_Item;
        }
        return node.getDescription(); // return the next node's value
    }

    public void addAll(HLListS otherList) { //add a complete list to the existing list

        HLListNodeS node = new HLListNodeS();
        node = head;

        if(head == null){ //If the head is null set the new head to the new list's head
            head = otherList.head;
        }
        else { //If the head is not null,
            while (node.next_Item != null) { //traverse until finding a null node
                node = node.next_Item;
            }
            node.next_Item = otherList.head; //set the head of the incoming list to the next
        }
    }

    public void set(int index, String listItem) { //To set the receiving list item to an index

        HLListNodeS node = new HLListNodeS();
        node = head;
        while(index>1){ //traverse to the index
            node = node.next_Item;
            index--;
        }
        node.description = listItem; //set incoming price
    }

    public int size() { //get the size of the list
        HLListNodeS node = new HLListNodeS();
        int counter = 1;

        if(head == null){ //If the head is null, the size is 0
            return 0;
        }
        else {
            node = head;

            while (node.next_Item != null) { //count until the next is null
                node = node.next_Item;
                counter++;
            }
            return counter; //return the count
        }
    }
}
