package ru.otstavnov_michail.NauJava.enums;

public enum ReportStatus {
    CREATED("Создан"),
    COMPLETED("Завершен"),
    ERROR("Ошибка");

    private final String displayName;

    ReportStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}