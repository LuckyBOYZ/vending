package pl.sda.vending;

import java.util.Arrays;

public enum ServiceMenuSelection {
    ADD_TRAY(1, "Add new tray to machine"),
    REMOVE_TRAY(2 , "Select tray to remove"),
    ADD_PRODUCT_FOR_TRAY(3 , "Select tray for add product to tray"),
    REMOVE_PRODUCT_FROM_TRAY(4 , "Select tray for remove product to tray"),
    CHANGE_PRICE(5 , "Select tray to change price for product"),
    EXIT(9 , "End of service menu");
    //dodac kolejne opcje removeTray , addProductFotTray removeProductcFromTray , changePrice , Exit 9

    private final Integer optionNumber;
    private final String optionMessage;
    ServiceMenuSelection(Integer optionNumber, String optionMessage) {
        this.optionNumber = optionNumber;
        this.optionMessage = optionMessage;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getOptionMessage() {
        return optionMessage;
    }

    public static ServiceMenuSelection serviceMenuSelection (Integer requestedOptionNumber){
        return Arrays.stream(values())
                .filter(x -> x.getOptionNumber().equals(requestedOptionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given wrong number"));
    }
}
