package pl.sda.vending.Service;

import pl.sda.vending.Repository.HardDriveVendingMachineRepository;
import pl.sda.vending.Service.Repository.VendinMachineRepository;
import pl.sda.vending.controller.Service.CustomerService;
import pl.sda.vending.model.Product;
import pl.sda.vending.model.VendingMachine;

import java.util.Optional;

public class DefaultCustomerService implements CustomerService {
    private final VendinMachineRepository machineRepository;

    public DefaultCustomerService(VendinMachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public Optional<Product> buyProductFromTray(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Product> boughtProduct = machine.buyProductWithSymbol(traySymbol.toUpperCase());
            machineRepository.save(machine);
            return boughtProduct;
        } else {
            System.out.println("Vending machine out of service");
            return Optional.empty();
        }
    }

    @Override
    public Optional<VendingMachine> loadMachineToPrint() {
        return machineRepository.load();
    }
}
