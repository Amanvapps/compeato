package com.appinnovates.campeat.utils;


import android.util.Log;

import com.appinnovates.campeat.model.getAllDealsModel.Operational;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateFormatUtil {

    public static String getDateInString(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        Date time = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyy h:mm a zz");  //Jun 13, 2018 1:45 PM GMT+05:30
        return (dateFormat.format(time));
    }

    public static String getDateString(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        Date time = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyy");  //Jun 13, 2018 1:45 PM GMT+05:30
        return (dateFormat.format(time));
    }

    public static String getDate(String stringDate, String format) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a zz");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        Date time = calendar.getTime();
        DateFormat dformat = new SimpleDateFormat(format);
        return (dformat.format(time));
    }

    public static String getTime(String date) {
        Date newDate = null;
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a zz");
        try {
            newDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(newDate);
        Date time = calendar.getTime();
        DateFormat format = new SimpleDateFormat("h:mm a");
        return (format.format(time));
    }

    public static String getTimeFromDate(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        Date time = calendar.getTime();
        DateFormat format = new SimpleDateFormat("h:mm a");
        return (format.format(time));
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTime();
    }

    public static long getNumberOfDays(Date endDate) {
        long diff = endDate.getTime() - getCurrentDate().getTime();
        return TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
    }

    public static long getNumberOfHours(Date endDate) {
        long diff = endDate.getTime() - getCurrentDate().getTime();
        return TimeUnit.HOURS.convert(diff,TimeUnit.MILLISECONDS);
    }

    public static long getNumberOfMins(Date endDate) {
        long diff = endDate.getTime() - getCurrentDate().getTime();
        return TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS);
    }

//    public static String getTimeLeft(Date startDate, Date endDate) {
//        Date start = setTime(startDate);
//        Date end = setTime(endDate);
//        long currentMs = getCurrentDate().getTime();
//        long startMs = start.getTime();
//        long endMs = end.getTime();
//        long diffMs;
//        if (endMs > currentMs) {
//            if (currentMs > startMs) {
//                diffMs = endMs - currentMs;
//            } else {
//                diffMs = endMs - startMs;
//            }
//            long diffSec = diffMs / 1000;
//            long min = diffSec / 60;
//            long hours = min / 60;
//            long minutes = min % 60;
//            if (hours > 0) {
//                return "" + hours + " h, " + minutes + " min";
//            } else {
//                return "" + minutes + " min";
//            }
//        } else {
//            return "0 min";
//        }
//    }
    public static String getTimeLeft(String startTime, String endTime) {
        Date start = setTime(startTime);
        Date end = setTime(endTime);
        long currentMs = getCurrentDate().getTime();
        long startMs = start.getTime();
        long endMs = end.getTime();
        long diffMs;
        if (endMs > currentMs) {
            if (currentMs > startMs) {
                diffMs = endMs - currentMs;
            } else {
                diffMs = endMs - startMs;
            }
            long diffSec = diffMs / 1000;
            long min = diffSec / 60;
            long hours = min / 60;
            long minutes = min % 60;
            if (hours > 0) {
                return "" + hours + " h, " + minutes + " min";
            } else {
                return "" + minutes + " min";
            }
        } else {
            return "0 min";
        }
    }

    public static long calculateTime(String time) {
        long t = 0;
        String [] newTime = time.split(":");
        for(int i = newTime.length-1 ; i>=0; i--) {
            Log.i("---t===========####", "=================");
            Log.i("---t Before----", String.valueOf(t));
            Log.i("---t pow----", String.valueOf(Math.pow(60, i)));
            t = t + Long.parseLong(newTime[i])*(long)Math.pow(60, i);
            Log.i("---t After----", String.valueOf(t));
            Log.i("---t------------####", "---------------");
        }
        return t * 1000;
    }

    public static Date setTime(String startTime) {
        String [] newTime = startTime.split(":");
        int hours = 0;
        int minutes = 0;
        int sec = 0;
        if (newTime.length == 3) {
            hours = Integer.parseInt(newTime[0]);
            minutes = Integer.parseInt(newTime[1]);
            sec = Integer.parseInt(newTime[2]);
        } else if(newTime.length == 2) {
            hours = Integer.parseInt(newTime[0]);
            minutes = Integer.parseInt(newTime[1]);
        }
        Date currentDate = getCurrentDate();
        Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        currentCalendar.setTime(currentDate);
        currentCalendar.set(Calendar.HOUR_OF_DAY, hours);
        currentCalendar.set(Calendar.MINUTE, minutes);
        currentCalendar.set(Calendar.SECOND, sec);
        currentCalendar.set(Calendar.MILLISECOND, 0);
        return currentCalendar.getTime();
    }

