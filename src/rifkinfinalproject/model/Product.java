package rifkinfinalproject.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    
    private final IntegerProperty productID = new SimpleIntegerProperty(0);
    private final StringProperty name = new SimpleStringProperty("");
    private final DoubleProperty price = new SimpleDoubleProperty(0.0);
    private final IntegerProperty inStock = new SimpleIntegerProperty(0);
    private final IntegerProperty min = new SimpleIntegerProperty(0);
    private final IntegerProperty max = new SimpleIntegerProperty(0);
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    // CONSTRUCTORS
    public Product(){
        this(0, "", 0.0, 0, 0, 0, null);
    }
    
    public Product(int productID, String name, double price, int inStock, int min, int max, Part firstPart) {
        setProductID(productID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
        associatedParts.add(firstPart);
    }
    
    // GETTERS FOR PROPERTY VALUES
    public int getProductID() {
        return productID.get();
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
    
    public ObservableList getAssociatedParts() {
        return associatedParts;
    }
    
    // SETTERS FOR PROPERTY VALUES
    public void setProductID(int productID) {
        this.productID.set(productID);
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
    
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    
    // GETTERS FOR PROPERTY ITSELF
    public IntegerProperty productIDProperty() {
        return productID;
    }
    
    public StringProperty productNameProperty() {
        return name;
    }
    
    public DoubleProperty productPriceProperty() {
        return price;
    }
    
    public IntegerProperty productInStockProperty() {
        return inStock;
    }
    
    public IntegerProperty productMinProperty() {
        return min;
    }
    
    public IntegerProperty productMaxProperty() {
        return max;
    }
    
    // OTHER METHODS
    public static String isProductValid(String name, int min, int max, int inv, double price, ObservableList<Part> associatedParts, String message) {
        
        // SET 1: INVENTORY VALUE SHOULD BE BETWEEN MIN AND MAX
        if (inv > max || inv < min) {
            message += "The current inventory level of the part must be between the minimum and maximum values.\n";
        }
        
        // SET 1: MAX SHOULD BE ABOVE MIN
        if (max < min) {
            message += "The maximum inventory value must be greater than the minimum value.\n";
        }
        
        // SET 1: PRODUCT SHOULD HAVE AT LEAST ONE PART
        if (associatedParts.size() < 1) {
            message += "The product must have at least one part.\n";
        }
        
        // SET 2: PRICE OF PRODUCT SHOULD BE GREATER THAN OR EQUAL TO TOTAL PRICE OF PARTS
        double totalPriceOfParts = 0.0;
        for (int i = 0; i < associatedParts.size(); i++) {
            totalPriceOfParts += associatedParts.get(i).getPrice();
        }
        if (price < totalPriceOfParts) {
            message += "Product price must be greater than or equal to the cost of the parts.\n";
        }
        
        // SET 2: NAME CANNOT BE BLANK
        if (name.length() == 0) {
            message += "The name of the product cannot be blank.\n";
        }
        
        if (inv <= 0) {
            message += "The current inventory level of the product must be greater than zero.\n";
        }
        
        if (price <= 0) {
            message += "The price of the product must be greater than zero.\n";
        }

        return message;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public void removeAssociatedPart(Part part) {
        associatedParts.remove(part);
    }
    
    public int lookupAssociatedPart(String partSearch) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(partSearch)) {
            for (int i = 0; i < associatedParts.size(); i++) {
                if (Integer.parseInt(partSearch) == associatedParts.get(i).getPartID()) {
                    isFound = true;
                    index = i;
                }
            }
        } else {
            for (int i = 0; i < associatedParts.size(); i++) {
                if (partSearch.equals(associatedParts.get(i).getName())) {
                    isFound = true;
                    index = i;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            System.out.println("No associated part was found.");
            return -1;
        }
    }
    
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
