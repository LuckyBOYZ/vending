package pl.sda.vending;

import pl.sda.vending.Repository.HardDriveVendingMachineRepository;
import pl.sda.vending.Service.DefaultCustomerService;
import pl.sda.vending.Service.DefaultEmployeeService;
import pl.sda.vending.Service.Repository.VendinMachineRepository;
import pl.sda.vending.controller.CustomerOperationController;
import pl.sda.vending.controller.EmployeeOperationController;
import pl.sda.vending.controller.Service.CustomerService;
import pl.sda.vending.controller.Service.EmployeeService;
import pl.sda.vending.util.Configuration;

import java.util.Scanner;

public class Main {
    Configuration configuration = new Configuration();
    VendinMachineRepository vendingMachineRepository = new HardDriveVendingMachineRepository(configuration);
    EmployeeService employeeService = new DefaultEmployeeService(vendingMachineRepository, configuration);
    CustomerService customerService = new DefaultCustomerService(vendingMachineRepository);
    EmployeeOperationController employeeOperationController = new EmployeeOperationController(employeeService);
    CustomerOperationController customerOperationController = new CustomerOperationController(customerService);

    private void startApplication() {
        while (true) {
            customerOperationController.printMachine();
            printMenu();
            try {
                UserMenuSelection userSelection = getUserSelection();
                switch (userSelection) {
                    case BUY_PRODUCT:
                        customerOperationController.buyProduct();
                        break;
                    case EXIT:
                        System.out.println(" > Bye ");
                        return;
                    case SERVICE_MENU:
                        handleServiceUser();
                        break;
                    default:
                        System.out.println("Invalid selection");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printMenu() {
        UserMenuSelection[] allProssibleSelections = UserMenuSelection.values();
        for (UserMenuSelection menuPosition : allProssibleSelections) {
            System.out.println(menuPosition.getOptionNumber() + ". " + menuPosition.getOptionText());
        }
    }

    private void handleServiceUser() {
        while (true) {
            customerOperationController.printMachine();
            printServiceMenu();
            try {
                ServiceMenuSelection serviceMenuSelection = getUserServiceSelection();
                switch (serviceMenuSelection) {
                    case ADD_TRAY:
                        employeeOperationController.addTray();
                        break;
                    case REMOVE_TRAY:
                        employeeOperationController.removeTray();
                        break;
                    case ADD_PRODUCT_FOR_TRAY:
                        employeeOperationController.addProducts();
                        break;
                    case REMOVE_PRODUCT_FROM_TRAY:
                        employeeOperationController.removeProductsFromTray();
                        break;
                    case CHANGE_PRICE:
                        employeeOperationController.changePrice();
                        break;
                    case EXIT:
                        System.out.println("End service menu");
                        return;
                    default:
                        System.out.println("Invalid selection");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printServiceMenu() {
        ServiceMenuSelection[] allPorssibleSelectionsServiceMenu = ServiceMenuSelection.values();
        for (ServiceMenuSelection menu : allPorssibleSelectionsServiceMenu) {
            System.out.println(menu.getOptionNumber() + ". " + menu.getOptionMessage());
        }
    }

    private UserMenuSelection getUserSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            UserMenuSelection userMenuSelection = UserMenuSelection.selectionForOptionNumber(menuNumber);
            return userMenuSelection;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal selection format");
        }
    }

    private ServiceMenuSelection getUserServiceSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            ServiceMenuSelection userServiceMenuSelection = ServiceMenuSelection.serviceMenuSelection(menuNumber);
            return userServiceMenuSelection;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal selection format");
        }
    }


    public static void main(String[] args) {
        new Main().startApplication();

    }
}

