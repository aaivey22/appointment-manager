package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.TimeZone;


/**
 * This class is used to work with dates and times
 */
public class TimeFunctions {

    public static ZoneId localZoneId;
    public static ZoneId UTC;

    /**
     * @param date,time combineDateTime takes date and time inputs and combines them to return combinedDateTime
     * @return combinedDateTime converts and combines localZoneId date and time.
     */
    public static ZonedDateTime combineDateTime(LocalDate date, LocalTime time) {
        localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime combinedDateTime = ZonedDateTime.of(date, time, localZoneId);
        return combinedDateTime;
    }

    /**
     * @param date,time combineDateTimeUTC takes date and time inputs and combines them to return combinedDateTime in UTC.
     * @return combinedDateTime converts and combines UTC date and time.
     */
    public static ZonedDateTime combineDateTimeUTC(LocalDate date, LocalTime time) {
        UTC = ZoneId.of("UTC");
        ZonedDateTime combinedDateTime = ZonedDateTime.of(date, time, UTC);
        return combinedDateTime;
    }

    /**
     * @param dateTime convertUTC takes date and time inputs and converts to UTC timezone
     * @return dateTime.withZoneSameInstant(UTC) a converted UTC time.
     */
    public static ZonedDateTime convertUTC(ZonedDateTime dateTime) {
        UTC = ZoneId.of("UTC");
        return dateTime.withZoneSameInstant(UTC);
    }


    /**
     * @param dateTime convertLocal takes date and time inputs and converts to local system timezone
     * @return startLocal.withZoneSameInstant(localZone) a converted local start time.
     */
    public static ZonedDateTime convertLocal(LocalDateTime dateTime) {
        LocalDate startDate = dateTime.toLocalDate();
        LocalTime startTime = dateTime.toLocalTime();
        ZonedDateTime startLocal = TimeFunctions.combineDateTimeUTC(startDate, startTime);
        ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
        return startLocal.withZoneSameInstant(localZone);
    }

    /**
     * @param start,end isOverlap takes in a start and end time, then gets all appointment dates and times from the appointments table
     * and checks for overlapping dates or times. Returns a boolean true if there is an overlap.
     * @return overLap method is used to check if start and end times overlap.
     */
    public static Boolean isOverlap(LocalDateTime start, LocalDateTime end) throws SQLException {
        ResultSet resultSet = LoginQuery.getAllDateTimes();
        Boolean overLap = false;
        while (resultSet.next()) {
            LocalDateTime startResult = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endResult = resultSet.getTimestamp("End").toLocalDateTime();
            if (startResult.isAfter(start) && startResult.isBefore(end)) {
                overLap = true;
            }
            if (startResult.isEqual(start) && startResult.isBefore(end)) {
                overLap = true;
            }
        }
        return overLap;
    }

    /**
     * @param start,end,appointmentID takes in a start and end time, appointmentID, then gets all appointment dates and times from the appointments table
     * and checks for overlapping dates or times. This excludes the provided appointmentID. Returns a boolean true if there is an overlap.
     * @return overLap isOverlapModify method is used to check if start and end times overlap.
     */
    public static Boolean isOverlapModify(LocalDateTime start, LocalDateTime end, Integer appointmentID) throws SQLException {
        ResultSet resultSet = LoginQuery.getAllDateTimesModify(appointmentID);
        Boolean overLap = false;
        while (resultSet.next()) {
            LocalDateTime startResult = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endResult = resultSet.getTimestamp("End").toLocalDateTime();
            if (startResult.isAfter(start) && startResult.isBefore(end)) {
                overLap = true;
            }
            if (startResult.isEqual(start) && startResult.isBefore(end)) {
                overLap = true;
            }
        }
        return overLap;
    }
}
