package ee.ttu.automaattestimine.main;


import java.nio.file.*;
import java.util.*;

public class Reports {
    private ArrayList<WeatherReport> reports = new ArrayList<>();
    private String cityList;
    private Writer writer;
    private Reader reader;
    private WeatherRequest weatherRequest;

    Reports(String cityList){
        this.cityList = cityList;
    }

    Reports(Writer writer, Reader reader, WeatherRequest weatherRequest){
        this.writer = writer;
        this.reader = reader;
        this.weatherRequest = weatherRequest;
        String fileContents;
        fileContents = this.reader.read(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\input.txt"));
        fileContents = fileContents.replace("\n", ",");
        this.cityList = fileContents;
        this.createReports();
    }

    private void createReports(){
        ArrayList<String> cityNameList = new ArrayList<>(Arrays.asList(cityList.split(",")));
        for (String city : cityNameList){
            reports.add(new WeatherReport(city, "Metric", writer, weatherRequest));
        }
    }

    public void generateReportFiles(){
        for (WeatherReport report : reports){
            report.writeReport();
        }
    }
}