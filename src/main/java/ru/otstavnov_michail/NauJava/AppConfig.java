package ru.otstavnov_michail.NauJava;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @PostConstruct
    public void displayAppInfo() {
        System.out.println("Приложение: " + appName);
        System.out.println("Версия: " + appVersion);
    }
}
