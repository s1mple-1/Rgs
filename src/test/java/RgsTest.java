import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class RgsTest {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @Before
    public void open() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webDriver, 10);
    }

    @Test
    public void exampleTest() {
//        1 +
        webDriver.get("http://www.rgs.ru");

//        2 +
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id=\"main-navbar-collapse\"]//a[contains(text(), 'Меню')]")));
        WebElement menuElement = webDriver.findElement(By.xpath("//div[@id=\"main-navbar-collapse\"]//a[contains(text(), 'Меню')]"));
        menuElement.click();

//        3 +
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@href=\"https://www.rgs.ru/products/private_person/health/dms/generalinfo/index.wbp\"]")));
        WebElement dmsElement = webDriver.findElement(By.xpath("//a[@href=\"https://www.rgs.ru/products/private_person/health/dms/generalinfo/index.wbp\"]"));
        dmsElement.click();

//        4 check head +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"content-document-header\"]")));
        Assert.assertEquals("Содержание не соотвествует", "ДМС — добровольное медицинское страхование",
                webDriver.findElement(By.xpath("//h1[@class=\"content-document-header\"]")).getText());

//        5 +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Отправить заявку')]")));
        WebElement sendElement = webDriver.findElement(By.xpath("//a[contains(text(), 'Отправить заявку')]"));
        sendElement.click();

//        6 check head +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"modal-content\"]")));
        Assert.assertEquals("Содержание не соотвествует", "Заявка на добровольное медицинское страхование",
                webDriver.findElement(By.xpath("//div[@class=\"modal-content\"]//b[contains(text(), 'Заявка на добровольное медицинское страхование')]")).getText());

//        7 +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"modal-content\"]")));
        WebElement lastNameElement = webDriver.findElement(By.xpath("//input[@name=\"LastName\"]"));
        WebElement firstNameElement = webDriver.findElement(By.xpath("//input[@name=\"FirstName\"]"));
        WebElement middleNameElement = webDriver.findElement(By.xpath("//input[@name=\"MiddleName\"]"));
        WebElement regionElement = webDriver.findElement(By.xpath("//select[@name=\"Region\"]"));
        WebElement phoneElement = webDriver.findElement(By.xpath("//input[contains(@data-bind, 'value: Phone')]"));
        WebElement emailElement = webDriver.findElement(By.xpath("//input[@name=\"Email\"]"));
        WebElement dateElement = webDriver.findElement(By.xpath("//input[@name=\"ContactDate\"]"));
        WebElement commentElement = webDriver.findElement(By.xpath("//textarea[@name=\"Comment\"]"));
        WebElement checkElement = webDriver.findElement(By.xpath("//input[@type=\"checkbox\"]"));
        WebElement sendButton = webDriver.findElement(By.xpath("//button[@id=\"button-m\"]"));


        lastNameElement.click();
        lastNameElement.sendKeys("Федотов");

        firstNameElement.click();
        firstNameElement.sendKeys("Федот");

        middleNameElement.click();
        middleNameElement.sendKeys("Вассарионович");

        regionElement.click();
        WebElement region = regionElement.findElement(By.xpath("//option[@value=\"77\"]"));
        region.click();

        phoneElement.click();
        phoneElement.sendKeys("9998887766");

        emailElement.click();
        emailElement.sendKeys("qwertyqwerty");

        dateElement.click();
        dateElement.sendKeys("11.06.2020");

        commentElement.click();
        commentElement.sendKeys("Бла-бла-бла");

        checkElement.click();

//        8 +
        Assert.assertEquals("Содержание поля Фамилия не соотвествует ожидаемому", "Федотов",
                lastNameElement.getAttribute("value"));

        Assert.assertEquals("Содержание поля Имя не соотвествует ожидаемому", "Федот",
                firstNameElement.getAttribute("value"));

        Assert.assertEquals("Содержание поля Отчество не соотвествует ожидаемому", "Вассарионович",
                middleNameElement.getAttribute("value"));

        Assert.assertEquals("Содержание поля Регион не соотвествует ожидаемому", "77",
                regionElement.getAttribute("value"));

        Assert.assertEquals("Содержание поля Телефон не соотвествует ожидаемому", "9998887766",
                phoneElement.getAttribute("value").replaceAll("\\p{Punct}|\\s", "").substring(1));

        Assert.assertEquals("Содержание E-mail не соотвествует ожидаемому", "qwertyqwerty",
                emailElement.getAttribute("value"));

        Assert.assertEquals("Содержание поля Дата не соотвествует ожидаемому", "11.06.2020",
                dateElement.getAttribute("value"));

        Assert.assertEquals("Содержание поля Комментарий не соотвествует ожидаемому", "Бла-бла-бла",
                commentElement.getAttribute("value"));

        Assert.assertEquals("Флаг поля Согласен не соотвествует ожиданию", "on",
                checkElement.getAttribute("value"));
//        or
        Assert.assertNull("Галочка \"Я согласен\" не нажата, отправка не возможна", sendButton.getAttribute("disabled"));

//        9 +
        sendButton.click();

//        10 +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"validation-error-text\"]")));
        Assert.assertEquals("Нет реакции на неправильный ввод e-mail", "Введите адрес электронной почты",
                webDriver.findElement(By.xpath("//span[@class=\"validation-error-text\"]")).getText());
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
