package personal.pascoe.jp0624.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import personal.pascoe.jp0624.models.RentalAgreement;
import personal.pascoe.jp0624.models.RentalForm;
import personal.pascoe.jp0624.models.Tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private ToolService toolService;
    @InjectMocks
    private CheckoutService checkoutService;

    @Test
    void rentTool_LADW() {
        Tool tool = buildMockTool(
                "Ladder",
                "Werner",
                BigDecimal.valueOf(1.99),
                true,
                true,
                false);

        when(toolService.findByToolCode(anyString())).thenReturn(tool);
        RentalForm rentalForm = RentalForm.builder()
                .toolCode("LADW")
                .rentalDays(3)
                .discountPercent(10)
                .checkoutDate(LocalDate.of(2020, 7, 2))
                .build();

        RentalAgreement expectedRentalAgreement = RentalAgreement.builder()
                .toolCode("LADW")
                .toolType("Ladder")
                .toolBrand("Werner")
                .rentalDays(3)
                .checkoutDate(LocalDate.of(2020, 7, 2))
                .dueDate(LocalDate.of(2020, 7, 5))
                .dailyRentalCharge(BigDecimal.valueOf(1.99))
                .chargeDays(3)
                .preDiscountCharge(BigDecimal.valueOf(5.97))
                .discountPercent(10)
                .discountAmount(BigDecimal.valueOf(0.59))
                .finalCharge(BigDecimal.valueOf(5.38))
                .build();

        RentalAgreement rentalAgreement = checkoutService.rentTool(rentalForm);

        assertEquals(expectedRentalAgreement, rentalAgreement);
    }

    @Test
    void rentTool_CHNS() {
        Tool tool = buildMockTool(
                "Chainsaw",
                "Stihl",
                BigDecimal.valueOf(1.49),
                true,
                false,
                true);

        when(toolService.findByToolCode(anyString())).thenReturn(tool);
        RentalForm rentalForm = RentalForm.builder()
                .toolCode("CHNS")
                .rentalDays(5)
                .discountPercent(25)
                .checkoutDate(LocalDate.of(2015, 7, 2))
                .build();

        RentalAgreement expectedRentalAgreement = RentalAgreement.builder()
                .toolCode("CHNS")
                .toolType("Chainsaw")
                .toolBrand("Stihl")
                .rentalDays(5)
                .checkoutDate(LocalDate.of(2015, 7, 2))
                .dueDate(LocalDate.of(2015, 7, 7))
                .dailyRentalCharge(BigDecimal.valueOf(1.49))
                .chargeDays(2)
                .preDiscountCharge(BigDecimal.valueOf(2.98))
                .discountPercent(25)
                .discountAmount(BigDecimal.valueOf(0.74))
                .finalCharge(BigDecimal.valueOf(2.24))
                .build();

        RentalAgreement rentalAgreement = checkoutService.rentTool(rentalForm);

        assertEquals(expectedRentalAgreement, rentalAgreement);
    }

    @Test
    void rentTool_JAKD() {
        Tool tool = buildMockTool(
                "Jackhammer",
                "DeWalt",
                BigDecimal.valueOf(2.99),
                true,
                false,
                false);

        when(toolService.findByToolCode(anyString())).thenReturn(tool);
        RentalForm rentalForm = RentalForm.builder()
                .toolCode("JAKD")
                .rentalDays(6)
                .discountPercent(0)
                .checkoutDate(LocalDate.of(2015, 9, 3))
                .build();

        RentalAgreement expectedRentalAgreement = RentalAgreement.builder()
                .toolCode("JAKD")
                .toolType("Jackhammer")
                .toolBrand("DeWalt")
                .rentalDays(6)
                .checkoutDate(LocalDate.of(2015, 9, 3))
                .dueDate(LocalDate.of(2015, 9, 9))
                .dailyRentalCharge(BigDecimal.valueOf(2.99))
                .chargeDays(4)
                .preDiscountCharge(BigDecimal.valueOf(11.96))
                .discountPercent(0)
                .discountAmount(BigDecimal.valueOf(0.0).setScale(2, RoundingMode.DOWN))
                .finalCharge(BigDecimal.valueOf(11.96))
                .build();

        RentalAgreement rentalAgreement = checkoutService.rentTool(rentalForm);

        assertEquals(expectedRentalAgreement, rentalAgreement);
    }

    @Test
    void rentTool_JAKR() {
        Tool tool = buildMockTool(
                "Jackhammer",
                "Ridgid",
                BigDecimal.valueOf(2.99),
                true,
                false,
                false);

        when(toolService.findByToolCode(anyString())).thenReturn(tool);
        RentalForm rentalForm = RentalForm.builder()
                .toolCode("JAKR")
                .rentalDays(9)
                .discountPercent(0)
                .checkoutDate(LocalDate.of(2015, 7, 2))
                .build();

        RentalAgreement expectedRentalAgreement = RentalAgreement.builder()
                .toolCode("JAKR")
                .toolType("Jackhammer")
                .toolBrand("Ridgid")
                .rentalDays(9)
                .checkoutDate(LocalDate.of(2015, 7, 2))
                .dueDate(LocalDate.of(2015, 7, 11))
                .dailyRentalCharge(BigDecimal.valueOf(2.99))
                .chargeDays(6)
                .preDiscountCharge(BigDecimal.valueOf(17.94))
                .discountPercent(0)
                .discountAmount(BigDecimal.valueOf(0.0).setScale(2, RoundingMode.DOWN))
                .finalCharge(BigDecimal.valueOf(17.94))
                .build();

        RentalAgreement rentalAgreement = checkoutService.rentTool(rentalForm);

        assertEquals(expectedRentalAgreement, rentalAgreement);
    }

    @Test
    void rentTool_JAKR_IndependenceDay() {
        Tool tool = buildMockTool(
                "Jackhammer",
                "Ridgid",
                BigDecimal.valueOf(2.99),
                true,
                false,
                false);

        when(toolService.findByToolCode(anyString())).thenReturn(tool);
        RentalForm rentalForm = RentalForm.builder()
                .toolCode("JAKR")
                .rentalDays(4)
                .discountPercent(50)
                .checkoutDate(LocalDate.of(2020, 7, 2))
                .build();

        RentalAgreement expectedRentalAgreement = RentalAgreement.builder()
                .toolCode("JAKR")
                .toolType("Jackhammer")
                .toolBrand("Ridgid")
                .rentalDays(4)
                .checkoutDate(LocalDate.of(2020, 7, 2))
                .dueDate(LocalDate.of(2020, 7, 6))
                .dailyRentalCharge(BigDecimal.valueOf(2.99))
                .chargeDays(2)
                .preDiscountCharge(BigDecimal.valueOf(5.98))
                .discountPercent(50)
                .discountAmount(BigDecimal.valueOf(2.99))
                .finalCharge(BigDecimal.valueOf(2.99))
                .build();

        RentalAgreement rentalAgreement = checkoutService.rentTool(rentalForm);

        assertEquals(expectedRentalAgreement, rentalAgreement);
    }

    @Test
    void rentTool_discountPercentException() {
        RentalForm rentalForm = new RentalForm("JAKR", 5, 101, LocalDate.of(2015, 9, 3));

        assertThrows(IllegalArgumentException.class, () -> checkoutService.rentTool(rentalForm));
    }

    private static Tool buildMockTool(String toolType,
                                      String brand,
                                      BigDecimal dailyCharge,
                                      boolean isWeekdayCharged,
                                      boolean isWeekendCharged,
                                      boolean isHolidayCharged) {
        Tool tool = mock(Tool.class);
        when(tool.getToolType()).thenReturn(toolType);
        when(tool.getBrand()).thenReturn(brand);
        when(tool.getDailyCharge()).thenReturn(dailyCharge);
        when(tool.isWeekdayCharged()).thenReturn(isWeekdayCharged);
        when(tool.isWeekendCharged()).thenReturn(isWeekendCharged);
        when(tool.isHolidayCharged()).thenReturn(isHolidayCharged);
        return tool;
    }

    private static RentalAgreement buildMockRentalAgreement(String toolCode,
                                                           String toolType,
                                                           String toolBrand,
                                                           int rentalDays,
                                                           LocalDate checkoutDate,
                                                           LocalDate dueDate,
                                                           BigDecimal dailyRentalCharge,
                                                           int chargeDays,
                                                           BigDecimal preDiscountCharge,
                                                           int discountPercent,
                                                           BigDecimal discountAmount,
                                                           BigDecimal finalCharge) {
        RentalAgreement rentalAgreement = mock(RentalAgreement.class);
        when(rentalAgreement.getToolCode()).thenReturn(toolCode);
        when(rentalAgreement.getToolType()).thenReturn(toolType);
        when(rentalAgreement.getToolBrand()).thenReturn(toolBrand);
        when(rentalAgreement.getRentalDays()).thenReturn(rentalDays);
        when(rentalAgreement.getCheckoutDate()).thenReturn(checkoutDate);
        when(rentalAgreement.getDueDate()).thenReturn(dueDate);
        when(rentalAgreement.getDailyRentalCharge()).thenReturn(dailyRentalCharge);
        when(rentalAgreement.getChargeDays()).thenReturn(chargeDays);
        when(rentalAgreement.getPreDiscountCharge()).thenReturn(preDiscountCharge);
        when(rentalAgreement.getDiscountPercent()).thenReturn(discountPercent);
        when(rentalAgreement.getDiscountAmount()).thenReturn(discountAmount);
        when(rentalAgreement.getFinalCharge()).thenReturn(finalCharge);
        return rentalAgreement;
    }
}
