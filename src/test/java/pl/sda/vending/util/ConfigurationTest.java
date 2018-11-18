package pl.sda.vending.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationTest {

    private Configuration configuration;

    @Before
    public void init() {
        configuration = new Configuration();
    }

    @Test
    public void shouldReturnExistingValue() {
        // given
        Long defaultNumber = 10L;
        // when
        Long existing = configuration.getLongProperty("test.property.long" , defaultNumber);
        // then
        assertEquals( (Long)20L , existing);
    }

    @Test
    public void shouldReturnExistingNameValue() {
        // given
        String defaultName = "name";
        String testPropertyString = "test.property.string";
        // when
        String existingString = configuration.getStringProperty(testPropertyString , defaultName);
        // then
        assertEquals("qwerty" , existingString);
    }

    @Test
    public void shouldReturnDefaultStringValueWhenPropertyisUnknown() {
        // given
        String uknownPropertyName = "dfekjfnksfsewfnewfesqcsac";
        String expectedDefault = ".Net sucks! Only Java!";

        //when
        String stringProperty = configuration.getStringProperty(uknownPropertyName, expectedDefault);

        // then
        assertEquals(expectedDefault, stringProperty);

    }

    @Test
    public void shouldReturnDefaultLongValueWhenPropertyisUnknown() {
        // given
        Long expectedDefault = 4L;

        //when
        Long stringProperty = configuration.getLongProperty("hehe", expectedDefault);

        // then
        assertEquals(expectedDefault, stringProperty);

    }

}