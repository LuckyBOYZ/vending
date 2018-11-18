package pl.sda.vending.model;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TrayTest {

    @Test
    public void shouldBeAbleToBuyLastAvailableProduct(){
        //given
        Product definedProduct = new Product("B1");
        Tray tray = Tray.builder("A1").products(definedProduct).build();
        //when
        Optional<Product> boughtProduct = tray.buyProduct();
        //then
        assertTrue(boughtProduct.isPresent());
        assertEquals(definedProduct, boughtProduct.get());
    }

    @Test
    public void shouldAddNewProductToTray(){
        // given
        Tray tray = Tray.builder("A1").build();
        for (int productNumber = 0 ; productNumber < Tray.AMOUNT_OF_PRODUCTS ; productNumber++){
            tray.addProduct(new Product("Mleko"));
        }
        // when
        boolean success = tray.addProduct(new Product("Mleko"));
        // then
        assertFalse(success);
    }

}