package Gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import smhi.Forecasts;
import smhi.HourlyForecast;
import smhi.SMHIWeatherAPI;
import smhi.WeatherConditionCodes;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by kasper on 2016-05-12.
 */


public class Controller {
    private SMHIWeatherAPI weatherApi;
    private Properties prop;
    private InputStream is;

    @FXML
    Label weatherIconLabel;
    @FXML
    Label temperatureLabel;
    @FXML
    Label windLabel;
    @FXML
    Label rainForecastLabel;

    @FXML
    Label timeLabel;
    @FXML
    Label dateLabel;




    public Controller() {
        prop = new Properties();
        try {
            File jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            System.out.println(jarPath.getAbsolutePath());
            String propFileName = jarPath.getAbsolutePath();
            if(propFileName.endsWith(".jar")){
                propFileName = jarPath.getParentFile().getAbsolutePath();
            }
            System.out.println(propFileName+"/config.properties");
            prop.load(new FileInputStream(propFileName+"/config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.weatherApi = new SMHIWeatherAPI(prop.getProperty("lat"), prop.getProperty("longitude"));
    }


    @FXML
    public void initialize() {
        startWeatherUpdater();
        startTimeAndDateUpdater();
        setWeatherFont(weatherIconLabel);
    }

    /**
     * Sets up how often the digital clock should update
     */
    private void startTimeAndDateUpdater() {
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Calendar now = Calendar.getInstance();
            timeLabel.setText(new SimpleDateFormat("HH:mm").format(now.getTime()));
            dateLabel.setText(getDatePrint());

        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

    }


    private void startWeatherUpdater() {
        Runnable updateWeather = () -> {
            Forecasts f = weatherApi.getForecasts();
            HourlyForecast forecast = f.getCurrentWeather();

            Platform.runLater(() -> {
                weatherIconLabel.setText(WeatherConditionCodes.fromInt(forecast.getWeatherSymbol()).toString());
                temperatureLabel.setText(forecast.getTemp() + " °C");
                windLabel.setText(forecast.getWindVelocity() + " m/s " + "max " + forecast.getWindGust() + " m/s");
                rainForecastLabel.setText("Rain: " + forecast.getRainfallMeanAmount() + " mm");

            });
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(updateWeather, 0, 10, TimeUnit.MINUTES);
    }


    private String getDatePrint() {
        Calendar now = Calendar.getInstance();
        String monthOfYear = new SimpleDateFormat("EEEE d MMMM, y", Locale.ENGLISH).format(now.getTime());
        return monthOfYear;
    }

    /**
     * Sets the weather font from https://erikflowers.github.io/weather-icons/
     * to a Label
     *
     * @param weatherLabel
     */
    private void setWeatherFont(Label weatherLabel) {
        Font f = null;
        try {
            URL url = getClass().getResource("/fonts/weathericons-regular-webfont.ttf");
            f = Font.loadFont(url.openStream(), 120);
        } catch (IOException e) {
            e.printStackTrace();
        }
        weatherLabel.setFont(f);
    }


}
