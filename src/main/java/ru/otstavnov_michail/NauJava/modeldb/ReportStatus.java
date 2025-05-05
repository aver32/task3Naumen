package ru.otstavnov_michail.NauJava.modeldb;

public enum ReportStatus {
    CREATED("Создан"),
    COMPLETED("Завершен"),
    FAILED("Ошибка");

    private final String displayName;

    ReportStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}