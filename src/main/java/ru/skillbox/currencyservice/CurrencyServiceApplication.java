package ru.skillbox.currencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // <--- ДОБАВЬ ЭТУ СТРОКУ
public class CurrencyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyServiceApplication.class, args);
    }
}
