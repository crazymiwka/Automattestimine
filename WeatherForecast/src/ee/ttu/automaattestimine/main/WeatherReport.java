package ee.ttu.automaattestimine.main;

import org.json.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WeatherReport {
    private String cityName;
    private String measuringUntis;
    private final String API_KEY = "28d5a41fc4b79c4db1d791e2d5c40cb3";
    private double currentTemperature;
    private JSONArray forecast;
    private String cityCoordinates;
    private Writer writer;
    private WeatherRequest weatherRequest;

    WeatherReport(String cityName, String measuringUntis, Writer writer, WeatherRequest weatherRequest){
        this.cityName = cityName;
        this.measuringUntis = measuringUntis;
        this.writer = writer;
        this.weatherRequest = weatherRequest;
        try {
            this.extractWeatherJSON(this.getJSON("find"));
            this.extractForecastJSON(this.getJSON("forecast"));
            this.cityCoordinates = this.extractCoordinates(this.getJSON("forecast"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCityName() {
        return cityName;
    }

    public String getMeasuringUntis() {
        return measuringUntis;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public boolean writeReport() {
        HashMap<String, Double> forecastTempMax = new HashMap<>();
        HashMap<String, Double> forecastTempMin = new HashMap<>();
        for (int i = 0; i < 40; i ++){
            String fragmentData = forecast.getJSONObject(i).getString("dt_txt").substring(0, 10);
            double fragmentTemp = forecast.getJSONObject(i).getJSONObject("main").getDouble("temp");
            forecastTempMax.put(fragmentData, fragmentTemp);
            forecastTempMin.put(fragmentData, fragmentTemp);
        }

        for (int i = 0; i < 40; i++) {
            String fragmentData = forecast.getJSONObject(i).getString("dt_txt").substring(0, 10);
            double fragmentTemp = forecast.getJSONObject(i).getJSONObject("main").getDouble("temp");
            if (forecastTempMax.containsKey(fragmentData) && forecastTempMax.get(fragmentData) < fragmentTemp){
                forecastTempMax.put(fragmentData, fragmentTemp);
            } else if (forecastTempMin.containsKey(fragmentData) && forecastTempMin.get(fragmentData) > fragmentTemp) {
                forecastTempMin.put(fragmentData, fragmentTemp);
            }
        }

        List<String> temperatureToWrite = Arrays.asList(cityName, cityCoordinates,
                "\nCurrent temperature: " + Double.toString(currentTemperature) + " °C",
                "\nForecast:",
                "1 Day:",
                "Max: " + forecastTempMax.get(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(1))) + " °C",
                "Min: " + forecastTempMin.get(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(1))) + " °C",
                "\n2 Day:",
                "Max: " + forecastTempMax.get(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(2))) + " °C",
                "Min: " + forecastTempMin.get(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(2))) + " °C",
                "\n3 Day:",
                "Max: " + forecastTempMax.get(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(3))) + " °C",
                "Min: " + forecastTempMin.get(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(3))) + " °C");
        return writer.write(cityName, temperatureToWrite);
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public JSONArray getForecast() {
        return forecast;
    }

    private String extractCoordinates(JSONObject jsonObject){
        return jsonObject.getJSONObject("city").getJSONObject("coord").toString();
    }

    private void extractForecastJSON(JSONObject jsonObject) {
        this.forecast = jsonObject.getJSONArray("list");
    }

    private void extractWeatherJSON(JSONObject jsonObject){
        this.currentTemperature =
                jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp");
    }

    private String generateLink(String requestType){
        return "https://api.openweathermap.org/data/2.5/" + requestType + "?q=" + this.cityName + "&units="
                        + this.measuringUntis + "&APPID=" + API_KEY;
    }

    JSONObject getJSON(String linkType) throws IOException {
        return weatherRequest.request(generateLink(linkType));
    }
}