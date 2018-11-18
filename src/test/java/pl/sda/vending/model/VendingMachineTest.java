package pl.sda.vending.model;

import org.junit.Test;
import pl.sda.vending.util.Configuration;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class VendingMachineTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenZeroRowsConfigured() {
        // given
        Configuration config = mock(Configuration.class);
        doReturn(0L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // when
        new VendingMachine(config);
        // then
        fail("Exception should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenRowsConfiguredisMoreThanNine() {
        // given
        Configuration config4 = mock(Configuration.class);
        doReturn(10L).when(config4).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config4).getLongProperty(eq("machine.size.cols"), anyLong());
        // when
        new VendingMachine(config4);
        // then
        fail("Exception should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenZeroColumnsConfigured() {
        // given
        Configuration config2 = mock(Configuration.class);
        doReturn(4L).when(config2).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(0L).when(config2).getLongProperty(eq("machine.size.cols"), anyLong());
        // when
        new VendingMachine(config2);
        // then
        fail("Exception should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenColumnsConfiguredIsMoreThanTwentySix() {
        // given
        Configuration config3 = mock(Configuration.class);
        doReturn(4L).when(config3).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(27L).when(config3).getLongProperty(eq("machine.size.cols"), anyLong());
        // when
        new VendingMachine(config3);
        // then
        fail("Exception should be raised");
    }

    @Test
    public void shouldDontThrowExceptionWhenVendingMachinHasCorrectsParameters() {
        // given
        Configuration config5 = mock(Configuration.class);
        doReturn(3L).when(config5).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(3L).when(config5).getLongProperty(eq("machine.size.cols"), anyLong());
        // when
        new VendingMachine(config5);
        // then
        assertEquals(3L, 3L);

    }

    @Test
    public void shouldBeAbelToAddTrayToEmptySpot() {
        // given
        Tray tray = Tray.builder("A2").build();
        Configuration mock = mock(Configuration.class);

        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());

        VendingMachine testedMachine = new VendingMachine(config);
        // when
        boolean placed = testedMachine.placeTrayAtPosition(tray);
        // then
        assertTrue(placed);
        assertEquals(tray, testedMachine.getTrayAtPosition(0, 1).get());
    }
    @Test
    public void shouldBeAbelToAddTrayToTakenSpot() {
        // given
        Tray tray = Tray.builder("A2").build();
        Configuration mock = mock(Configuration.class);

        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());

        Tray tray1 = Tray.builder("A2").build();

        VendingMachine testedMachine = new VendingMachine(config);
        // when
        boolean firstTrayPlacementResult = testedMachine.placeTrayAtPosition(tray);
        boolean secondTrayPlacementResult = testedMachine.placeTrayAtPosition(tray1);
        // then
        assertFalse(secondTrayPlacementResult);
        assertEquals(tray, testedMachine.getTrayAtPosition(0, 1).get());
    }
    @Test
    public void shouldBeAbelToAddTrayToNowExist() {
        // given
        Tray tray = Tray.builder("$$").build();
        Configuration mock = mock(Configuration.class);

        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());

        VendingMachine testedMachine = new VendingMachine(config);
        // when
        boolean placed = testedMachine.placeTrayAtPosition(tray);
        // then
        assertFalse(placed);
    }

    @Test
    public void shouldReturnEmptyOptionalIfTrayCouldNotBeReturn(){
        // given
        String traySymbol = "A1";
        Configuration mockedConfiguration = getMockedConfiguration();
        VendingMachine testedMachine = new VendingMachine(mockedConfiguration);
        // when
        Optional<Tray> removedTray = testedMachine.removeTrayWithSymbol(traySymbol);
        // then
        assertFalse(removedTray.isPresent());
    }

    @Test
    public void shouldBeAbloeToRemoveTray(){
        //given
        String traySymbol = "B2";
        Configuration mockedConfiguration = getMockedConfiguration();
        VendingMachine testedMachine = new VendingMachine(mockedConfiguration);
        Tray tray = Tray.builder(traySymbol).build();
        testedMachine.placeTrayAtPosition(tray);
        //when
        Optional<Tray> removedTray = testedMachine.removeTrayWithSymbol(traySymbol);
        //then
        assertTrue(removedTray.isPresent());
        assertEquals(tray , removedTray.get());
    }

    @Test
    public void removedTrayShouldNotBeAvailable(){
        //given
        String traySymbol = "C3";
        Configuration mockedConfiguration = getMockedConfiguration();
        VendingMachine testedMachine = new VendingMachine(mockedConfiguration);
        Tray tray = Tray.builder(traySymbol).build();
        testedMachine.placeTrayAtPosition(tray);
        // when
        testedMachine.removeTrayWithSymbol(traySymbol);
        // then
        assertEquals(Optional.empty() , testedMachine.getTrayAtPosition(2 , 2));

    }

    private Configuration getMockedConfiguration() {

        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());

        return config;
    }

}