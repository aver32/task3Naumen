package ru.otstavnov_michail.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.otstavnov_michail.NauJava.enums.ReportStatus;
import ru.otstavnov_michail.NauJava.modeldb.Guest;
import ru.otstavnov_michail.NauJava.modeldb.Report;
import ru.otstavnov_michail.NauJava.modeldb.User;
import ru.otstavnov_michail.NauJava.repository.GuestRepository;
import ru.otstavnov_michail.NauJava.repository.ReportRepository;
import ru.otstavnov_michail.NauJava.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final GuestRepository guestRepository;
    private final TemplateEngine templateEngine;

    @Autowired
    public ReportService(ReportRepository reportRepository, UserRepository userRepository, GuestRepository guestRepository, TemplateEngine templateEngine) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.templateEngine = templateEngine;
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report = reportRepository.save(report);
        return report.getId();
    }

    public CompletableFuture<Boolean> generateReportAsync(Long reportId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<Report> reportOptional = reportRepository.findById(reportId);
                if (reportOptional.isEmpty()) {
                    return false;
                }

                Report report = reportOptional.get();
                long totalStartTime = System.currentTimeMillis();

                // Результаты и время выполнения для каждого потока
                AtomicLong userCount = new AtomicLong();
                AtomicLong userCountTime = new AtomicLong();
                AtomicReference<List<User>> users = new AtomicReference<>();
                AtomicReference<List<Guest>> guests = new AtomicReference<>();
                AtomicLong entitiesListTime = new AtomicLong();

                // Поток для подсчета пользователей
                Thread countThread = new Thread(() -> {
                    long startTime = System.currentTimeMillis();
                    userCount.set(userRepository.count());
                    userCountTime.set(System.currentTimeMillis() - startTime);
                });

                // Поток для получения списка объектов
                Thread listThread = new Thread(() -> {
                    long startTime = System.currentTimeMillis();
                    users.set(userRepository.findAll());
                    guests.set(guestRepository.findAll());
                    entitiesListTime.set(System.currentTimeMillis() - startTime);
                });

                // Запускаем потоки
                countThread.start();
                listThread.start();

                // Ожидаем завершения потоков
                try {
                    countThread.join();
                    listThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Report generation interrupted", e);
                }

                long totalElapsed = System.currentTimeMillis() - totalStartTime;

                // Подготавливаем данные для шаблона
                Context context = new Context();
                context.setVariable("userCount", userCount.get());
                context.setVariable("users", users.get());
                context.setVariable("guests", guests.get());
                context.setVariable("userCountTime", userCountTime.get());
                context.setVariable("entitiesListTime", entitiesListTime.get());
                context.setVariable("totalElapsed", totalElapsed);

                // Генерируем HTML из шаблона
                String htmlContent = templateEngine.process("report-template", context);

                // Сохраняем отчет
                report.setContent(htmlContent);
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

                return true;
            } catch (Exception e) {
                reportRepository.findById(reportId).ifPresent(r -> {
                    r.setStatus(ReportStatus.ERROR);
                    r.setContent("Error generating report: " + e.getMessage());
                    reportRepository.save(r);
                });
                return false;
            }
        });
    }
}