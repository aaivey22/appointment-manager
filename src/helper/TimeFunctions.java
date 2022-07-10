package helper;

import java.time.*;
import java.util.TimeZone;

public class TimeFunctions {

    public static ZoneId localZoneId;
    public static ZoneId UTC;

    public static ZonedDateTime combineDateTime(LocalDate date, LocalTime time){
        localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime combinedDateTime = ZonedDateTime.of(date, time, localZoneId);
        return combinedDateTime;
    }

    public static ZonedDateTime combineDateTimeUTC(LocalDate date, LocalTime time){
        UTC = ZoneId.of("UTC");
        ZonedDateTime combinedDateTime = ZonedDateTime.of(date, time, UTC);
        return combinedDateTime;
    }

    public static ZonedDateTime convertUTC(ZonedDateTime dateTime){
        UTC = ZoneId.of("UTC");
        //return LocalDateTime.from(dtInput.atZone(UTC));
        return dateTime.withZoneSameInstant(UTC);
    }

    public static ZonedDateTime convertLocal(LocalDateTime dateTime){
        LocalDate startDate = dateTime.toLocalDate();
        LocalTime startTime = dateTime.toLocalTime();
        ZonedDateTime startLocal = TimeFunctions.combineDateTimeUTC(startDate, startTime);
        ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
        return startLocal.withZoneSameInstant(localZone);
    }
}
