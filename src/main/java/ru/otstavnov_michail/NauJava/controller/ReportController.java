package ru.otstavnov_michail.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otstavnov_michail.NauJava.enums.ReportStatus;
import ru.otstavnov_michail.NauJava.modeldb.Report;
import ru.otstavnov_michail.NauJava.service.ReportService;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Long> createReport() {
        Long reportId = reportService.createReport();
        // Запускаем асинхронное формирование отчета
        CompletableFuture<Boolean> future = reportService.generateReportAsync(reportId);
        return ResponseEntity.ok(reportId);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getReportContent(@PathVariable Long id) {
        Optional<Report> reportOptional = reportService.getReportById(id);

        if (reportOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("<html><body><h3>Ошибка</h3><p>Отчет с ID " + id + " не найден</p></body></html>");
        }

        Report report = reportOptional.get();

        if (report.getStatus() == ReportStatus.CREATED) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("<html><body><h3>Отчет еще формируется</h3><p>Статус: " +
                            report.getStatus().getDisplayName() + "</p></body></html>");
        }

        if (report.getStatus() == ReportStatus.ERROR) {
            String errorContent = report.getContent() != null && !report.getContent().isEmpty()
                    ? report.getContent()
                    : "Причина ошибки не указана";

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<html><body><h3>Ошибка формирования отчета</h3><p>" +
                            errorContent + "</p></body></html>");
        }

        if (report.getContent() == null || report.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("<html><body><h3>Отчет не содержит данных</h3></body></html>");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(report.getContent());
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getReportStatus(@PathVariable Long id) {
        Optional<Report> reportOptional = reportService.getReportById(id);

        return reportOptional.map(report -> ResponseEntity.ok(report.getStatus().getDisplayName())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Отчет с ID " + id + " не найден"));

    }
}