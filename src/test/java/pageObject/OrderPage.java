package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage {
    private WebDriver driver;

    //Поле Имя
    private By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    //Поле Фамилия
    private By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    //Поле Адрес
    private By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    //Поле Станция метро
    private By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
    //Список станций
    private By getMetroStationList = By.xpath("//div[@class='select-search__select']//*[text()='Менделеевская']");
    //Поле Телефон
    private By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    //Кнопка Далее
    private By nextButton = By.xpath(".//button[text()='Далее']");

    public OrderPage(WebDriver driver){
        this.driver = driver;
    }

    //Заполнить поле Имя
    public void setFirstName(String firstName){
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    //Заполнить поле Фамилия
    public void setLastName(String lastName){
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    //Заполнить поле Адрес
    public void setAddress(String address){
        driver.findElement(addressField).sendKeys(address);
    }

    //Выбрать станцию метро Достоевская
    public void setMetroStation(){
        driver.findElement(metroStationField).click();
        driver.findElement(getMetroStationList).click();


    }

    //Заполнить поле Телефон
    public void setPhone(String phone){
        driver.findElement(phoneField).sendKeys(phone);
    }

    //Заполнить форму заказа
    public void setOrderForm(String firstName, String lastName, String address, String stationName, String phone){
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setMetroStation();
        setPhone(phone);
    }

    //Нажать кнопку Далее
    public void clickNextButton(){
        driver.findElement(nextButton).click();
    }

}
