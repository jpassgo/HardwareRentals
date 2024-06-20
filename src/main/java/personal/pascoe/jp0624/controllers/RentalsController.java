package personal.pascoe.jp0624.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.pascoe.jp0624.models.RentalAgreement;
import personal.pascoe.jp0624.models.RentalForm;
import personal.pascoe.jp0624.services.CheckoutService;

@RestController
@AllArgsConstructor
@RequestMapping("/rentals")
public class RentalsController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public RentalAgreement rentTool(@RequestBody RentalForm rentalForm) {
        return checkoutService.rentTool(rentalForm);
    }
}

