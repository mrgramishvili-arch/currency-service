package ru.skillbox.currencyservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.currencyservice.dto.CurrencyListResponse;
import ru.skillbox.currencyservice.entity.Currency;
import ru.skillbox.currencyservice.service.CurrencyService;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/")
    public ResponseEntity<CurrencyListResponse> getAllCurrencies() {
        CurrencyListResponse response = new CurrencyListResponse();
        response.setCurrencies(currencyService.getAllCurrencies());
        return ResponseEntity.ok(response);
    }

    // Новый метод
    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable Long id) {
        return ResponseEntity.ok(currencyService.getCurrencyById(id));
    }
}