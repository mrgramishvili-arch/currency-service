package ru.skillbox.currencyservice.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.skillbox.currencyservice.dto.CurrencyJaxb;
import ru.skillbox.currencyservice.dto.ValCurs;
import ru.skillbox.currencyservice.entity.Currency;
import ru.skillbox.currencyservice.repository.CurrencyRepository;

import java.io.StringReader;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyLoaderService {

    private final CurrencyRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${cbr.url}")
    private String cbrUrl;

    @Scheduled(fixedRate = 3600000)
    public void updateCurrencies() {
        log.info("Запуск обновления валют из ЦБ РФ...");
        try {
            String xmlData = restTemplate.getForObject(cbrUrl, String.class);
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(new StringReader(xmlData));

            for (CurrencyJaxb jaxb : valCurs.getValutes()) {
                BigDecimal price = new BigDecimal(jaxb.getValue().replace(",", "."));

                // Ищем валюту по коду. Если нет - создаем новую.
                Currency currency = repository.findByIsoCharCode(jaxb.getCharCode())
                        .orElse(new Currency());

                currency.setName(jaxb.getName());
                currency.setNominal(jaxb.getNominal());
                currency.setIsoNumericCode(jaxb.getNumCode());
                currency.setRate(price);
                currency.setIsoCharCode(jaxb.getCharCode());

                // Если это новый объект, Hibernate сам сгенерирует ID через последовательность
                repository.save(currency);
            }
            log.info("Обновление валют успешно завершено. Обработано: {}", valCurs.getValutes().size());
        } catch (Exception e) {
            log.error("Ошибка при обновлении валют: {}", e.getMessage());
        }
    }
}