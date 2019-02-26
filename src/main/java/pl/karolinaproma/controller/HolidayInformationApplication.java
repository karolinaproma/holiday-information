package pl.karolinaproma.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"pl.karolinaproma"})
@SpringBootApplication
public class HolidayInformationApplication {
    public static void main(String[] args) {
        SpringApplication.run(HolidayInformationApplication.class, args);
    }
}
