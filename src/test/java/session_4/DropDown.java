package session_4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class DropDown {

    private WebDriver driver;

    @BeforeTest
    public void openDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }


    @Test
    public void testDropDownSelection() {
        driver.get("https://the-internet.herokuapp.com/dropdown");
        Select select = new Select(driver.findElement(By.id("dropdown")));
        /**
         *  select.selectByIndex(2);
         *  select.selectByVisibleText("Option 2");
         *  select.selectByValue("2");
         */
        select.selectByIndex(2);

        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, "Option 2");

        /*
         select "Option 1"
         assert the your choice has been selected
         -- get all available options
         -- Assert that available options are Option 1 and Option 2
         */

        select.selectByVisibleText("Option 1");
        selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption,"Option 1");

       ArrayList<WebElement> elements = (ArrayList<WebElement>) select.getOptions();
       /* for (int i = 0; i < elements.size(); i++) {
            System.out.println(elements.get(i).getText());
        }*/

        for (WebElement el : elements) {
            System.out.println(el.getText());
        }
   Assert.assertEquals(elements.size(),3);
    }


    @AfterTest
    public void closeDriver(){
        driver.quit();
    }


}
