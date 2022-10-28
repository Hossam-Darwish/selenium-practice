package session_5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DynamicLoadingTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void openDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //  Explicit Wait
             wait = new WebDriverWait(driver, 50);

    }

    @BeforeMethod
    public void openLink(){
        driver.get("https://the-internet.herokuapp.com/dynamic_loading");
    }


    @Test
    public void testDynamicLoading(){
        driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[text()='Start']")))).click();
        WebElement el = driver.findElement(By.id("finish"));
        wait.until(ExpectedConditions.visibilityOf(el));
        String text =  el.getText();
        Assert.assertEquals(text,"Hello World!");
    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }
}
