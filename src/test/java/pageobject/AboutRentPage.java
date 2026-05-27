package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AboutRentPage {
    private WebDriver driver;

    //Название формы
    private By nameForm = By.xpath(".//div[@class='Order_Header__BZXOb']");

    //Поле "Когда привезти самокат"
    private By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    //Поле "Срок аренды"
     private By rentalPeriodField = By.xpath(".//div[@class='Dropdown-control']");
     //Элемент списка "Срок аренды"
     private By rentalPeriodOption = By.xpath(".//div[@class='Dropdown-option' and text()='сутки']");
    //Цвет самоката
    private By colorInput = By.xpath(".//input[@id='black']");
    //Поле "Комментарий для курьера"
    private By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    //Кнопка "Заказать"
    private By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    //Кнопка Да на форме подтверждения заказа
    private By confirmButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Да']");
// Заказ оформлен
    private By orderPlaced = By.xpath(".//div[text()='Заказ оформлен']");


    public AboutRentPage(WebDriver driver){
        this.driver = driver;
    }

    //Получить название формы
    public String getNameForm() {
        return driver.findElement(nameForm).getText();
    }
    //Указать дату, когда привезти самокат
    public void setStartDateRental(String dateRental){
        driver.findElement(dateField).sendKeys(dateRental);
        driver.findElement(By.xpath(".//div[@role='button' and @tabindex='0']")).click();
    }
    //Указать срок аренды
    public void setRentalPeriodField() {
        driver.findElement(rentalPeriodField).click();
        driver.findElement(rentalPeriodOption).click();
    }
//Выбрать цвет самоката
    public void setColor(){
        driver.findElement(colorInput);
    }

    //Написать комментарий курьеру
    public void setComment(String comment){
        driver.findElement(commentField).sendKeys(comment);
    }

    //Нажать кнопку Заказать
    public void clickOrder()
    {
        driver.findElement(orderButton).click();
    }

    //Подтвердить заказ
    public void confirmOrder(){
        driver.findElement(confirmButton).click();
    }

    //Заполнить форму "Про аренду и подтвердить заказ"
    public void setAndConfirm (String dateRental,  String comment){
        setStartDateRental(dateRental);
        setRentalPeriodField();
        setColor();
        setComment(comment);
        clickOrder();
        confirmOrder();
    }

    // Проверка видимости окна с сообщением "Заказ оформлен"
    public void orderPlacedDisplayed() {
        try {
            WebElement message = new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderPlaced));
            assertTrue(message.isDisplayed(), "Сообщение 'Заказ оформлен' должно быть видимым на странице");
        } catch (Exception e) {
            fail("Не найдено сообщение 'Заказ оформлен' после оформления заказа. Ошибка: " + e.getMessage());
        }
    }
}
