package ee.ttu.automaattestimine.test;

import static org.junit.Assert.*;

import ee.ttu.automaattestimine.main.Weather;
import static org.junit.Assert.*;
import java.util.Date;

public class WeatherTest {
    Weather weather;

    @org.junit.Before
    public void setUp() throws Exception {
        weather = new Weather();
    }

    @org.junit.Test
    public void testTodayDateAvailability() {
        Date date = new Date();
        assertEquals(weather.getTodayDate(), date.getTime());
    }

    @org.junit.Test
    public void testNextDaysAvailability() {
        Date date = new Date();
        assertEquals(weather.getTodayDatePlusOneDay(), date.getTime() + 60*60*24);
        assertEquals(weather.getTodayDatePlusTwoDay(), date.getTime() + 2*60*60*24);
        assertEquals(weather.getTodayDatePlusThreeDay(), date.getTime() + 3*60*60*24);
    }

    @org.junit.Test
    public void testCoordinatesAvailability() {
        assertNotNull(weather.getCoordinates());
        assertTrue(weather.getCoordinates().contains("Lat:"));
        assertTrue(weather.getCoordinates().contains("Ion:"));
    }

    @org.junit.Test
    public void testNextDaysTemperatureDifference() {
        assertTrue(weather.getTodayDatePlusOneDayMinTemperature() < weather.getTodayDatePlusOneDayMaxTemperature());
        assertTrue(weather.getTodayDatePlusTwoDayMinTemperature() < weather.getTodayDatePlusTwoDayMaxTemperature());
        assertTrue(weather.getTodayDatePlusThreeDayMinTemperature() < weather.getTodayDatePlusThreeDayMaxTemperature());
    }
}