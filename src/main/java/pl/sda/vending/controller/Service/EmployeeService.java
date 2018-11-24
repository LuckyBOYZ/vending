package pl.sda.vending.controller.Service;

import pl.sda.vending.model.Tray;

import java.util.Optional;

public interface EmployeeService {
    Optional<String> addTray (Tray tray);
    Optional<String> removeTrayWithSymbol(String traySymbol);
    Optional<String> addProduct(String traySymbol , String productName , Integer amountOfProducts);
    Optional<String> updatedPrice (String traySymbol , Long updatedPrice);
    Optional<String> removeProductsFromTray(String traySymbol);
}