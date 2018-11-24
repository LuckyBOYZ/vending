package pl.sda.vending.controller.Service;

import pl.sda.vending.model.Product;
import pl.sda.vending.model.VendingMachine;

import java.util.Optional;

public interface CustomerService {
    Optional<Product> buyProductFromTray(String traySymbol);
    Optional<VendingMachine> loadMachineToPrint();
}
