package rifkinfinalproject.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partIDCount = 4;
    private static int productIDCount = 3;
    
    // CONSTRUCTOR
    public Inventory() {
        
    }
    
    // ID Counter Methods
    public static int getPartIDCount() {
        partIDCount++;
        return partIDCount;
    }
    
    public static int getProductIDCount() {
        productIDCount++;
        return productIDCount;
    }
    
    public static void addPart(Part part) {
        allParts.add(part);
    }
    
    public void removePart(Part part) {
        allParts.remove(part);
    }
    
    public static int lookupPart(String partSearch) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(partSearch)) {
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(partSearch) == allParts.get(i).getPartID()) {
                    isFound = true;
                    index = i;
                }
            }
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (partSearch.equals(allParts.get(i).getName())) {
                    isFound = true;
                    index = i;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            System.out.println("No part was found.");
            return -1;
        }
    }
    
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    public static void updateProduct(int index, Product product) {
        products.set(index, product);
    }
    
    public static void addProduct(Product product) {
        products.add(product);
    }
    
    public void removeProduct(Product product) {
        products.remove(product);
    }

    public int lookupProduct(String productSearch) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(productSearch)) {
            for (int i = 0; i < products.size(); i++) {
                if (Integer.parseInt(productSearch) == products.get(i).getProductID()) {
                    isFound = true;
                    index = i;
                }
            }
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (productSearch.equals(products.get(i).getName())) {
                    isFound = true;
                    index = i;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            System.out.println("No product was found.");
            return -1;
        }
    }
        
    public static ObservableList<Part> getPartData() {
        return allParts;
    }
    
    public static ObservableList<Product> getProductData() {
        return products;
    }
    
    
    
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
   
}
