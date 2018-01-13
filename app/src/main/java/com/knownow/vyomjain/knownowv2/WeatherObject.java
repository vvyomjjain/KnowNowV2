package com.knownow.vyomjain.knownowv2;

import static com.knownow.vyomjain.knownowv2.R.drawable.th;

/**
 * Created by Vyom Jain on 09-09-2017.
 */

public class WeatherObject {

    private String cityCountry;
    private String temp;
    private String icon;
    private String description;
    private String humidity;
    private String pressure;
    private String windSpeed;
    private String windDirection;
    private String tempMin;
    private String tempMax;
    private String visibility;
    private String clouds;

    public WeatherObject(String cityCountry,
                         String temp,
                         String icon,
                         String description,
                         String humidity,
                         String pressure,
                         String windSpeed,
                         String windDirection,
                         String tempMin,
                         String tempMax,
                         String visibility,
                         String clouds){
        this.cityCountry = cityCountry;
        this.temp = temp;
        this.icon = icon;
        this.description = description;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.visibility = visibility;
        this.clouds = clouds;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public String getTemp() {
        return temp;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getClouds() {
        return clouds;
    }

    public String getVisibility() {
        return visibility;
    }
}
