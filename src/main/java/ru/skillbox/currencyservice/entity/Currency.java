package ru.skillbox.currencyservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "currency")
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    @SequenceGenerator(name = "currency_seq", sequenceName = "currency_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Integer nominal;

    @Column(name = "iso_numeric_code")
    private String isoNumericCode;

    // Переименовали в rate
    private BigDecimal rate;

    @Column(name = "iso_char_code")
    private String isoCharCode;
}
