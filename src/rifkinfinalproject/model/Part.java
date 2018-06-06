package rifkinfinalproject.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {
    
    private final IntegerProperty partID = new SimpleIntegerProperty(0);
    private final StringProperty name = new SimpleStringProperty("");
    private final DoubleProperty price = new SimpleDoubleProperty(0.0);
    private final IntegerProperty inStock = new SimpleIntegerProperty(0);
    private final IntegerProperty min = new SimpleIntegerProperty(0);
    private final IntegerProperty max = new SimpleIntegerProperty(0);
    
    // CONSTRUCTORS
    public Part(){
        this(0, "", 0.0, 0, 0, 0);
    }
    
    public Part(int partID, String name, double price, int inStock, int min, int max) {
        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    }
    
    // GETTERS FOR PROPERTY VALUES
    public int getPartID() {
        return partID.get();
    }

    public String getName() {
        return name.get();
    }
    
    public double getPrice() {
        return price.get();
    }
    
    public int getInStock() {
        return inStock.get();
    }
    
    public int getMin() {
        return min.get();
    }
    
    public int getMax() {
        return max.get();
    }
    
    // SETTERS FOR PROPERTY VALUES
    public void setPartID(int partID) {
        this.partID.set(partID);
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }
    
    public void setMin(int min) {
        this.min.set(min);
    }
    
    public void setMax(int max) {
        this.max.set(max);
    }
        
    // GETTERS FOR PROPERTY ITSELF
    public IntegerProperty partIDProperty() {
        return partID;
    }
    
    public StringProperty partNameProperty() {
        return name;
    }
    
    public DoubleProperty partPriceProperty() {
        return price;
    }
    
    public IntegerProperty partInStockProperty() {
        return inStock;
    }
    
    public IntegerProperty partMinProperty() {
        return min;
    }
    
    public IntegerProperty partMaxProperty() {
        return max;
    }
    
    // OTHER METHODS
    public static String isPartValid(String name, int min, int max, int inv, double price, String message) {
        if (inv > max || inv < min) {
            message += "The current inventory level of the part must be between the minimum and maximum values.\n";
        }
        if (max < min) {
            message += "The maximum inventory value must be greater than the minimum value.\n";
        }
        if (name.length() == 0) {
            message += "The name of the part cannot be blank.\n";
        }
        if (inv < 0) {
            message += "The current inventory level of the part must be greater than 0.\n";
        }
        if (price <= 0) {
            message += "The price of the part must be greater than 0.\n";
        }
        return message;
    }
    
}
