package helper;

import java.time.*;

public class TimeFunctions {

    public static LocalDateTime combineDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }

    public static ZonedDateTime convertUTC(LocalDateTime dateTime){
        return ZonedDateTime.of(dateTime, ZoneOffset.UTC);
    }
}
