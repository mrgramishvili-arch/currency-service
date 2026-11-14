package ru.skillbox.currencyservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.currencyservice.dto.CurrencyShortDto;
import ru.skillbox.currencyservice.entity.Currency;
import ru.skillbox.currencyservice.repository.CurrencyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository repository;

    public List<CurrencyShortDto> getAllCurrencies() {
        return repository.findAll().stream()
                .map(c -> new CurrencyShortDto(c.getName(), c.getRate()))
                .collect(Collectors.toList());
    }

    // Новый метод для получения валюты по ID
    public Currency getCurrencyById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Валюта не найдена"));
    }
}
