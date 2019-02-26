package pl.karolinaproma.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParallelHoliday {
    private String date;
    private String name1;
    private String name2;
}
