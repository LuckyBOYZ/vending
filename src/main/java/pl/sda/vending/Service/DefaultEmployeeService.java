package pl.sda.vending.Service;

import pl.sda.vending.Service.Repository.VendinMachineRepository;
import pl.sda.vending.controller.Service.EmployeeService;
import pl.sda.vending.model.Product;
import pl.sda.vending.model.Tray;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.util.Configuration;

import java.util.Optional;

public class DefaultEmployeeService implements EmployeeService {
    private final VendinMachineRepository machineRepository;
    private final Configuration configuration;

    public DefaultEmployeeService(VendinMachineRepository machineRepository, Configuration configuration) {
        this.machineRepository = machineRepository;
        this.configuration = configuration;
    }

    @Override
    public Optional<String> addTray(Tray tray) {
        Optional<VendingMachine> optionalVendingMachine = machineRepository.load();
        VendingMachine vendingMachine = optionalVendingMachine.orElseGet(() -> new VendingMachine(configuration));
        if (vendingMachine.placeTrayAtPosition(tray)) {
            machineRepository.save(vendingMachine);
            return Optional.empty();
        } else {
            return Optional.of("Not add tray");
        }
    }

    @Override
    public Optional<String> removeTrayWithSymbol(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            Optional<Tray> removedTray = loadedMachine.get().removeTrayWithSymbol(traySymbol);
            if (removedTray.isPresent()) {
                machineRepository.save(loadedMachine.get());
                return Optional.empty();
            } else {
                return Optional.of("Tray can not be removed");
            }
        } else {
            return Optional.of("There is no vending machine");
        }
    }

    @Override
    public Optional<String> addProduct(String traySymbol, String productName, Integer amountOfProducts) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        VendingMachine vendingMachine = loadedMachine.get();
        if (loadedMachine.isPresent()) {
            for (int i = 0; i < amountOfProducts; i++) {
                if (!loadedMachine.get().addProductWithSymbol(traySymbol, new Product(productName))) {
                    machineRepository.save(vendingMachine);
                    return Optional.of("Could not add " + (amountOfProducts - i) + " products");
                }
            }
        } else {
            return Optional.of("There is no vending machine. Please add one tray to machine");
        }
        machineRepository.save(vendingMachine);
        return Optional.empty();
    }

    @Override
    public Optional<String> updatedPrice(String traySymbol, Long updatedPrice) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine vendingMachine = loadedMachine.get();
            boolean successful = vendingMachine.updatePriceForSymbol(traySymbol, updatedPrice);
            machineRepository.save(vendingMachine);
            if (successful){
                return Optional.empty();
            }
            else {
                return Optional.of("Coulde not change price, check tray symbol");
            }
        } else {
            return Optional.of("There is no vending machine, create one first");
        }
    }

    @Override
    public Optional<String> removeProductsFromTray(String traySymbol) {
        Optional<VendingMachine> loadeMachine = machineRepository.load();
        if (loadeMachine.isPresent()){
            VendingMachine machine = loadeMachine.get();
            Optional<Product> product = machine.removeProductFromTray(traySymbol);
            machineRepository.save(machine);
            if (product.isPresent()){
                return Optional.empty();
            } else {
                Optional.of("Could not poll product, please check tray");
            }
        }
        return Optional.of("There is no vending machine, create one first");
    }
}

