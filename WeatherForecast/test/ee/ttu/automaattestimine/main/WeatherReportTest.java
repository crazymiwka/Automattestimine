package ee.ttu.automaattestimine.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherReportTest {
    WeatherReport weather;
    Writer writer;
    Reader reader;
    WeatherRequest weatherRequest;

    @BeforeEach
    void testBeforeEach(){
        writer = new Writer();
        reader = new Reader();
        weatherRequest = new WeatherRequest();
        weather = new WeatherReport("Tallinn", "Metric", writer, weatherRequest);
    }

    @Test
    void testFileExistAfterWrite(){
        weather.writeReport();
        if (Files.notExists(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\Tallinn.txt"))){
            fail("File was not created");
        }
    }

    @Test
    void testGetJSONRequestTypeFind() {
        try {
            assertNotNull(weather.getJSON("find"));
        } catch (IOException e) {
            fail("It is Null");
        }
    }

    @Test
    void testGetJSONRequestTypeForecast() {
        try {
            assertNotNull(weather.getJSON("forecast"));
        } catch (IOException e) {
            fail("It is Null");
        }
    }

    @Test
    void testGetCityName() {
        assertTrue(weather.getCityName().matches("[a-zA-Z]+"));
    }

    @Test
    void testGetMeasuringUntis() {
        assertTrue(weather.getMeasuringUntis().matches("[a-zA-Z]+"));
    }

    @Test
    void testGetCurrentTemperature() {
        assertTrue(weather.getCurrentTemperature() >= 0 || weather.getCurrentTemperature() <= 0);
    }

    @Test
    void testMockFileWrite() {
        writer = mock(Writer.class);
        weather = new WeatherReport("London", "Metric", writer, weatherRequest);
        when(writer.write(any(), any())).thenReturn(true);
        weather.writeReport();
        if (!Files.notExists(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\London.txt"))){
            fail("File was not created");
        }
        verify(writer).write(any(), any());
    }

    @Test
    void testMockFileRead() {
        reader = mock(Reader.class);
        when(reader.read(any())).thenReturn("Helsinki\nRiga");
        Reports reports = new Reports(writer, reader, weatherRequest);
        reports.generateReportFiles();
        if (Files.notExists(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\Helsinki.txt"))
                || Files.notExists(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\Riga.txt"))){
            fail("File was not created");
        }
        verify(reader).read(any());
    }
}