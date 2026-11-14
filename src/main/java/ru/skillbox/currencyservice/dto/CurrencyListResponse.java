package ru.skillbox.currencyservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class CurrencyListResponse {
    private List<CurrencyShortDto> currencies;
}
