import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item implements Serializable {
    private String category;
    private String name;
    private double price;
    private int amount;
    private Date expirationDate;

    public Item(String category, String name, double price, int amount, Date expirationDate) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.expirationDate = expirationDate;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
     @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return "Name: " + name +
               ", Category: " + category +
               ", Price: $" + price +
               ", Amount: " + amount +
               ", Expiration Date: " + dateFormat.format(expirationDate);
    }
}
