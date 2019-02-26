package pl.karolinaproma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.karolinaproma.domain.HolidayForm;
import pl.karolinaproma.service.HolidayInformationService;

@RestController
@RequestMapping("/holidays")
public class HolidayInformationController {

    @Autowired
    private HolidayInformationService holidayInformationService;

    @RequestMapping(value="/find", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findCommonHoliday(@RequestBody HolidayForm holidayForm){
        String response = holidayInformationService.findCommonHoliday(holidayForm);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
