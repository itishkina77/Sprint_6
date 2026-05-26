package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

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

    // Клик по аккордеону по его id
    public void clickAccordionById(String accordionId) {
        WebElement arrowElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.id(accordionId))
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arrowElement);
    }

    // Получение текста панели по её id
    public String getPanelTextById(String panelId) {
        WebElement panelElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(panelId))
        );
        return panelElement.getText();
    }

    // Проверка текста панели с понятным сообщением об ошибке
    public void verifyPanelTextEquals(String panelId, String expectedText) {
        String actualText = getPanelTextById(panelId);
        Assertions.assertEquals(
                expectedText,
                actualText,
                "Текст панели не совпадает. Ожидалось: '" + expectedText + "', получено: '" + actualText + "'"
        );
    }
}