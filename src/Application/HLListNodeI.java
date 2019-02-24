package Application;

public class HLListNodeI {

    int price=0;
    HLListNodeI next_Price;

    public HLListNodeI(){ //whenever the HLListNodeI is called set the next value to null
        next_Price = null;
    }

    public void setNext_Price(HLListNodeI next_PriceLink) { //set the next
        this.next_Price = next_PriceLink;
    }

    public int getPrice() { //get the value of node's price
        return price;
    }

    public void setPrice(int inPrice) { //set the value to the node's price
        price = inPrice;
    }

}
