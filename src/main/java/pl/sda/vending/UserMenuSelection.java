package pl.sda.vending;

import java.util.Arrays;

public enum UserMenuSelection {
    BUY_PRODUCT(1, "Buy product"),
    EXIT(9, "Exit"),
    SERVICE_MENU(0, "Service Menu");

    private final Integer optionNumber;
    private final String optionText;

    UserMenuSelection(Integer optionNumber , String optionText) {
        this.optionNumber = optionNumber;
        this.optionText = optionText;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getOptionText() {
        return optionText;
    }

    public static UserMenuSelection selectionForOptionNumber(Integer requestedOptionNumber){
        return Arrays.stream(values())
                .filter(enumValue -> enumValue.getOptionNumber().equals(requestedOptionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given wrong option: "+requestedOptionNumber));

//        for (UserMenuSelection userMenu: values()) {
//            if (userMenu.getOptionNumber() == Integer.valueOf(requestedOptionNumber)){
//                return userMenu;
//            }
//        }
//        throw new IllegalArgumentException("Given wrong option: "+requestedOptionNumber);
    }
}
