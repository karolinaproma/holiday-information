package pl.karolinaproma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayForm {
    private String countryCode1;
    private String countryCode2;
    private String date;
}
