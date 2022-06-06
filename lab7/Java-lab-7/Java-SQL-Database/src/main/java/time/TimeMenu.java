package time;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeMenu {

    private final Date date;

    public Date getDate() {
        return date;
    }

    public TimeMenu() {
        LocalDateTime now = LocalDateTime.now();
        String date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();
        this.date = Date.valueOf(Objects.requireNonNull(TimeMenu.checkDate(date)));
    }

    public static LocalDate checkDate(String date) {
        int rok;
        int miesiac;
        int dzien;
        try {
            String[] d = date.split("-");
            rok = Integer.parseInt(d[0]);
            miesiac = Integer.parseInt(d[1]);
            dzien = Integer.parseInt(d[2]);
        }catch (Exception e){
            return null;
        }

        if (rok >= 3000 || rok < 0) {
            return null;
        }

        if (miesiac > 13 || miesiac < 0) {
            return null;
        }

        boolean leap = false;

        // if the year is divided by 4
        if (rok % 4 == 0) {

            // if the year is century
            if (rok % 100 == 0) {

                // if year is divided by 400
                // then it is a leap year
                leap = rok % 400 == 0;
            }

            // if the year is not century
            else {
                leap = true;
            }
        }

        if (dzien > 0) {
            if (miesiac == 1 || miesiac == 3 || miesiac == 5 || miesiac == 7 || miesiac == 8
                    || miesiac == 10 || miesiac == 12) {
                if (dzien >= 32) {
                    return null;
                }

            } else if (miesiac == 4 || miesiac == 6 || miesiac == 9 || miesiac == 11) {
                if (dzien >= 31) {
                    return null;
                }

            } else if (leap) {
                if (dzien >= 30) {
                    return null;
                }

            } else {
                if (dzien >= 29) {
                    return null;
                }
            }
        }else return null;

        String zeroMonth = "";
        String zeroDay = "";
        if(miesiac < 10) zeroMonth = "0";
        if(dzien < 10) zeroDay = "0";

        String x = rok + "-" + zeroMonth + miesiac + "-" + zeroDay + dzien;

        return LocalDate.parse(x);
    }
}
