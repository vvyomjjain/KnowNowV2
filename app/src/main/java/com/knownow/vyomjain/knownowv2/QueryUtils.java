package com.knownow.vyomjain.knownowv2;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vyom Jain on 02-09-2017.
 */

public final class QueryUtils {

    //TAG for log messages
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //only for news
    private static List<Article> extractFeatureFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Article> earthquakes = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            String source = baseJsonResponse.getString("source");
            JSONArray articles = baseJsonResponse.getJSONArray("articles");

            for(int i=0; i< articles.length(); i++){
                JSONObject currentNews = articles.getJSONObject(i);

                String author = currentNews.getString("author");
                String title = currentNews.getString("title");
                String description = currentNews.getString("description");
                String newsUrl = currentNews.getString("url");
                String urlToImage = currentNews.getString("urlToImage");
                String publishedAt = currentNews.getString("publishedAt");

                Article earthquake = new Article(title, description, author, newsUrl, urlToImage, publishedAt, source);
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

    //only for news
    public static List<Article> fetchNews(String newsUrl){

        Log.i(LOG_TAG, "TEST: fetchNews() method called");
        URL url = createUrl(newsUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Article> earthquakes = extractFeatureFromJson(jsonResponse);

        return earthquakes;
    }

    //only for weather
    public static WeatherObject fetchWeather(String weatherUrl){

        Log.i(LOG_TAG, "fetchNews() is called");
        URL url = createUrl(weatherUrl);

        String weatherJsonResponse = null;
        try{
            weatherJsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem in fetching news JSON", e);
        }

        WeatherObject currentWeather = extractWeatherFromJson(weatherJsonResponse);
        return currentWeather;
    }

    //only for weather
    public static WeatherObject extractWeatherFromJson(String weatherJson){

        if (TextUtils.isEmpty(weatherJson)){
            return null;
        }

        WeatherObject currentWeather = null;

        try {
            JSONObject jsonResponse = new JSONObject(weatherJson);

            String visibility = jsonResponse.getString("visibility");
            String cityName = jsonResponse.getString("name");
            String countryCode = jsonResponse.getJSONObject("sys").getString("country");

            JSONObject main = jsonResponse.getJSONObject("main");
            String temp = main.getString("temp");
            String humidity = main.getString("humidity");
            String pressure = main.getString("pressure");
            String tempMin = main.getString("temp_min");
            String tempMax = main.getString("temp_max");

            JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("main");
            String icon = weather.getString("icon");

            JSONObject wind = jsonResponse.getJSONObject("wind");
            String windSpeed = wind.getString("speed");
            String windDirection = wind.getString("deg");

            String clouds = jsonResponse.getJSONObject("clouds").getString("all");

            currentWeather = new WeatherObject(cityName + ", " + countryCode, temp, icon, description, humidity, pressure, windSpeed, windDirection, tempMin, tempMax, visibility, clouds);
        }catch (Exception e){
            Log.e(LOG_TAG, "extractWeatherFromJson() me exception hua h", e);
        }

        return currentWeather;
    }

    //only for forecast
    public static List<WeatherForecastObject> fetchForecast(String forecastUrl){
        Log.i(LOG_TAG, "fetchForecast() is called...");
        URL url = createUrl(forecastUrl);

        String forecastJsonResponse = null;
        try{
            forecastJsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem in fetching forecast", e);
        }

        List<WeatherForecastObject> forecastObjects = extractForecastFromJson(forecastJsonResponse);
        return forecastObjects;
    }

    //for forecast
    public static List<WeatherForecastObject> extractForecastFromJson(String forecastJson){
        if(TextUtils.isEmpty(forecastJson)){
            return null;
        }

        ArrayList<WeatherForecastObject> weatherForecastObjects = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(forecastJson);

            int cnt = baseJsonResponse.getInt("cnt");
            JSONArray list = baseJsonResponse.getJSONArray("list");

            for(int i=0; i<cnt; i++){
                JSONObject listItem = list.getJSONObject(i);
                String temp = listItem.getJSONObject("main").getString("temp");
                JSONArray weather = listItem.getJSONArray("weather");
                String icon = weather.getJSONObject(0).getString("icon");
                String time = listItem.getString("dt_txt");

                WeatherForecastObject weatherForecastObject = new WeatherForecastObject(temp, icon, time);
                weatherForecastObjects.add(weatherForecastObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return weatherForecastObjects;
    }
}
