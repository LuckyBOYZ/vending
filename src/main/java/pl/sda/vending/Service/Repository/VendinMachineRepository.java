package pl.sda.vending.Service.Repository;

import pl.sda.vending.model.VendingMachine;

import java.util.Optional;

public interface VendinMachineRepository {
    VendingMachine save(VendingMachine machine);
    Optional<VendingMachine> load();
}
