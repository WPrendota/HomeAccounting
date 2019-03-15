import java.time.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    private Integer Year;

    public String getYearMonth(){
        Date year = new Date();
        DateFormat df = new SimpleDateFormat("yyyy_MM");
        return df.format(year);
    }

    public String getDate(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(date);
    }

    public String getDay(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd");
        return df.format(date);
    }
}
