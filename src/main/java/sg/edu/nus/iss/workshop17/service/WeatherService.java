package sg.edu.nus.iss.workshop17.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.workshop17.model.openweather.Weather;


@Service
public class WeatherService {
    //Inject values from the application.properties file
    @Value("${workshop17.open.weather.url}")
    private String openWeatherUrl;

    @Value("${workshop17.open.weather.api.key}")
    private String openWeatherApiKey;


    public Optional<Weather> getWeather(String city, String unitMeasurement)
        throws IOException{
        System.out.println("openWeatherUrl" + openWeatherUrl);
        System.out.println("openWeatherApiKey" + openWeatherApiKey);
        //Building the uri to send to the website
        String weatherUrl = UriComponentsBuilder
                              .fromUriString(openWeatherUrl)
                              .queryParam("q", 
                                    city.replaceAll(" ", "+"))
                              .queryParam("units", unitMeasurement)
                              .queryParam("appId", openWeatherApiKey)
                              .toUriString();
        //Create client object that can perform HTTP requests
        RestTemplate template= new RestTemplate();
        //Calling the URI through the client object
        ResponseEntity<String> r  = template.getForEntity(weatherUrl, 
                String.class);
                //r.getBody() is a Json string
        //Create weather object with the create method with the Json String
        Weather w = Weather.create(r.getBody());
        //If weather object is not empty, return it
        if(w != null)
            return Optional.of(w);
        return Optional.empty();
    }
}
