package ru.otstavnov_michail.NauJava;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void testLoginAndLogout() {
        driver.get("http://localhost:8080/login");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        username.sendKeys("admin");
        password.sendKeys("admin");
        submitButton.click();

        // Ждём переход на страницу профиля
        wait.until(ExpectedConditions.urlContains("/profile"));
        Assertions.assertTrue(driver.getPageSource().contains("Добро пожаловать в профиль"));

        // Выход
        WebElement logoutButton = driver.findElement(By.cssSelector("form[action='/logout'] button"));
        logoutButton.click();

        // Проверка, что вернулись на логин
        wait.until(ExpectedConditions.urlContains("/login"));
        Assertions.assertTrue(driver.getPageSource().contains("Вход в систему"));
    }
}
