package ee.ttu.automaattestimine.main;

import org.json.JSONObject;
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

    @Test
    void testMockExternalAPI() throws IOException {
        weatherRequest = mock(WeatherRequest.class);
        when(weatherRequest.request(any())).thenReturn(new JSONObject("{\"cod\":\"200\",\"message\":0.0367,\"cnt\":40,\"list\":[{\"dt\":1513544400,\"main\":{\"temp\":-1.18,\"temp_min\":-1.18,\"temp_max\":0.01,\"pressure\":1033.18,\"sea_level\":1037.24,\"grnd_level\":1033.18,\"humidity\":100,\"temp_kf\":-1.19},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.56,\"deg\":24.0012},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-17 21:00:00\"},{\"dt\":1513555200,\"main\":{\"temp\":3.93,\"temp_min\":3.93,\"temp_max\":4.82,\"pressure\":1034.22,\"sea_level\":1038.18,\"grnd_level\":1034.22,\"humidity\":100,\"temp_kf\":-0.89},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.21,\"deg\":82.5008},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-18 00:00:00\"},{\"dt\":1513566000,\"main\":{\"temp\":7.51,\"temp_min\":7.51,\"temp_max\":8.11,\"pressure\":1032.95,\"sea_level\":1036.78,\"grnd_level\":1032.95,\"humidity\":81,\"temp_kf\":-0.59},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":0.31,\"deg\":151.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-18 03:00:00\"},{\"dt\":1513576800,\"main\":{\"temp\":8.68,\"temp_min\":8.68,\"temp_max\":8.98,\"pressure\":1031.29,\"sea_level\":1035.23,\"grnd_level\":1031.29,\"humidity\":79,\"temp_kf\":-0.3},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.76,\"deg\":122.505},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-18 06:00:00\"},{\"dt\":1513587600,\"main\":{\"temp\":5.13,\"temp_min\":5.13,\"temp_max\":5.13,\"pressure\":1031.31,\"sea_level\":1035.36,\"grnd_level\":1031.31,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.68,\"deg\":82.5013},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-18 09:00:00\"},{\"dt\":1513598400,\"main\":{\"temp\":3.41,\"temp_min\":3.41,\"temp_max\":3.41,\"pressure\":1031.15,\"sea_level\":1035.23,\"grnd_level\":1031.15,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.7,\"deg\":286},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-18 12:00:00\"},{\"dt\":1513609200,\"main\":{\"temp\":2.6,\"temp_min\":2.6,\"temp_max\":2.6,\"pressure\":1030.51,\"sea_level\":1034.53,\"grnd_level\":1030.51,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.38,\"deg\":308},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-18 15:00:00\"},{\"dt\":1513620000,\"main\":{\"temp\":2.04,\"temp_min\":2.04,\"temp_max\":2.04,\"pressure\":1029.01,\"sea_level\":1033.06,\"grnd_level\":1029.01,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.19,\"deg\":281},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-18 18:00:00\"},{\"dt\":1513630800,\"main\":{\"temp\":4.23,\"temp_min\":4.23,\"temp_max\":4.23,\"pressure\":1027.14,\"sea_level\":1031.3,\"grnd_level\":1027.14,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.18,\"deg\":248.502},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-18 21:00:00\"},{\"dt\":1513641600,\"main\":{\"temp\":9.38,\"temp_min\":9.38,\"temp_max\":9.38,\"pressure\":1026,\"sea_level\":1030.23,\"grnd_level\":1026,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":6.42,\"deg\":240.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-19 00:00:00\"},{\"dt\":1513652400,\"main\":{\"temp\":12.12,\"temp_min\":12.12,\"temp_max\":12.12,\"pressure\":1023.47,\"sea_level\":1027.46,\"grnd_level\":1023.47,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":9.87,\"deg\":236.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-19 03:00:00\"},{\"dt\":1513663200,\"main\":{\"temp\":12.04,\"temp_min\":12.04,\"temp_max\":12.04,\"pressure\":1023.53,\"sea_level\":1027.17,\"grnd_level\":1023.53,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":10.77,\"deg\":238.002},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-19 06:00:00\"},{\"dt\":1513674000,\"main\":{\"temp\":10.92,\"temp_min\":10.92,\"temp_max\":10.92,\"pressure\":1026.05,\"sea_level\":1030.17,\"grnd_level\":1026.05,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":7.1,\"deg\":251},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-19 09:00:00\"},{\"dt\":1513684800,\"main\":{\"temp\":7.29,\"temp_min\":7.29,\"temp_max\":7.29,\"pressure\":1029.91,\"sea_level\":1033.76,\"grnd_level\":1029.91,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.48,\"deg\":338.008},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-19 12:00:00\"},{\"dt\":1513695600,\"main\":{\"temp\":6.14,\"temp_min\":6.14,\"temp_max\":6.14,\"pressure\":1031.66,\"sea_level\":1035.76,\"grnd_level\":1031.66,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.34,\"deg\":357.502},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-19 15:00:00\"},{\"dt\":1513706400,\"main\":{\"temp\":4.27,\"temp_min\":4.27,\"temp_max\":4.27,\"pressure\":1033.55,\"sea_level\":1037.63,\"grnd_level\":1033.55,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.87,\"deg\":11.5002},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-19 18:00:00\"},{\"dt\":1513717200,\"main\":{\"temp\":2.09,\"temp_min\":2.09,\"temp_max\":2.09,\"pressure\":1035.64,\"sea_level\":1039.67,\"grnd_level\":1035.64,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.08,\"deg\":17.0034},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-19 21:00:00\"},{\"dt\":1513728000,\"main\":{\"temp\":6.28,\"temp_min\":6.28,\"temp_max\":6.28,\"pressure\":1037.54,\"sea_level\":1041.47,\"grnd_level\":1037.54,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.1,\"deg\":29.0011},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-20 00:00:00\"},{\"dt\":1513738800,\"main\":{\"temp\":9.18,\"temp_min\":9.18,\"temp_max\":9.18,\"pressure\":1036.65,\"sea_level\":1040.56,\"grnd_level\":1036.65,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.96,\"deg\":27.5062},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-20 03:00:00\"},{\"dt\":1513749600,\"main\":{\"temp\":9.5,\"temp_min\":9.5,\"temp_max\":9.5,\"pressure\":1035.37,\"sea_level\":1039.23,\"grnd_level\":1035.37,\"humidity\":79,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.02,\"deg\":35.5025},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-20 06:00:00\"},{\"dt\":1513760400,\"main\":{\"temp\":6.27,\"temp_min\":6.27,\"temp_max\":6.27,\"pressure\":1036,\"sea_level\":1040.04,\"grnd_level\":1036,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":2.81,\"deg\":22.0012},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-20 09:00:00\"},{\"dt\":1513771200,\"main\":{\"temp\":6.5,\"temp_min\":6.5,\"temp_max\":6.5,\"pressure\":1035.93,\"sea_level\":1039.91,\"grnd_level\":1035.93,\"humidity\":97,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":3.92,\"deg\":17.0004},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-20 12:00:00\"},{\"dt\":1513782000,\"main\":{\"temp\":6.67,\"temp_min\":6.67,\"temp_max\":6.67,\"pressure\":1035.47,\"sea_level\":1039.31,\"grnd_level\":1035.47,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":4.42,\"deg\":357.001},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-20 15:00:00\"},{\"dt\":1513792800,\"main\":{\"temp\":5.4,\"temp_min\":5.4,\"temp_max\":5.4,\"pressure\":1035.18,\"sea_level\":1039,\"grnd_level\":1035.18,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":4.42,\"deg\":350.502},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-20 18:00:00\"},{\"dt\":1513803600,\"main\":{\"temp\":5.25,\"temp_min\":5.25,\"temp_max\":5.25,\"pressure\":1034.66,\"sea_level\":1038.67,\"grnd_level\":1034.66,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":4.31,\"deg\":354.501},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-20 21:00:00\"},{\"dt\":1513814400,\"main\":{\"temp\":6.24,\"temp_min\":6.24,\"temp_max\":6.24,\"pressure\":1035.32,\"sea_level\":1039.26,\"grnd_level\":1035.32,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":4.56,\"deg\":350.006},\"rain\":{\"3h\":0.02},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-21 00:00:00\"},{\"dt\":1513825200,\"main\":{\"temp\":7.53,\"temp_min\":7.53,\"temp_max\":7.53,\"pressure\":1033.88,\"sea_level\":1037.67,\"grnd_level\":1033.88,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":4.53,\"deg\":357.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-21 03:00:00\"},{\"dt\":1513836000,\"main\":{\"temp\":7.86,\"temp_min\":7.86,\"temp_max\":7.86,\"pressure\":1032.62,\"sea_level\":1036.48,\"grnd_level\":1032.62,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":3.9,\"deg\":24.5004},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-21 06:00:00\"},{\"dt\":1513846800,\"main\":{\"temp\":5.76,\"temp_min\":5.76,\"temp_max\":5.76,\"pressure\":1033.41,\"sea_level\":1037.33,\"grnd_level\":1033.41,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":4.01,\"deg\":9.50284},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-21 09:00:00\"},{\"dt\":1513857600,\"main\":{\"temp\":5.21,\"temp_min\":5.21,\"temp_max\":5.21,\"pressure\":1033.91,\"sea_level\":1037.86,\"grnd_level\":1033.91,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":4.65,\"deg\":5.00507},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-21 12:00:00\"},{\"dt\":1513868400,\"main\":{\"temp\":4.66,\"temp_min\":4.66,\"temp_max\":4.66,\"pressure\":1033.8,\"sea_level\":1037.81,\"grnd_level\":1033.8,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.21,\"deg\":346.001},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-21 15:00:00\"},{\"dt\":1513879200,\"main\":{\"temp\":4.32,\"temp_min\":4.32,\"temp_max\":4.32,\"pressure\":1033.53,\"sea_level\":1037.47,\"grnd_level\":1033.53,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.21,\"deg\":4.50137},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-21 18:00:00\"},{\"dt\":1513890000,\"main\":{\"temp\":4.3,\"temp_min\":4.3,\"temp_max\":4.3,\"pressure\":1033.7,\"sea_level\":1037.61,\"grnd_level\":1033.7,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.63,\"deg\":9.50046},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-21 21:00:00\"},{\"dt\":1513900800,\"main\":{\"temp\":6.12,\"temp_min\":6.12,\"temp_max\":6.12,\"pressure\":1034.5,\"sea_level\":1038.41,\"grnd_level\":1034.5,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.26,\"deg\":6.00134},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-22 00:00:00\"},{\"dt\":1513911600,\"main\":{\"temp\":7.64,\"temp_min\":7.64,\"temp_max\":7.64,\"pressure\":1032.85,\"sea_level\":1036.75,\"grnd_level\":1032.85,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":3.82,\"deg\":12.51},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-22 03:00:00\"},{\"dt\":1513922400,\"main\":{\"temp\":7.78,\"temp_min\":7.78,\"temp_max\":7.78,\"pressure\":1031.27,\"sea_level\":1035.2,\"grnd_level\":1031.27,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":2.71,\"deg\":17.504},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-22 06:00:00\"},{\"dt\":1513933200,\"main\":{\"temp\":4.81,\"temp_min\":4.81,\"temp_max\":4.81,\"pressure\":1031.04,\"sea_level\":1035.16,\"grnd_level\":1031.04,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.06,\"deg\":2.00043},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-22 09:00:00\"},{\"dt\":1513944000,\"main\":{\"temp\":2.41,\"temp_min\":2.41,\"temp_max\":2.41,\"pressure\":1030.88,\"sea_level\":1034.86,\"grnd_level\":1030.88,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.42,\"deg\":351.509},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-22 12:00:00\"},{\"dt\":1513954800,\"main\":{\"temp\":1.44,\"temp_min\":1.44,\"temp_max\":1.44,\"pressure\":1029.49,\"sea_level\":1033.55,\"grnd_level\":1029.49,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.55,\"deg\":355.5},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-22 15:00:00\"},{\"dt\":1513965600,\"main\":{\"temp\":0.93,\"temp_min\":0.93,\"temp_max\":0.93,\"pressure\":1027.95,\"sea_level\":1032.02,\"grnd_level\":1027.95,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.31,\"deg\":4.0029},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-22 18:00:00\"}],\"city\":{\"id\":1850144,\"name\":\"Tokyo\",\"coord\":{\"lat\":35.6828,\"lon\":139.759},\"country\":\"JP\",\"population\":12445327}}"));
        Reports reports = new Reports(writer, reader, weatherRequest);
        reports.generateReportFiles();
        if (Files.notExists(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\Helsinki.txt"))
                || Files.notExists(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\Riga.txt"))){
            fail("File was not created");
        }
        verify(weatherRequest, atLeastOnce()).request(any());
    }
}