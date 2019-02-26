package pl.karolinaproma.domain;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HolidaySearchCriteria {
    private String country;
    private String year;
    private String month;
    private String day;

    public HolidaySearchCriteria(String countryCode, LocalDate date){
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonthValue());
        String day = String.valueOf(date.getDayOfMonth());

        this.country = countryCode;
        this.year = year;
        this.month = convertToTwoCharString(month);
        this.day = convertToTwoCharString(day);
    }

    private String convertToTwoCharString(String numberString){
        if(numberString.length() < 2){
            numberString = "0" + numberString;
        }
        return numberString;
    }
}
