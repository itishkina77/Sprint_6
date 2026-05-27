package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локатор кнопки согласия с куки
    private By cookieButton = By.xpath(".//button[@class='App_CookieButton__3cvqF']");

    private String getQuestionId(int index) {
        return "accordion__heading-" + index;
    }
    private String getAnswerId(int index) {
        return "accordion__panel-" + index;
    }

    //Кнопка Заказать вверху страницы
    private By orderButtonUpPage = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");
    //Кнопка Заказать внизу страницы
    private By orderButtonDownPage = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']");


    public MainPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    //Нажать Заказать вверху страницы
    public void orderButtonUpPageClick(){
        driver.findElement(orderButtonUpPage).click();
    }

    //Нажать Заказать внизу страницы
    public void orderButtonDownPageClick(){
        driver.findElement(orderButtonDownPage).click();
    }

    // Закрыть согласие на куки
    public void closeCookieConsent() {
        try {
            WebElement cookieButtonElement = wait.until(
                    ExpectedConditions.elementToBeClickable(cookieButton)
            );
            cookieButtonElement.click();
        } catch (Exception e) {
            System.out.println("Не удалось закрыть куки: " + e.getMessage());
        }
    }

    // Клик по аккордеону по его id
    public void clickQuestionByIndex(int index) {
        By questionLocator = By.id(getQuestionId(index));
        WebElement arrowElement = wait.until(
                ExpectedConditions.elementToBeClickable(questionLocator)
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arrowElement);
    }

    // Получение текста панели по её id
    public void verifyAnswerTextEquals(int questionIndex, String expectedText) {
        By answerLocator = By.id(getAnswerId(questionIndex));
        String actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator)).getText();
        assertEquals(expectedText, actualText);
    }
}