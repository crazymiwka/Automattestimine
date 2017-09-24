package ee.ttu.automaattestimine.main;

import java.util.Date;

public class Weather {
    private Date todayDate;
    private Date todayDatePlusOneDay;
    private Date todayDatePlusTwoDay;
    private Date todayDatePlusThreeDay;
    private String coordinates;
    private int todayDatePlusOneDayMinTemperature;
    private int todayDatePlusOneDayMaxTemperature;
    private int todayDatePlusTwoDayMinTemperature;
    private int todayDatePlusTwoDayMaxTemperature;
    private int todayDatePlusThreeDayMinTemperature;
    private int todayDatePlusThreeDayMaxTemperature;

    public Date getTodayDate() {
        return todayDate;
    }

    public Date getTodayDatePlusOneDay() {
        return todayDatePlusOneDay;
    }

    public Date getTodayDatePlusTwoDay() {
        return todayDatePlusTwoDay;
    }

    public Date getTodayDatePlusThreeDay() {
        return todayDatePlusThreeDay;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public int getTodayDatePlusOneDayMinTemperature() {
        return todayDatePlusOneDayMinTemperature;
    }

    public int getTodayDatePlusOneDayMaxTemperature() {
        return todayDatePlusOneDayMaxTemperature;
    }

    public int getTodayDatePlusTwoDayMinTemperature() {
        return todayDatePlusTwoDayMinTemperature;
    }

    public int getTodayDatePlusTwoDayMaxTemperature() {
        return todayDatePlusTwoDayMaxTemperature;
    }

    public int getTodayDatePlusThreeDayMinTemperature() {
        return todayDatePlusThreeDayMinTemperature;
    }

    public int getTodayDatePlusThreeDayMaxTemperature() {
        return todayDatePlusThreeDayMaxTemperature;
    }
}