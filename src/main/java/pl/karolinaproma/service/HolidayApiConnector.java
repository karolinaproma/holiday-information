package pl.karolinaproma.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.karolinaproma.domain.Holiday;
import pl.karolinaproma.domain.HolidaySearchCriteria;
import pl.karolinaproma.domain.ResponseFromApi;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

@Component
public class HolidayApiConnector {

    private static final String URL_TEMPLATE = "?key=%s&country=%s&year=%s&month=%s&day=%s&%s=true";

    @Value("${api.key}")
    private String key;

    @Value("${api.url}")
    private String urlAddress;

    @Value("${search.direction}")
    private String searchDirection;

    private Logger logger = Logger.getLogger(HolidayApiConnector.class.getName());

    public Holiday findHoliday(HolidaySearchCriteria searchCriteria){
        Holiday holiday = new Holiday();
        try {
            URL holidayUrl = new URL(prepareUrl(searchCriteria));
            HttpsURLConnection conn = (HttpsURLConnection)holidayUrl.openConnection();
            InputStream response = conn.getInputStream();
            ResponseFromApi responseFromApi = parseResponse(response);
            if(!responseFromApi.getHolidays().isEmpty()){
                holiday = responseFromApi.getHolidays().get(0);
            }
        } catch(IOException e){
            logger.warning(e.getMessage());
        }
        return holiday;
    }

    private String prepareUrl(HolidaySearchCriteria searchCriteria){
        return urlAddress + String.format(URL_TEMPLATE,key,
                searchCriteria.getCountry(),
                searchCriteria.getYear(),
                searchCriteria.getMonth(),
                searchCriteria.getDay(),
                searchDirection);
    }

    private ResponseFromApi parseResponse(InputStream response){
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseFromApi responseFromApi = new ResponseFromApi();
        String responseString = "";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(response, "UTF8"))){
            responseString = br.readLine();
            responseFromApi = objectMapper.readValue(responseString, ResponseFromApi.class);
        }catch(IOException e){
            logger.warning(e.getMessage());
        }
        return responseFromApi;
    }
}
