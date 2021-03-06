package com.example.dewartan.chronosoptim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public final class EventDate {
    private static final int MAX_DAYS = 90;
    private static final Date maxDay=createMaxDay();
    private static final SimpleDateFormat parser=createFormat();

    private static Date createMaxDay(){
        Calendar maxCal=new GregorianCalendar();
        maxCal.add(Calendar.DATE,MAX_DAYS+1);
        return maxCal.getTime();
    }

    private static SimpleDateFormat createFormat(){
        return new SimpleDateFormat("MM-dd-yyyy", Locale.US);
    }

    public static boolean beforeMax(Event event){
        return parse(event.getDate()).before(maxDay);
    }

    public static boolean beforeMax(Calendar cal){
        return cal.getTime().before(maxDay);
    }

    public static String getDayOfWeek(Event event) {
        String date=event.getDate();
        return getDayOfWeek(parse(date)) + ", " + date;
    }
    public static String getDayOfWeek(Date d){
        return new SimpleDateFormat("EEEE", Locale.US).format(d);
    }

    public static String pretty(String s){
        // "MM-dd-yyyy" ->  "dow: M/d"
        Date date=parse(s);
        return getDayOfWeek(date) + ": " + new SimpleDateFormat("M/d", Locale.US).format(date);
    }

    public static Date parse(String s){
        Date date=null;
        try{
            date=parser.parse(s);
        }catch(ParseException ex){
            ex.printStackTrace();
        }
        return date;
    }

    public static String format(Date d){
        return parser.format(d);
    }
    public static String format(Calendar c){
        return parser.format(c.getTime());
    }

    public static boolean matches(Calendar calendar,Event event){
        return event.getDate().equals(format(calendar));
    }

    public static boolean contains(ArrayList<String> headers,Event event){
        return headers.contains(pretty(event.getDate()));
    }

}
