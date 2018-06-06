package rifkinfinalproject.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Outsourced extends Part {
    
    private final StringProperty companyName = new SimpleStringProperty("");
    
    // CONSTRUCTORS
    public Outsourced() {
        this(0, "", 0.0, 0, 0, 0, "");
    }
    
    public Outsourced(int partID, String name, double price, int inStock, int min, int max, String companyName) {
        super(partID, name, price, inStock, min, max);
        setCompanyName(companyName);
    }
    
    // GETTERS FOR PROPERTY VALUES
    public String getCompanyName() {
        return companyName.get();
    }
    
    // SETTERS FOR PROPERTY VALUES
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
      
    // GETTERS FOR PROPERTY ITSELF
    public StringProperty companyNameProperty() {
        return companyName;
    }
    
}
