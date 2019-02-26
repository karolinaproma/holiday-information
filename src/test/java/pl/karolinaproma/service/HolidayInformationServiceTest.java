package pl.karolinaproma.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import pl.karolinaproma.domain.Holiday;
import pl.karolinaproma.domain.HolidayForm;
import pl.karolinaproma.domain.HolidaySearchCriteria;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HolidayInformationServiceTest {

    @Autowired
    @InjectMocks
    private HolidayInformationServiceImpl serviceUnderTests;

    @Mock
    private static HolidayApiConnector holidayApiConnector;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Holiday holiday1 = new Holiday();
        holiday1.setName("Święto Trzech Króli");
        holiday1.setDate("2019-01-06");

        Holiday holiday2 = new Holiday();
        holiday2.setName("Epiphany");
        holiday2.setDate("2019-01-06");

        when(holidayApiConnector.findHoliday(ArgumentMatchers.isA(HolidaySearchCriteria.class))).thenReturn(holiday1, holiday2);
    }

    @Test
    public void shouldThrowExceptionBecouseWrongDate(){
        HolidayForm form = HolidayForm.builder().date("218-12-31").countryCode1("PL").countryCode2("US").build();

        thrown.expect(Exception.class);
        serviceUnderTests.findCommonHoliday(form);
    }

    @Test
    public void shouldFindCommonHoliday(){
        String countryCode1 = "PL";
        String countryCode2 = "US";
        String date = "2019-02-23";
        HolidayForm form = HolidayForm.builder().date(date).countryCode1(countryCode1).countryCode2(countryCode2).build();

        String actualResponse = serviceUnderTests.findCommonHoliday(form);

        String expectedResponse = "{\"date\":\"2019-01-06\",\"name1\":\"Święto Trzech Króli\",\"name2\":\"Epiphany\"}";
        Assert.assertEquals(expectedResponse, actualResponse);
    }
}
