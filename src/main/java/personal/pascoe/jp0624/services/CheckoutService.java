package personal.pascoe.jp0624.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.pascoe.jp0624.models.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.math.BigDecimal.valueOf;
import static java.time.DayOfWeek.*;
import static personal.pascoe.jp0624.models.Holiday.*;

@Service
@AllArgsConstructor
public class CheckoutService {

    @Autowired
    private ToolService toolService;

    public RentalAgreement rentTool(RentalForm rentalForm) {
        validateRentalForm(rentalForm);

        Tool tool = toolService.findByToolCode(rentalForm.getToolCode());

        LocalDate dueDate = calculateDueDate(rentalForm.getCheckoutDate(), rentalForm.getRentalDays());
        int chargeDays = calculateChargeDays(rentalForm.getCheckoutDate(), dueDate, tool);
        BigDecimal preDiscountCharge = valueOf(chargeDays).multiply(tool.getDailyCharge());
        BigDecimal discountAmount = calculateDiscountAmount(rentalForm.getDiscountPercent(), preDiscountCharge);

        var rentalAgreement = RentalAgreement.builder()
                .toolCode(rentalForm.getToolCode())
                .toolType(tool.getToolType())
                .toolBrand(tool.getBrand())
                .rentalDays(rentalForm.getRentalDays())
                .checkoutDate(rentalForm.getCheckoutDate())
                .dueDate(dueDate)
                .dailyRentalCharge(tool.getDailyCharge())
                .chargeDays(chargeDays)
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(rentalForm.getDiscountPercent())
                .discountAmount(discountAmount)
                .finalCharge(preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.DOWN))
                .build();

       printRentalAgreement(rentalAgreement);
       return rentalAgreement;
    }

    private BigDecimal calculateDiscountAmount(int discountPercent, BigDecimal preDiscountCharge) {
        BigDecimal discountPercentAsDecimal = valueOf(discountPercent).divide(valueOf(100)).setScale(2, RoundingMode.DOWN);
        return preDiscountCharge.multiply(discountPercentAsDecimal).setScale(2, RoundingMode.DOWN);
    }

    private void validateRentalForm(RentalForm rentalForm) {
        if (rentalForm.getRentalDays() < 1) {
            throw new IllegalArgumentException("Rental days must be at greater than zero.");
        } else if (!(0 <= rentalForm.getDiscountPercent() && rentalForm.getDiscountPercent() <= 100)) {
            throw new IllegalArgumentException("Discount percent must be within range 0 to 100 (inclusive).");
        }
    }

    public void printRentalAgreement(RentalAgreement rentalAgreement) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        String formattedRentalAgreement = "Tool code: " + rentalAgreement.getToolCode() + "\n" +
                "Tool type: " + rentalAgreement.getToolType() + "\n" +
                "Tool brand: " + rentalAgreement.getToolBrand() + "\n" +
                "Rental days: " + rentalAgreement.getRentalDays() + "\n" +
                "Checkout date: " + dateFormatter.format(rentalAgreement.getCheckoutDate()) + "\n" +
                "Due date: " +  dateFormatter.format(rentalAgreement.getDueDate()) + "\n" +
                "Daily rental charge: " +  currencyFormatter.format(rentalAgreement.getDailyRentalCharge()) + "\n" +
                "Charge days: " +  rentalAgreement.getChargeDays() + "\n" +
                "Prediscount charge: " +  currencyFormatter.format(rentalAgreement.getPreDiscountCharge()) + "\n" +
                "Discount percent: " +  formatPercent(rentalAgreement.getDiscountPercent()) + "\n" +
                "Discount amount: " +  currencyFormatter.format(rentalAgreement.getDiscountAmount()) + "\n" +
                "Final charge: " +  currencyFormatter.format(rentalAgreement.getFinalCharge());
        System.out.println(formattedRentalAgreement);
    }

    private static String formatPercent(int percent) {
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMinimumFractionDigits(0);
        percentFormatter.setMaximumFractionDigits(0);
        return percentFormatter.format(valueOf(percent).divide(new BigDecimal("100")));
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays) {
        return checkoutDate.plusDays(rentalDays);
    }

    private int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool tool) {
        int chargeDays = 0;
        var weekendDays = List.of(SATURDAY, SUNDAY);
        var weekDays = Arrays.stream(values()).filter(day -> !weekendDays.contains(day)).toList();

        LocalDate date = checkoutDate.plusDays(1);
        while (date.isBefore(dueDate.plusDays(1))) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (tool.isHolidayCharged() && isHoliday(date) && isDiscountDayInRange(date, checkoutDate, dueDate)) {
                if (!tool.isWeekendCharged() && weekendDays.contains(dayOfWeek)) {
                    chargeDays--;
                }
            } else if (tool.isWeekdayCharged() && weekDays.contains(dayOfWeek)) {
                chargeDays++;
            } else if (tool.isWeekendCharged() && weekendDays.contains(dayOfWeek)) {
                chargeDays++;
            }
            date = date.plusDays(1);
        }
        return chargeDays;
    }
}
