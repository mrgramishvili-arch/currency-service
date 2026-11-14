package ru.skillbox.currencyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CurrencyShortDto {
    private String name;
    private BigDecimal value;
}
