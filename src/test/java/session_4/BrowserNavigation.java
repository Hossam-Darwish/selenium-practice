package session_4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrowserNavigation {

    private WebDriver driver;

    @BeforeTest
    public void openDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test
    public void testRefresh() {
        driver.get("https://www.google.com/");
        driver.navigate().refresh();
        Assert.assertEquals(driver.getTitle(), "Google");

    }

    @Test
    public void testBack() {
        driver.get("https://www.google.com/");
        driver.get("https://www.facebook.com/");
        Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
        driver.navigate().back();
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @Test
    public void testForward() {
        driver.get("https://www.google.com/");
        driver.get("https://www.facebook.com/");
        driver.navigate().back();
        driver.navigate().forward();
        Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");

    }

    @Test
    public void testFrames() {
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        String text = driver
                .switchTo()
                .frame("frame-top")
                .switchTo()
                .frame("frame-middle")
                .findElement(By.id("content"))
                .getText();

        Assert.assertTrue(text.contains("MIDDLE"));
        throw new SkipException("Just for demo skip ");


    }

    @Test
    public void testFileUpload() {
        driver.get("https://the-internet.herokuapp.com/upload");
        driver.findElement(By.id("file-upload")).sendKeys("E:\\JavaEE.PNG");
        driver.findElement(By.id("file-submit")).click();
        String text = driver.findElement(By.id("uploaded-files")).getText();
        Assert.assertEquals(text, "JavaEE.PNG");

    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }
}
