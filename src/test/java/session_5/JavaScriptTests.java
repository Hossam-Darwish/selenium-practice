package session_5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class JavaScriptTests {

    private WebDriver driver;

    @BeforeTest
    public void openDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);

    }

    @BeforeMethod
    public void openLink(){
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
    }

    @Test
    public void testJavaScriptAlert() {

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS Alert");
        alert.accept();  // OK

        String resultText = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(resultText, "You successfully clicked an alert");

    }

    @Test
    public void testJavaScriptConfirmOk() {

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();  // OK
        String resultText = driver.findElement(By.id("result")).getText();

        Assert.assertEquals(resultText, "You clicked: Ok");

    }

    @Test
    public void testJavaScriptConfirmCancel() {
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert alert = driver.switchTo().alert();
        alert.dismiss();  // Cancel
        String resultText = driver.findElement(By.id("result")).getText();

        Assert.assertEquals(resultText, "You clicked: Cancel");

    }

    @Test
    public void testJavaScriptPrompt(){
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        Alert alert = driver.switchTo().alert();
        alert.sendKeys("Ahmed");
        alert.accept();  // Ok
        String resultText = driver.findElement(By.id("result")).getText();

        Assert.assertEquals(resultText, "You entered: Ahmed");
    }

    @Test
    public void testScroll(){
        driver.get("https://the-internet.herokuapp.com/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)");

        Assert.assertTrue(driver.findElement(By.id("page-footer")).isDisplayed());


    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }


}
