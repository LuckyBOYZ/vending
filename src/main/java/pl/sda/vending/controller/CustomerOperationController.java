package pl.sda.vending.controller;

import pl.sda.vending.controller.Service.CustomerService;
import pl.sda.vending.model.Product;
import pl.sda.vending.model.Tray;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.util.StringUtil;

import java.util.Optional;
import java.util.Scanner;

public class CustomerOperationController {
    private final CustomerService customerService;
    private final Integer trayWitdh = 12;

    public CustomerOperationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void printMachine() {
        Optional<VendingMachine> loadedMachine = customerService.loadMachineToPrint();
        if (!loadedMachine.isPresent()){
            System.out.println("Vending Machine out of service");
            return;
        }
        VendingMachine machine = loadedMachine.get();
        for (int rowNo = 0; rowNo < machine.rowsCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printUpperBoundry(machine, rowNo, colNo);
            }
            System.out.println();

            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printSymbol(machine, rowNo, colNo);
            }

            System.out.println();

            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printNameProduct(machine, rowNo, colNo);
            }

            System.out.println();

            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printPrice(machine, rowNo, colNo);
            }

            System.out.println();

            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printLowerBoundry(machine, rowNo, colNo);
            }

            System.out.println();
        }
    }

    public void buyProduct(){
        System.out.print(" > Tray symbol: ");
        String traySymbol = new Scanner(System.in).nextLine();
        Optional<Product> product = customerService.buyProductFromTray(traySymbol);
        if (product.isPresent()) {
            System.out.println(" > Please take your product: " + product.get().getName());
        } else {
            System.out.println("Out of stock");
        }
    }

    private void printUpperBoundry(VendingMachine machine, int rowNumber, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWitdh) + "+");
    }

    private void printSymbol(VendingMachine machine, int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        String traySymbol = tray.map(Tray::getSymbol).orElse("--");
        System.out.print("|" + StringUtil.adjustText(traySymbol, trayWitdh) + "|");
    }

    private void printNameProduct(VendingMachine machine, int rowNo, int colNo) {
        Optional<String> productName = machine.productNameAPosition(rowNo, colNo);
        String formattedName = productName.orElse("--");
        System.out.print("|" + StringUtil.adjustText(formattedName, trayWitdh) + "|");
    }

    private void printPrice(VendingMachine machine, int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        Long price = tray.map(Tray::getPrice).orElse(0L);
        String formattedMoney = StringUtil.formatMoney(price);
        String centredMoney = StringUtil.adjustText(formattedMoney, trayWitdh);
        System.out.print("|" + centredMoney + "|");
    }


    private void printLowerBoundry(VendingMachine machine, int rowNo, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWitdh) + "+");
    }

}
