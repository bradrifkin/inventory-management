package rifkinfinalproject.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Inhouse extends Part {
    
    private final IntegerProperty machineID = new SimpleIntegerProperty(0);
    
    // CONSTRUCTORS
    public Inhouse() {
        this(0, "", 0.0, 0, 0, 0, 0);
    }
      
    public Inhouse(int partID, String name, double price, int inStock, int min, int max, int machineID) {
        super(partID, name, price, inStock, min, max);
        setMachineID(machineID);
    }
    
    // GETTERS FOR PROPERTY VALUES
    public int getMachineID() {
        return machineID.get();
    }
    
    // SETTERS FOR PROPERTY VALUES
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
      
    // GETTERS FOR PROPERTY ITSELF
    public IntegerProperty machineIDProperty() {
        return machineID;
    }
    
}
