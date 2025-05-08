package ru.otstavnov_michail.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otstavnov_michail.NauJava.enums.ReportStatus;
import ru.otstavnov_michail.NauJava.modeldb.Report;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
    List<Report> findAllByStatus(ReportStatus status);
    Optional<Report> findTopByOrderByCreatedAtDesc();
}
