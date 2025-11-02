package br.edu.infnet.josecsjuniorapi.model.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.infnet.josecsjuniorapi.model.service.WeatherService;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

@Component
public class OpenMeteoClient implements WeatherService{

    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();
    private final ObjectMapper om = new ObjectMapper();

    public Double obterTemperaturaCelsius(double latitude, double longitude) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast"
                       + "?latitude=" + latitude
                       + "&longitude=" + longitude
                       + "&current_weather=true";

            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(4))
                    .GET()
                    .build();

            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            
            if (resp.statusCode() != 200) 
            	return null;

            Map<?,?> root = om.readValue(resp.body(), Map.class);
            Map<?,?> current = (Map<?,?>) root.get("current_weather");
            
            Double temperatura = current != null ? ((Number) current.get("temperature")).doubleValue() : null;
                        
            return temperatura;            

        } catch (Exception e) {
            return null;
        }
    }
}
