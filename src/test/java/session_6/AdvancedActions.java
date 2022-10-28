package session_6;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

public class AdvancedActions {
    private WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void openDriver(String browser) {

        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver  = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

        }

        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void openLink() {
        driver.get("https://the-internet.herokuapp.com/hovers");

    }

    @Test
    @Parameters("userNumber")
    public void testHoverOfUser(int userNumber) {

        Actions userHover = new Actions(driver) ;
        WebElement secondUser = driver.findElements(By.cssSelector("div.figure")).get(userNumber -1);
        userHover.moveToElement(secondUser).perform();
        String userName = driver.findElements(By.cssSelector(".figcaption h5")).get(userNumber - 1).getText();
        Assert.assertEquals(userName,String.format("name: user%d",userNumber));



    }

    @Test
    public void testDragAndDrop() {
        driver.get("https://demo.guru99.com/test/drag_drop.html");
        WebElement bankElement = driver.findElement(By.id("credit2"));
        WebElement bankPlace = driver.findElement(By.id("bank"));
        WebElement amount = driver.findElements(By.id("fourth")).get(0);
        WebElement amountPlace = driver.findElement(By.id("amt7"));

        Actions action1 = new Actions(driver);

        action1.dragAndDrop(bankElement, bankPlace).perform();
        action1.dragAndDrop(amount, amountPlace).perform();
        String debitMovement = driver.findElement(By.id("t7")).getText();
        Assert.assertEquals(debitMovement, "5000");


    }


    @Test
    public void testScroll() {
        driver.get("https://www.selenium.dev/documentation/webdriver/browser/windows/");
        /*driver.findElement(By.cssSelector(".td-page")).sendKeys(Keys.chord(Keys.CONTROL,Keys.END));*/

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Assert.assertEquals(driver.findElement(By.className("mt-2")).getText(), "About Selenium");

    }


    @Test
    public void testDoubleClick() {
        driver.get("https://www.browserstack.com/");
        WebElement freeTrialLocator = driver.findElement(By.id("free-trial-link-anchor"));

        Actions ac = new Actions(driver);
        ac.doubleClick(freeTrialLocator).perform();

        Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Create a FREE Account')]")).isDisplayed());
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
