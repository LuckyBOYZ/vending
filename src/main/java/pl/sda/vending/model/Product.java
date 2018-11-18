package pl.sda.vending.model;

import java.io.Serializable;

public class Product implements Serializable {
    public static final long serialVersionUID = 1L;
    private String name;

    public Product(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
