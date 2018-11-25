package pl.sda.vending.model;

import pl.sda.vending.util.Configuration;

import java.io.Serializable;
import java.util.Optional;

public class VendingMachine implements Serializable {
    public static final long serialVersionUID = 1L;
    private final Long rowsCount;
    private final Long colsCount;
    private final Tray[][] trays;

    public VendingMachine(Configuration configuration) {

        rowsCount = configuration.getLongProperty("machine.size.rows", 6L);
        colsCount = configuration.getLongProperty("machine.size.cols", 4L);
        if (rowsCount <= 0 || rowsCount > 9) {
            throw new IllegalArgumentException("Row count " + rowsCount + " is invalid");
        }
        if (colsCount() <= 0 || colsCount() > 26) {
            throw new IllegalArgumentException("Row count " + rowsCount + " is invalid");
        }
        trays = new Tray[rowsCount.intValue()][colsCount.intValue()];
    }

    public Optional<Tray> getTrayAtPosition(int rowNo, int colNo) {
        try {
            Tray tray = trays[rowNo][colNo];
            Optional<Tray> wrappedTray = Optional.ofNullable(tray);
            return wrappedTray;
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public Long rowsCount() {
        return rowsCount;
    }

    public Long colsCount() {
        return colsCount;
    }

    public Optional<String> productNameAPosition(Integer rowNo, Integer colNo) {
        Optional<Tray> tray = getTrayAtPosition(rowNo, colNo);
        if (tray.isPresent()) {
            return tray.get().firstProductName();
        }

        return Optional.empty();

    }

    public Optional<Product> buyProductWithSymbol(String traySymbol) {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                if (trays[i][j] != null) {
                    if (trays[i][j].getSymbol().equals(traySymbol)) {
                        return trays[i][j].buyProduct();
                    }
                }
            }
        }
        return Optional.empty();
    }

    public boolean addProductWithSymbol(String traySymbol, Product product) {
        getTrayForSymbol(traySymbol);
        Optional<Tray> trayForSymbol = getTrayForSymbol(traySymbol);
        if (trayForSymbol.isPresent()) {
            return trayForSymbol.get().addProduct(product);
        } else {
            return false;
        }
    }

    public boolean placeTrayAtPosition(Tray tray) {
        String symbol = tray.getSymbol();
        if (symbol.length() != 2) {
            return false;
        }
        int rowNO = symbol.charAt(0) - 'A';
        int colNo = symbol.charAt(1) - '1';
        if (rowNO < 0 || rowNO >= rowsCount || colNo < 0 || colNo >= colsCount) {
            return false;
        } else if (trays[rowNO][colNo] == null) {
            trays[rowNO][colNo] = tray;
            return true;
        } else {
            return false;
        }
    }

    public Optional<Tray> removeTrayWithSymbol(String traySymbol) {
        if (traySymbol.length() != 2) {
            return Optional.empty();
        }
        int rowNo = traySymbol.charAt(0) - 'A';
        int colNo = traySymbol.charAt(1) - '1';
        Optional<Tray> trayAtPosition = getTrayAtPosition(rowNo, colNo);
        if (trayAtPosition.isPresent()) {
            trays[rowNo][colNo] = null;
        }
        return trayAtPosition;
    }

    private Optional<Tray> getTrayForSymbol(String traySymbol) {
        if (traySymbol.length() != 2) {
            return Optional.empty();
        }
        int rowNo = traySymbol.charAt(0) - 'A';
        int colNo = traySymbol.charAt(1) - '1';
        return getTrayAtPosition(rowNo, colNo);
    }

    public boolean updatePriceForSymbol(String traySymbol, Long updatedPrice) {
        Optional<Tray> trayForSymbol = getTrayForSymbol(traySymbol);
        if (trayForSymbol.isPresent()) {
            trayForSymbol.get().updatePrice(updatedPrice);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Product> removeProductFromTray(String traySymbol) {
        Optional<Tray> trayForSymbol = getTrayForSymbol(traySymbol);
        if (trayForSymbol.isPresent()) {
            Product product = trayForSymbol.get().removeProductFromTray();
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    public VendingMachineSnapshot snapshot() {
        VendingMachineSnapshot.Builder builder = VendingMachineSnapshot.builder(rowsCount, colsCount);
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                Tray tray = trays[i][j];
                if (tray != null) {
                    builder.tray(i, j, tray.snapshot());
                }
            }
        }
        return builder.build();
    }
}