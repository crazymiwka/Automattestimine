package ee.ttu.automaattestimine.main;

import org.json.*;
import java.io.*;
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

    public boolean writeReport(){
        List<String> temperatureToWrite = Arrays.asList(cityName, cityCoordinates,
                "\nCurrent temperature: " + Double.toString(currentTemperature) + " °C",
                "\nForecast: ",
                "1 Day: " + forecast.getJSONObject(8).getJSONObject("main").getDouble("temp_max") + " °C",
                "2 Day: " + forecast.getJSONObject(16).getJSONObject("main").getDouble("temp_max") + " °C",
                "3 Day: " + forecast.getJSONObject(24).getJSONObject("main").getDouble("temp_max") + " °C");
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