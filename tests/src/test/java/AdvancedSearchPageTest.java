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

public class AdvancedSearchPageTest {
    public WebDriver driver;

    private AdvancedSearchPage advancedSearchPage;
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        
        this.advancedSearchPage = new AdvancedSearchPage(this.driver);
        this.advancedSearchPage.login("testella", "asd123qwe");
    }
    
    @Test
    public void testAdvancedSearch() {
        ResultPage ResultPage = this.advancedSearchPage.advancedSearch("Kimetsu no Yaiba\n");   // Search with an enter at the end -> the result page should load immediately

        WebElement reviewBox = this.advancedSearchPage.waitAndReturnElement((By.xpath("//*[@id=\"right\"]/div[11]")));
        assertTrue(reviewBox.isDisplayed());   
    }

    @Test
    public void testNoResultsAdvancedSearch() {
        String[] searchQueries = {"Ki", "Naruto", "Kimets no Yaiba"};  

        for (String searchQuery : searchQueries) {
            driver.navigate().refresh();
            
            this.advancedSearchPage = new AdvancedSearchPage(driver);

            ResultPage resultPage = this.advancedSearchPage.advancedSearch(searchQuery + "\n");

            WebElement warningText = this.advancedSearchPage.waitAndReturnElement(By.xpath("//*[@id='right']/div/table/tbody/tr/td[2]/h1"));
            assertTrue(warningText.isDisplayed());

            Boolean hasNoReviews = this.advancedSearchPage.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='right']/div[11]")));
            assertTrue(hasNoReviews);
        }  
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
