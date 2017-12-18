package ee.ttu.automaattestimine.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class test {
    public static void main(String[] args) {
        //Specifying the pattern of input date and time
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateString = "22-03-2017 11:18:32";
        try{
            //formatting the dateString to convert it into a Date
            Date date = sdf.parse(dateString);
            System.out.println("Given Time in milliseconds : "+date.getTime());

            Calendar calendar = Calendar.getInstance();
            //Setting the Calendar date and time to the given date and time
            calendar.setTime(date);
            System.out.println("Given Time in milliseconds : "+calendar.getTimeInMillis());
        }catch(ParseException e){
            e.printStackTrace();
        }
        /*System.out.println(new Date());

        final LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        final Date endOfDayAsDate = Date.from(endOfDay.toInstant(ZoneOffset.UTC));
        System.out.println(endOfDayAsDate);

        final LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        final Date startOfDayAsDate = Date.from(startOfDay.toInstant(ZoneOffset.UTC));

        System.out.println(startOfDayAsDate);*/

    }
}