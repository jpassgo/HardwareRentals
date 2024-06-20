package personal.pascoe.jp0624.models;

import java.time.LocalDate;
import java.time.MonthDay;

import static java.time.DayOfWeek.*;
import static java.time.Month.SEPTEMBER;

public class Holiday {
    private static final MonthDay INDEPENDENCE_DAY = MonthDay.of(7, 4);

    public static boolean isHoliday(LocalDate date) {
        return INDEPENDENCE_DAY.equals(MonthDay.from(date)) ||
                (SEPTEMBER.equals(date.getMonth()) &&
                        date.getDayOfWeek().equals(MONDAY) &&
                        date.getDayOfMonth() < 8);
    }

    public static boolean isDiscountDayInRange(LocalDate date, LocalDate checkoutDate, LocalDate dueDate) {
        if (INDEPENDENCE_DAY.equals(MonthDay.from(date))) {
            if (SATURDAY.equals(date.getDayOfWeek())) {
              return !date.minusDays(1).isBefore(checkoutDate);
            } else if (SUNDAY.equals(date.getDayOfWeek())) {
                return !date.plusDays(1).isAfter(dueDate);
            }
        }
        return true;
    }
}
