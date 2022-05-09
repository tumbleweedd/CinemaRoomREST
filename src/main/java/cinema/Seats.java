package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seats {
    private int row;
    private int column;
    private int price;
    @JsonIgnore
    boolean purchased;

    public int getPrice() {
        return price;
    }

    public Seats(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = 0;
        this.purchased = false;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Seats() {

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


//    public void addPurchase(Purchase purchase) {
//        this.purchaseHashMap.put()
//    }
}