//    private static Date setTime(Date startDate) {
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        calendar.setTime(startDate);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minutes = calendar.get(Calendar.MINUTE);
//        int sec = calendar.get(Calendar.SECOND);
//        Date currentDate = getCurrentDate();
//        Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        currentCalendar.setTime(currentDate);
//        currentCalendar.set(Calendar.HOUR_OF_DAY, hours);
//        currentCalendar.set(Calendar.MINUTE, minutes);
//        currentCalendar.set(Calendar.SECOND, sec);
//        currentCalendar.set(Calendar.MILLISECOND, 0);
//        return currentCalendar.getTime();
//    }

    public static String getOpeningHours(Operational operationalModel) {
        StringBuilder builder = new StringBuilder();
        String openingHours = "";
        String open = "";
        String close = "";
        if (operationalModel.getSUN() != null && operationalModel.getSUN().size()== 2) {
            open = operationalModel.getSUN().get(0);
            open = getMilitaryTime(open.substring(0,2));
            close = operationalModel.getSUN().get(1);
            close = getMilitaryTime(close.substring(0,2));
            openingHours = "Sunday " + open +"-"+ close + "\n";
            builder.append(openingHours);
        }
        if (operationalModel.getMON() != null && operationalModel.getMON().size()== 2) {
            open = operationalModel.getMON().get(0);
            open = getMilitaryTime(open.substring(0,2));
            close = operationalModel.getMON().get(1);
            close = getMilitaryTime(close.substring(0,2));
            openingHours = "Monday " + open +"-"+ close + "\n";
            builder.append(openingHours);
        }
        if (operationalModel.getTHU() != null && operationalModel.getTHU().size()== 2) {
            open = operationalModel.getTHU().get(0);
            open = getMilitaryTime(open.substring(0,2));
            close = operationalModel.getTHU().get(1);
            close = getMilitaryTime(close.substring(0,2));
            openingHours = "Tuesday " + open +"-"+ close + "\n";
            builder.append(openingHours);
        }
        if (operationalModel.getWED() != null && operationalModel.getWED().size()== 2) {
            open = operationalModel.getWED().get(0);
            open = getMilitaryTime(open.substring(0,2));
            close = operationalModel.getWED().get(1);
            close = getMilitaryTime(close.substring(0,2));
            openingHours = "Wednesday " + open +"-"+ close + "\n";
            builder.append(openingHours);
        }
        if (operationalModel.getTHU() != null && operationalModel.getTHU().size()== 2) {
            open = operationalModel.getTHU().get(0);
            open = getMilitaryTime(open.substring(0,2));
            close = operationalModel.getTHU().get(1);
            close = getMilitaryTime(close.substring(0,2));
            openingHours = "Thursday " + open +"-"+ close + "\n";
            builder.append(openingHours);
        }
        if (operationalModel.getFRI() != null && operationalModel.getFRI().size()== 2) {
            open = operationalModel.getFRI().get(0);
            open = getMilitaryTime(open.substring(0,2));
            close = operationalModel.getFRI().get(1);
            close = getMilitaryTime(close.substring(0,2));
            openingHours = "Friday " + open +"-"+ close + "\n";
            builder.append(openingHours);
        }
        if (operationalModel.getSAT() != null && operationalModel.getSAT().size()== 2) {
            open = operationalModel.getSAT().get(0);
            open = getMilitaryTime(open.substring(0,2));
            close = operationalModel.getSAT().get(1);
            close = getMilitaryTime(close.substring(0,2));
            openingHours = "Saturday " + open +"-"+ close + "\n";
            builder.append(openingHours);
        }
        Log.e("time", builder.toString());
        return builder.toString();
    }

    private static String getMilitaryTime(String hr) {
        DateFormat f1 = new SimpleDateFormat("HH"); //HH for hour of the day (0 - 23)
        Date date = null;
        try {
            date = f1.parse(hr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("ha");
        return f2.format(date);
    }
}
