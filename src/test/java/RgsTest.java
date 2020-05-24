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
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"main-navbar-collapse\"]/ol[1]/li/a")));
        WebElement menuElement = webDriver.findElement(By.xpath("//*[@id=\"main-navbar-collapse\"]/ol[1]/li/a"));
        menuElement.click();

//        3 +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"container-rgs-main-menu-links\"]")));
        WebElement dmsElement = webDriver.findElement(By.xpath("//*[@id=\"rgs-main-menu-insurance-dropdown\"]/div[1]/div/div/div[2]/div[2]/ul/li[2]/a"));
        dmsElement.click();

//        4 check head +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"content-document-header\"]")));
        Assert.assertEquals("Содержание не соотвествует", "ДМС — добровольное медицинское страхование",
                webDriver.findElement(By.xpath("//*[@class=\"content-document-header\"]")).getText());

//        5 +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Отправить заявку')]")));
        WebElement sendElement = webDriver.findElement(By.xpath("//a[contains(text(), 'Отправить заявку')]"));
        sendElement.click();

//        6 check head +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"modal-content\"]")));
        Assert.assertEquals("Содержание не соотвествует", "Заявка на добровольное медицинское страхование",
                webDriver.findElement(By.xpath("//*[@class=\"modal-content\"]//b[contains(text(), 'Заявка на добровольное медицинское страхование')]")).getText());

//        7 +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"modal-content\"]")));
        WebElement lastNameElement = webDriver.findElement(By.xpath("//*[@name=\"LastName\"]"));
        WebElement firstNameElement = webDriver.findElement(By.xpath("//*[@name=\"FirstName\"]"));
        WebElement middleNameElement = webDriver.findElement(By.xpath("//*[@name=\"MiddleName\"]"));
        WebElement regionElement = webDriver.findElement(By.xpath("//*[@name=\"Region\"]"));
        WebElement phoneElement = webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[5]/input"));
        WebElement emailElement = webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[6]/input"));
        WebElement dateElement = webDriver.findElement(By.xpath("//*[@name=\"ContactDate\"]"));
        WebElement commentElement = webDriver.findElement(By.xpath("//*[@name=\"Comment\"]"));
        WebElement checkElement = webDriver.findElement(By.xpath("//*[@type=\"checkbox\"]"));
        WebElement sendButton = webDriver.findElement(By.xpath("//*[@id=\"button-m\"]"));


        lastNameElement.click();
        lastNameElement.sendKeys("Федотов");


        firstNameElement.click();
        firstNameElement.sendKeys("Федот");

        middleNameElement.click();
        middleNameElement.sendKeys("Вассарионович");

        regionElement.click();
        WebElement region = regionElement.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[4]/select/option[5]"));
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

//        8
        Assert.assertEquals("Содержание отсутсвует", "form-control validation-control-has-success",
                webDriver.findElement(By.xpath("//*[@name=\"LastName\"]")).getAttribute("class"));

        Assert.assertEquals("Содержание отсутсвует", "form-control validation-control-has-success",
                webDriver.findElement(By.xpath("//*[@name=\"FirstName\"]")).getAttribute("class"));

        Assert.assertEquals("Содержание отсутсвует", "form-control validation-control-has-success",
                webDriver.findElement(By.xpath("//*[@name=\"MiddleName\"]")).getAttribute("class"));

        Assert.assertEquals("Содержание отсутсвует", "popupSelect form-control validation-control-has-success",
                webDriver.findElement(By.xpath("//*[@name=\"Region\"]")).getAttribute("class"));

        Assert.assertEquals("Содержание отсутсвует", "form-control validation-control-has-success",
                webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[5]/input")).getAttribute("class"));


        Assert.assertEquals("Содержание отсутсвует", "dateInput form-control collapsing-in collapsing-out validation-control-has-success",
                webDriver.findElement(By.xpath("//*[@name=\"ContactDate\"]")).getAttribute("class"));

        Assert.assertNull("Галочка \"Я согласен\" не нажата", webDriver.findElement(By.xpath("//*[@id=\"button-m\"]")).getAttribute("disabled"));

//        9 +
        sendButton.click();

//        10 +
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"validation-error-text\"]")));
        Assert.assertEquals("Нет реакции на неправильный ввод", "Введите адрес электронной почты",
                webDriver.findElement(By.xpath("//*[@class=\"validation-error-text\"]")).getText());
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
