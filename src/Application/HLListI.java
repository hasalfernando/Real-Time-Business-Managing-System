package Application;

public class HLListI { //List for the integer types

    HLListNodeI head = null; //Initialize the head node and declare as null

    public void clear(){ //To clear the list, set the head node as null
        head = null;
    }

    public void add(int price){ //Add integer values to the list

        HLListNodeI node = new HLListNodeI();
        node.setPrice(price); //set the value using the setPrice method of the node class
        node.setNext_Price(null); //set null to the next

        if(head == null){
            head = node; //If head is null, the node should be given the head reference
        }
        else{ //If the head isn't null

            HLListNodeI n = head; //set the head to the new node
            while (n.next_Price!=null){ //traverse until the next is null
                n = n.next_Price;
            }
            n.next_Price = node; //set the value to the next
        }
    }

    public void remove(int index){ //to remove a node from the list

        if(index ==1){ //if the index is one,
            head = head.next_Price; //set the head reference to the node after the head
        }
        else{ //If the index is some other value
            HLListNodeI nodePrev = head; //set the head to a new node
            HLListNodeI nodeNxt = null;

            for (int i = 1; i < index - 1; i++) { //traverse until the node before index
                nodePrev = nodePrev.next_Price;
            }
            //remove the index which needs to be removed by setting the reference off the previous node's next to the next node after the index
            nodeNxt = nodePrev.next_Price;
            nodePrev.next_Price = nodeNxt.next_Price;

        }
    }

    public int get(int index){ //get values of a certain index

        HLListNodeI node = head;

        for(int i=0; i<index-1; i++){ //traverse to the node before the required index
            node = node.next_Price;
        }
        return node.getPrice(); // return the next node's value
    }

    public void addAll(HLListI otherList) { //add a complete list to the existing list

        HLListNodeI node = new HLListNodeI();
        node = head;

        if(head == null){ //If the head is null set the new head to the new list's head
            head = otherList.head;
        }
        else { //If the head is not null,
            while (node.next_Price != null) { //traverse until finding a null node
                node = node.next_Price;
            }
            node.next_Price = otherList.head; //set the head of the incoming list to the next
        }
    }

    public void set(int index, int listItem) { //To set the receiving price to an index

        HLListNodeI node = new HLListNodeI();
        node = head;
        while(index>1){ //traverse to the index
            node = node.next_Price;
            index--;
        }
        node.price = listItem; //set incoming price
    }

    public int size() { //get the size of the list

        HLListNodeI node = new HLListNodeI();
        int counter = 1;

        if(head == null){ //If the head is null, the size is 0
            return 0;
        }
        else {
            node = head;

            while (node.next_Price != null) { //count until the next is null
                node = node.next_Price;
                counter++;
            }
            return counter; //return the count
        }
    }
}
