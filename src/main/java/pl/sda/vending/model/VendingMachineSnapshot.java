package pl.sda.vending.model;

import java.util.Optional;

public class VendingMachineSnapshot {
    private final TraySnapshot[][] trays;
    private final Long rows;
    private final Long cols;

    private VendingMachineSnapshot(Builder builder){
        trays = builder.trays;
        this.rows = builder.rows;
        this.cols = builder.cols;
    }

    public static Builder builder (Long rows , Long cols){
        return new Builder(rows , cols);
    }

    public Optional<TraySnapshot> getTray(int rowNo , int colNo){
        return Optional.ofNullable(trays[rowNo][colNo]);
    }

    public Long getRowsCount(){
        return rows;
    }
    public Long getColsCount(){
        return cols;
    }

    public static class Builder {
        private TraySnapshot[][] trays;
        private Long rows;
        private Long cols;

        private Builder(Long rows , Long cols){
            trays = new TraySnapshot[rows.intValue()][cols.intValue()];
            this.rows = rows;
            this.cols = cols;
        }

        public Builder tray(int rowNumber , int colNumber , TraySnapshot tray){
            trays[rowNumber][colNumber] = tray;
            return this;
        }


        public VendingMachineSnapshot build(){
            return new VendingMachineSnapshot(this);

        }
    }
}
