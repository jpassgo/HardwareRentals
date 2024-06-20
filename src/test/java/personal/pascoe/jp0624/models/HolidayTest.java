package personal.pascoe.jp0624.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class HolidayTest {

    @Test
    void isHoliday() {
        assertTrue(Holiday.isHoliday(LocalDate.of(2024, 7, 4)));
        assertTrue(Holiday.isHoliday(LocalDate.of(2024, 9, 2)));

        assertFalse(Holiday.isHoliday(LocalDate.of(2024, 10, 31)));
        assertFalse(Holiday.isHoliday(LocalDate.of(2024, 1, 1)));
    }

    @Test
    void isDiscountDayInRange_discountFriday() {
        // Independence Day falls on a Saturday in 2026
        var checkoutDate = LocalDate.of(2026, 7, 3);
        var dueDate = LocalDate.of(2026, 7, 8);
        var date = LocalDate.of(2026, 7, 4);
        assertTrue(Holiday.isDiscountDayInRange(date, checkoutDate, dueDate));
    }

    @Test
    void isDiscountDayInRange_discountMonday() {
        // Independence Day falls on a Sunday in 2027
        var checkoutDate = LocalDate.of(2027, 7, 3);
        var dueDate = LocalDate.of(2027, 7, 8);
        var date = LocalDate.of(2027, 7, 4);
        assertTrue(Holiday.isDiscountDayInRange(date, checkoutDate, dueDate));
    }

    @Test
    void isDiscountDayInRange_discountFriday_outOfRange() {
        // Independence Day falls on a Saturday in 2026
        var checkoutDate = LocalDate.of(2026, 7, 4);
        var dueDate = LocalDate.of(2026, 7, 8);
        var date = LocalDate.of(2026, 7, 4);
        assertFalse(Holiday.isDiscountDayInRange(date, checkoutDate, dueDate));
    }

    @Test
    void isDiscountDayInRange_discountMonday_outOfRange() {
        // Independence Day falls on a Sunday in 2027
        var checkoutDate = LocalDate.of(2027, 7, 3);
        var dueDate = LocalDate.of(2027, 7, 4);
        var date = LocalDate.of(2027, 7, 4);
        assertFalse(Holiday.isDiscountDayInRange(date, checkoutDate, dueDate));
    }
}