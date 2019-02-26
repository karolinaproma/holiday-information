package pl.karolinaproma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseFromApi {
    private String status;
    private List<Holiday> holidays;
}
