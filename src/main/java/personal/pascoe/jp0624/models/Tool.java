package personal.pascoe.jp0624.models;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String toolCode;

    @Column(nullable = false)
    private String toolType;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private BigDecimal dailyCharge;

    @Column(nullable = false)
    private boolean isWeekdayCharged;

    @Column(nullable = false)
    private boolean isWeekendCharged;

    @Column(nullable = false)
    private boolean isHolidayCharged;
}