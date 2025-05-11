package ru.otstavnov_michail.NauJava;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testCreateReport_shouldReturnReportId() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .post("/api/reports")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void testGetReportContent_shouldReturnAcceptedIfReportStillCreated() {
        // Предварительно создаем отчет
        Long reportId = RestAssured
                .given()
                .when()
                .post("/api/reports")
                .then()
                .statusCode(200)
                .extract().as(Long.class);

        // Проверяем статус формирования отчета
        RestAssured
                .given()
                .when()
                .get("/api/reports/" + reportId)
                .then()
                .statusCode(202) // ACCEPTED
                .body(containsString("Отчет еще формируется"));
    }

    @Test
    void testGetReportContent_shouldReturnNotFoundForInvalidId() {
        RestAssured
                .given()
                .when()
                .get("/api/reports/9999999")
                .then()
                .statusCode(404)
                .body(containsString("не найден"));
    }

    @Test
    void testGetReportStatus_shouldReturnNotFoundForInvalidId() {
        RestAssured
                .given()
                .when()
                .get("/api/reports/9999999/status")
                .then()
                .statusCode(404)
                .body(containsString("не найден"));
    }

    @Test
    void testGetReportStatus_shouldReturnStatusText() {
        Long reportId = RestAssured
                .given()
                .when()
                .post("/api/reports")
                .then()
                .statusCode(200)
                .extract().as(Long.class);

        RestAssured
                .given()
                .when()
                .get("/api/reports/" + reportId + "/status")
                .then()
                .statusCode(200)
                .body(anyOf(containsString("Создан"), containsString("Готов"), containsString("Ошибка")));
    }
}
