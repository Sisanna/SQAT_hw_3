import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  

import java.net.URL;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SettingsPageTest {
    public WebDriver driver;

    private SettingsPage settingsPage;
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();

        this.settingsPage = new SettingsPage(this.driver);
        this.settingsPage.login("testella", "asd123qwe");
        this.driver.get("https://animeaddicts.hu/user.php?userfunction");   // It can only be accessed after login
        
        ResultPage dataSettings = this.settingsPage.navigate(By.xpath("//*[@id=\"right\"]/div/table[1]/tbody/tr[3]/td[1]/a"));  // Go to user data settings
    }

    @Test
    public void testChangingUserData() {
        settingsPage.fillOut("Ella", "Valahol", "2000", "SQAT", "asd123qwe");
        assertTrue(settingsPage.getBodyText().contains("sikeres"));
        WebElement successText = settingsPage.waitAndReturnElement((By.xpath("//*[@id=\"right\"]/div[1]/table/tbody/tr/td[2]/h1")));
        assertTrue(successText.isDisplayed());
    }

    @Test
    public void testChangingUserDataWithoutCorrectPassword() {
        settingsPage.fillOut("Ella", "Valahol", "2000", "SQAT", "");
        WebElement warningText = settingsPage.waitAndReturnElement((By.xpath("//*[@id=\"right\"]/div[1]/table/tbody/tr/td[2]/h1")));
        assertTrue(warningText.isDisplayed());
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
