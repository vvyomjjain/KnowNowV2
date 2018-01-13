package com.knownow.vyomjain.knownowv2;

/**
 * Created by Vyom Jain on 11-11-2017.
 */

public class WeatherForecastObject {

    private String temp;
    private String icon;
    private String time;

    public WeatherForecastObject(String temp, String icon, String time){
        this.temp = temp;
        this.icon = icon;
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemp() {
        return temp;
    }

    public String getTime() {
        return time;
    }
}
