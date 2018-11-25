package pl.sda.vending.controller;

import pl.sda.vending.controller.Service.EmployeeService;

import java.util.Optional;
import java.util.Scanner;

public class EmployeeOperationController {

    private final EmployeeService employeeService;

    public EmployeeOperationController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void addProducts() {
        Optional<String> errorMessage = employeeService.addProduct(getTraySymbolFromUSer(), getNameProductFromUser(), getQuantityOfProductsFromUser());
        System.out.println(errorMessage.orElse("All products has been added"));
    }

    public void addTray() {
        String symbol = getTraySymbolFromUSer();
        Long price = getPriceFromUser();

        Optional<String> errorMessege = employeeService.addTrayForSymbolAndPrice(symbol ,price);
        System.out.println(errorMessege.orElse("Tray has been added"));
    }

    public void removeTray() {
        Optional<String> errorMessage = employeeService.removeTrayWithSymbol(getTraySymbolFromUSer());
        System.out.println(errorMessage.orElse("Tray has been removed"));
    }

    public void changePrice(){
        String symbol = getTraySymbolFromUSer();
        Long price = getPriceFromUser();
        Optional<String> error = employeeService.updatedPrice(symbol, price);
        if (error.isPresent()){
            System.out.println(error.get());
        } else {
            System.out.println("Success");
        }
    }

    public void removeProductsFromTray(){
        String traySymbolFromUSer = getTraySymbolFromUSer();
        Optional<String> error = employeeService.removeProductsFromTray(traySymbolFromUSer);
        if (error.isPresent()){
            System.out.println(error.get());
        } else {
            System.out.println("Products from tray are removed");
        }
    }


    private String getTraySymbolFromUSer() {
        System.out.print(" > Provide tray symbol :");
        return getUserInputString().toUpperCase();
    }

    private Long getPriceFromUser() {
        Long price = null;
        while (price == null) {
            System.out.print(" > Provided tray price: ");
            try {
                price = Long.parseLong(getUserInputString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Try again.");
            }
        }
        return price;
    }

    private String getUserInputString() {
        return new Scanner(System.in).nextLine();
    }

    private Integer getQuantityOfProductsFromUser() {
        Integer quantity = null;
        while (quantity == null) {
            System.out.print(" > Provided product quantity: ");
            try {
                quantity = Integer.parseInt(getUserInputString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
        return quantity;
    }

    private String getNameProductFromUser() {
        System.out.print(" > Provide name Products :");
        return getUserInputString().toUpperCase();
    }

}