import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.MainPage;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ImportantQuestionsSectionTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @ParameterizedTest
    @MethodSource("accordionDataProvider")
    @DisplayName("Клик по аккордеону раскрывает соответствующий текст")
    void clickAccordion_shouldExpandCorrespondingText(
            String accordionId,
            String panelId,
            String expectedText) {

        driver.get("https://qa-scooter.education-services.ru/");
        MainPage mainPage = new MainPage(driver);

        mainPage.clickAccordionById(accordionId);
        mainPage.verifyPanelTextEquals(panelId, expectedText);
    }

    // Источник данных: [id аккордеона, id панели, ожидаемый текст]
    private static Stream<Arguments> accordionDataProvider() {
        return Stream.of(
                arguments(
                        "accordion__heading-0",
                        "accordion__panel-0",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                ),
                arguments(
                        "accordion__heading-1",
                        "accordion__panel-1",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."
                ),
                arguments(
                        "accordion__heading-2",
                        "accordion__panel-2",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                ),
                arguments(
                        "accordion__heading-3",
                        "accordion__panel-3",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                ),
                arguments(
                        "accordion__heading-4",
                        "accordion__panel-4",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."
                ),
                arguments(
                        "accordion__heading-5",
                        "accordion__panel-5",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                ),
                arguments(
                        "accordion__heading-6",
                        "accordion__panel-6",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."
                ),
                arguments(
                        "accordion__heading-7",
                        "accordion__panel-7",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                )
        );
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}