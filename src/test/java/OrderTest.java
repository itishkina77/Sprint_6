import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.AboutRentPage;
import pageObject.MainPage;
import pageObject.OrderPage;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    private WebDriver driver;

    @ParameterizedTest
    @MethodSource("orderTestDataProvider")
    @DisplayName("Заказ самоката с разными параметрами")
    public void orderByButton(String browser, String buttonPosition,
                              String firstName, String lastName, String address,
                              String metro, String phone, String date, String comment) {
        setUp(browser);

        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);
        AboutRentPage aboutRentPage = new AboutRentPage(driver);

        // Выбираем какую кнопку нажать
        if (buttonPosition.equals("UP")) {
            mainPage.orderButtonUpPageClick();
        } else {
            mainPage.orderButtonDownPageClick();
        }

        orderPage.setOrderForm(firstName, lastName, address, metro, phone);
        orderPage.clickNextButton();

        //проверяем переход на страницу "Про аренду"
        String expectedTitle = "Про аренду";
        String actualTitle = aboutRentPage.getNameForm();
        assertEquals(expectedTitle, actualTitle, "Заголовок страницы не соответствует ожидаемому");

        aboutRentPage.setAndConfirm(date, comment);
        aboutRentPage.orderPlacedDisplayed();
    }

    // Провайдер данных для теста
    private static Stream<Arguments> orderTestDataProvider() {
        return Stream.of(
                // Chrome с кнопкой Заказать вверху страницы  - полный заказ
                Arguments.of("chrome", "UP",
                        "Иван", "Петров", "Москва", "Сокольники", "+79031231212",
                        "28.05.2026", "тест"),

                // Chrome с кнопкой Заказать вверху страницы и с пустым комментарием
                Arguments.of("chrome", "UP",
                        "Анна", "Сидорова", "Санкт-Петербург", "Невский проспект", "+79112223344",
                        "15.06.2026", ""),

                // Chrome с кнопкой Заказать внизу страницы  - полный заказ
                Arguments.of("chrome", "DOWN",
                        "Петр", "Иванов", "Казань", "Кремлевская", "+79223334455",
                        "05.07.2026", "Оставить у двери"),

                // Firefox с кнопкой Заказать вверху страницы  - полный заказ
                Arguments.of("firefox", "UP",
                        "Сергей", "Смирнов", "Екатеринбург", "Площадь 1905 года", "+79445556677",
                        "10.09.2026", "тест"),

                // Firefox с кнопкой Заказать внизу страницы  - полный заказ
                Arguments.of("firefox", "DOWN",
                        "Дмитрий", "Соколов", "Ростов-на-Дону", "Площадь Ленина", "+79556667788",
                        "30.11.2026", "тест"),

                // Firefox с кнопкой Заказать внизу страницы и с пустым комментарием
                Arguments.of("firefox", "DOWN",
                        "Елена", "Михайлова", "Самара", "Российская", "+79667778899",
                        "23.06.2026", "")
        );
    }

    private void setUp(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("https://qa-scooter.education-services.ru/");
        closeCookieConsent();
    }

    private void closeCookieConsent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(".//button[@class='App_CookieButton__3cvqF']")
            ));
            cookieButton.click();
        } catch (Exception e) {
            System.out.println("Не удалось закрыть куки: " + e.getMessage());
        }
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}