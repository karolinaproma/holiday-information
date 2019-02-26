package pl.karolinaproma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.karolinaproma.domain.*;

import java.time.LocalDate;
import java.util.logging.Logger;

@Service
public class HolidayInformationServiceImpl implements HolidayInformationService {

    private static final String PREVIOUS_DIRECTION = "previous";

    @Value("${search.direction}")
    private String searchDirection = "previous";

    private Logger logger = Logger.getLogger(HolidayInformationServiceImpl.class.getName());

    @Autowired
    private HolidayApiConnector holidayApiConnector;

    @Override
    public String findCommonHoliday(HolidayForm holidayForm){

        String firstCountryCode = holidayForm.getCountryCode1();
        String secondCountryCode = holidayForm.getCountryCode2();

        Holiday holidayInFirstCountry = holidayApiConnector.findHoliday(new HolidaySearchCriteria(firstCountryCode, LocalDate.parse(holidayForm.getDate())));
        Holiday holidayInSecondCountry = holidayApiConnector.findHoliday(new HolidaySearchCriteria(secondCountryCode, LocalDate.parse(holidayForm.getDate())));

        LocalDate dateForFirstCountry = LocalDate.parse(holidayInFirstCountry.getDate());
        LocalDate dateForSecondCountry = LocalDate.parse(holidayInSecondCountry.getDate());

        while(!dateForFirstCountry.equals(dateForSecondCountry)){
            if(searchForFirstCountry(holidayInFirstCountry, holidayInSecondCountry)){
                holidayInFirstCountry = searchForOneCountry(firstCountryCode, dateForFirstCountry);
                dateForFirstCountry = LocalDate.parse(holidayInFirstCountry.getDate());
            } else {
                holidayInSecondCountry = searchForOneCountry(secondCountryCode, dateForSecondCountry);
                dateForSecondCountry = LocalDate.parse(holidayInSecondCountry.getDate());
            }
        }
        return prepareResult(holidayInFirstCountry, holidayInSecondCountry);
    }

    private boolean searchForFirstCountry(Holiday holidayInFirstCountry, Holiday holidayInSecondCountry){
        LocalDate dateForFirstCountry = LocalDate.parse(holidayInFirstCountry.getDate());
        LocalDate dateForSecondCountry = LocalDate.parse(holidayInSecondCountry.getDate());
        return PREVIOUS_DIRECTION.equals(searchDirection) && dateForFirstCountry.isAfter(dateForSecondCountry);
    }

    private Holiday searchForOneCountry(String countryCode, LocalDate date){
        return holidayApiConnector.findHoliday(new HolidaySearchCriteria(countryCode, date));
    }

    private String prepareResult(Holiday previousHolidayInFirstCountry, Holiday previousHolidayInSecondCountry){
        String holidaysAsString = "";
        ObjectMapper objectMapper = new ObjectMapper();
        ParallelHoliday parallelHolidays = ParallelHoliday.builder()
                .date(previousHolidayInFirstCountry.getDate())
                .name1(previousHolidayInFirstCountry.getName())
                .name2(previousHolidayInSecondCountry.getName())
                .build();
        try {
            holidaysAsString = objectMapper.writeValueAsString(parallelHolidays);
        } catch (JsonProcessingException e) {
            logger.warning(e.getMessage());
        }
        return holidaysAsString;
    }
}
