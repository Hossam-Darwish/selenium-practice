package session_3;

import com.aventstack.extentreports.utils.FileUtil;
import com.google.common.io.Files;
import file_utils.CsvFileManager;
import file_utils.ExcelFileManager;
import file_utils.JsonFileManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.RetryAnalyzer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AuthenticationFormTests {
    Properties props = System.getProperties();
    private final ExcelFileManager excelFileManager = new ExcelFileManager("src/test/test_data/formAuthenticationFile.xlsx", "Sheet1");
    private WebDriver driver;

    @BeforeTest
    public void openDriver() throws IOException {
        props.load(new FileInputStream("src/test/test_data/config.properties"));
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void openAppLink() {
        driver.get(props.getProperty("LOGIN_URL"));
    }

    @Test()
    public void testLoginWithValidCredentials() {

        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();

        Assert.assertTrue(driver.findElement(By.id("flash")).isDisplayed());
        String text = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(text.contains("You logged into a secure area!"));

    }

    @Test(priority = 2)
    public void testLoginWithEmptyFields() {

        driver.findElement(By.id("username")).sendKeys("");
        driver.findElement(By.id("password")).sendKeys("");
        driver.findElement(By.cssSelector("button.radius")).click();
        Assert.assertTrue(driver.findElement(By.id("flash")).isDisplayed());
        String text = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(text.contains("Your username is invalid!"));

    }

    @Test
    public void testCheckBox1() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElement(By.xpath("//*[@id='checkboxes']/input[1]"));
        checkbox1.click();
        Assert.assertTrue(checkbox1.isSelected());

        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));
        checkbox2.click();
        Assert.assertFalse(checkbox2.isSelected());

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testSlider() {
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");
        WebElement slider = driver.findElement(By.tagName("input"));
        for (int i = 0; i < 8; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        Assert.assertEquals(driver.findElement(By.id("range")).getText(), "4");
        Assert.assertTrue(false);
    }


    @Test(dataProvider = "getUserCredentialsFromCsv")
    public void testLoginWithExternalFile(String userName, String pass) {
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.className("radius")).click();
        Assert.assertTrue(driver.findElement(By.id("flash")).isDisplayed());
        String text = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(text.contains("invalid!"));
    }


    @Test(dataProvider = "getData")
    public void testLoginWithInvalidCredentials(String usr, String pass) {
        driver.findElement(By.id("username")).sendKeys(usr);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.className("radius")).click();
        Assert.assertTrue(driver.findElement(By.id("flash")).isDisplayed());
        String text = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(text.contains("invalid!"));

    }

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                {"tomsmith", "1234"},
                {"ahmed", "SuperSecretPassword!"},
                {"", ""},
                {"", "SuperSecretPassword!"},
                {"tomsmith", ""}
        };
    }

    @DataProvider
    public Object[][] getUserCredentialsFromCsv() throws IOException {
        return CsvFileManager.getCsvData("src/test/test_data/formAuthenticationFile.csv");
    }

    @DataProvider
    public Object[][] getUserCredentialsFromExcel() throws IOException, InvalidFormatException {
        return excelFileManager.getExcelData();
    }

    @DataProvider
    public Object[][] getUserCredentialsFromJson() throws IOException, ParseException {
        String[] keys = {"username", "password"};
        return JsonFileManager.readJsonData("src/test/test_data/credentials.json", keys);
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            var camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            Files.move(screenshot, new File(System.getProperty("user.dir")+"/screenshots/"+ result.getName() + ".png"));
        }
    }

    @AfterTest
    public void closeDriver() {
        driver.close();
    }
}