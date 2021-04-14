package sk.kosickaakademia.spivak.exchangerates.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public String getCurrentTime(){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatForDateNow.format(dateNow);
    }
}
